package com.comrade.domain;

import com.comrade.domain.dto.create.CreateOrderCommand;
import com.comrade.domain.entity.Customer;
import com.comrade.domain.entity.Order;
import com.comrade.domain.entity.Restaurant;
import com.comrade.domain.event.OrderCreatedEvent;
import com.comrade.domain.exception.OrderDomainException;
import com.comrade.domain.mapper.OrderDataMapper;
import com.comrade.domain.ports.output.repository.CustomerRepository;
import com.comrade.domain.ports.output.repository.OrderRepository;
import com.comrade.domain.ports.output.repository.RestaurantRepository;
import com.comrade.domain.service.OrderDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateHelper {
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    private final ApplicationEventDomainPublisher applicationEventDomainPublisher;

    public OrderCreateHelper(OrderDomainService orderDomainService,
                             OrderRepository orderRepository,
                             CustomerRepository customerRepository,
                             RestaurantRepository restaurantRepository,
                             OrderDataMapper orderDataMapper,
                             ApplicationEventDomainPublisher applicationEventDomainPublisher) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
        this.applicationEventDomainPublisher = applicationEventDomainPublisher;
    }

    public OrderCreatedEvent persistOrder(CreateOrderCommand createOrderCommand){
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        Order order=orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order,restaurant);
        Order savedOrder=saveOrder(order);
        log.info("Order is saved with id {}",savedOrder.getId().getValue());
        return orderCreatedEvent;
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findCustomer(customerId);
        if (optionalCustomer.isEmpty()){
            log.warn("Could not find customer with id {}",customerId);
            throw new OrderDomainException("Could not find customer with id "+customerId);
        }
    }

    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant =  orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()){
            log.warn("Could not find restaurant with restaurant id {}",createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Could not find restaurant with restaurant id"+createOrderCommand.getRestaurantId());
        }
        return optionalRestaurant.get();
    }

    private Order saveOrder(Order order){
        Order orderResult = orderRepository.save(order);
        if (null == orderResult){
            log.error("Could not save order !");
            throw new OrderDomainException("Could not save order !");
        }
        log.info("Order is saved with id {}",orderResult.getId().getValue());
        return orderResult;
    }
}
