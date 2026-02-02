package dev.imdmk.ordersystem.domain.order.event;

import dev.imdmk.ordersystem.domain.event.DomainEvent;
import dev.imdmk.ordersystem.domain.order.OrderId;

import java.time.Instant;

public record OrderCancelEvent(
        OrderId orderId,
        Instant occurredAt
) implements DomainEvent {

    public static OrderCancelEvent now(OrderId orderId) {
        return new OrderCancelEvent(orderId, Instant.now());
    }
}

