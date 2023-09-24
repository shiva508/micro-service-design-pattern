package com.comrade.domain.mapper;

import com.comrade.domain.dto.create.CreateOrderCommand;
import com.comrade.domain.dto.create.CreateOrderResponse;
import com.comrade.domain.dto.create.OrderAddress;
import com.comrade.domain.entity.Order;
import com.comrade.domain.entity.OrderItem;
import com.comrade.domain.entity.Product;
import com.comrade.domain.entity.Restaurant;
import com.comrade.domain.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {

    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand){
        return Restaurant.builder()
                         .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                         .products(createOrderCommand.getItems()
                                                     .stream()
                                                     .map(orderItem -> new Product(new ProductId(orderItem.getProductId())))
                                                     .collect(Collectors.toList())
                                  )
                         .build();
    }

    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand){
        return Order.builder()
                    .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                    .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                    .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                    .price(new Money(createOrderCommand.getPrice()))
                    .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                    .build();
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(UUID.randomUUID(),
                                 orderAddress.getStreet(),
                                 orderAddress.getPostalCode(),
                                 orderAddress.getCity());
    }

    private List<OrderItem> orderItemsToOrderItemEntities(List<com.comrade.domain.dto.create.OrderItem> orderItems) {
        return orderItems.stream().map(orderItem -> OrderItem.builder()
                                                             .product(new Product(new ProductId(orderItem.getProductId())))
                                                             .price(new Money(orderItem.getPrice()))
                                                             .quantity(orderItem.getQuantity())
                                                             .subTotal(new Money(orderItem.getSubTotal()))
                                                             .build())
                                  .collect(Collectors.toList());
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order savedOrder) {
        return CreateOrderResponse.builder()
                                  .orderTracingId(savedOrder.getTrackingId().getValue())
                                  .orderStatus(savedOrder.getOrderStatus())
                                  .build();
    }
}
