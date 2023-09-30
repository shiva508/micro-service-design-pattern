package com.comrade.domain;

import com.comrade.domain.dto.create.CreateOrderCommand;
import com.comrade.domain.event.DomainEventPublisher;
import com.comrade.domain.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationEventDomainPublisher implements ApplicationEventPublisherAware, DomainEventPublisher<OrderCreatedEvent> {

    private ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void publish(OrderCreatedEvent orderCreatedEvent) {
        this.applicationEventPublisher.publishEvent(orderCreatedEvent);
        log.info("CreateOrderCommand is created with id: {}",orderCreatedEvent.getOrder().getId().getValue());
    }
}
