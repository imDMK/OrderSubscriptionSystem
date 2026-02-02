package dev.imdmk.ordersystem.domain.order.event;

import dev.imdmk.ordersystem.domain.event.DomainEvent;
import dev.imdmk.ordersystem.domain.order.OrderId;

import java.time.Instant;

public record OrderPaidEvent(
        OrderId orderId,
        Instant occurredAt
) implements DomainEvent {

    public static OrderPaidEvent now(OrderId orderId) {
        return new OrderPaidEvent(orderId, Instant.now());
    }
}

