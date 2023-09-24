package com.comrade.domain.ports.output.message.publisher.restaurantapproval;

import com.comrade.domain.event.DomainEventPublisher;
import com.comrade.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
