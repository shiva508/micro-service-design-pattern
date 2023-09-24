package com.comrade.domain.service;

import com.comrade.domain.entity.Order;
import com.comrade.domain.entity.Product;
import com.comrade.domain.entity.Restaurant;
import com.comrade.domain.event.OrderCancelledEvent;
import com.comrade.domain.event.OrderCreatedEvent;
import com.comrade.domain.event.OrderPaidEvent;
import com.comrade.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService{

    public static final String UTC = "UTC";

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
        validateRestaurant(restaurant);
        setOrderProductInformation(order,restaurant);
        order.validateOrder();
        order.initializeOrder();
        log.info("Order initiated with id {}",order.getId().getValue());
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
    }
    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        log.info("Order with id {} is paid",order.getId().getValue());
        return new OrderPaidEvent(order,ZonedDateTime.now(ZoneId.of(UTC)));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
        log.info("Order with id {} is approved",order.getId().getValue());
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage) {
        order.initCancel(failureMessage);
        log.info("Order payment is init cancelled with id {} ",order.getId().getValue());
        return new OrderCancelledEvent(order,ZonedDateTime.now(ZoneId.of(UTC)));
    }
    @Override
    public void cancelOrder(Order order, List<String> failureMessage) {
        order.cancel(failureMessage);
        log.info("Order payment is cancelled with id {} ",order.getId().getValue());
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.isActive()){
            throw new OrderDomainException("Restaurant with id:"+restaurant.getId().getValue()+" is currently not active");
        }
    }
    private void setOrderProductInformation(Order order, Restaurant restaurant) {
        order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(restaurantProduct ->{
            Product currentProduct = orderItem.getProduct();
            if (currentProduct.equals(restaurantProduct)){
                currentProduct.updateWithConfirmedNameAndPrice(restaurantProduct.getName(),restaurantProduct.getPrice());
            }
        }));
    }
}
