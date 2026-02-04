package dev.imdmk.ordersystem.infrastructure.event;

import dev.imdmk.ordersystem.application.event.DomainEventPublisher;
import dev.imdmk.ordersystem.domain.event.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;

import java.util.Objects;

public final class SpringDomainEventPublisher implements DomainEventPublisher {

    private final ApplicationEventPublisher publisher;

    public SpringDomainEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = Objects.requireNonNull(publisher, "publisher must not be null");
    }

    @Override
    public void publish(DomainEvent event) {
        publisher.publishEvent(event);
    }
}

