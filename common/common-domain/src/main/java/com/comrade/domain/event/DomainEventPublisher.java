package com.comrade.domain.event;

public interface DomainEventPublisher <T extends DomainEvent>{
    public void publish(T domainEvent);
}
