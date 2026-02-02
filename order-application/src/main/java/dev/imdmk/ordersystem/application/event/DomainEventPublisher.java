package dev.imdmk.ordersystem.application.event;

import dev.imdmk.ordersystem.domain.event.DomainEvent;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}

