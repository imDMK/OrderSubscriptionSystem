package dev.imdmk.ordersystem.domain.model;

import dev.imdmk.ordersystem.domain.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AggregateRoot {

    private final List<DomainEvent> domainEvents = new ArrayList<>(4);

    protected void registerEvent(DomainEvent event) {
        Objects.requireNonNull(event, "event must not be null");
        domainEvents.add(event);
    }

    public List<DomainEvent> pullDomainEvents() {
        final List<DomainEvent> events = List.copyOf(domainEvents);
        domainEvents.clear();
        return events;
    }

    protected boolean hasEvents() {
        return !domainEvents.isEmpty();
    }
}

