package com.comrade.domain;

import com.comrade.domain.event.OrderCreatedEvent;
import com.comrade.domain.ports.output.message.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class OrderCreatedEventApplicationListener {

    private final OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher;

    public OrderCreatedEventApplicationListener(OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
        this.orderCreatedPaymentRequestMessagePublisher = orderCreatedPaymentRequestMessagePublisher;
    }

    @TransactionalEventListener
    public void process(OrderCreatedEvent orderCreatedEvent){
        orderCreatedPaymentRequestMessagePublisher.publish(orderCreatedEvent);
    }
}
