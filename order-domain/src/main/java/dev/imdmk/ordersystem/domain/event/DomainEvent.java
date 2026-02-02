package dev.imdmk.ordersystem.domain.event;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredAt();
}

