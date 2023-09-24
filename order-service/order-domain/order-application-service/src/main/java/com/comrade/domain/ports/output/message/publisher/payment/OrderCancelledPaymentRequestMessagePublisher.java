package com.comrade.domain.ports.output.message.publisher.payment;

import com.comrade.domain.event.DomainEventPublisher;
import com.comrade.domain.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCancelledEvent> {
}
