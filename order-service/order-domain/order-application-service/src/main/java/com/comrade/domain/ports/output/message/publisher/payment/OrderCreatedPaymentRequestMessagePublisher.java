package com.comrade.domain.ports.output.message.publisher.payment;

import com.comrade.domain.event.DomainEventPublisher;
import com.comrade.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
