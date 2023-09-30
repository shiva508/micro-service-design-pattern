package com.comrade.domain;

import com.comrade.domain.dto.create.CreateOrderCommand;
import com.comrade.domain.dto.create.CreateOrderResponse;
import com.comrade.domain.event.OrderCreatedEvent;
import com.comrade.domain.mapper.OrderDataMapper;
import com.comrade.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCreateCommandHandler {

    private final OrderCreateHelper orderCreateHelper;

    private final OrderDataMapper orderDataMapper;

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    public OrderCreateCommandHandler(OrderCreateHelper orderCreateHelper,
                                     OrderDataMapper orderDataMapper,
                                     OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
        this.orderCreateHelper = orderCreateHelper;
        this.orderDataMapper = orderDataMapper;
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand){
        OrderCreatedEvent orderCreatedEvent = orderCreateHelper.persistOrder(createOrderCommand);
        log.info("Order is saved with id {}",orderCreatedEvent.getOrder().getId().getValue());
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
        return orderDataMapper.orderToCreateOrderResponse(orderCreatedEvent.getOrder(),"Order created successfully");
    }

}
