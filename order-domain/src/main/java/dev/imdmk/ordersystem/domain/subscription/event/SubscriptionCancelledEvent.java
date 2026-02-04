package dev.imdmk.ordersystem.domain.subscription.event;

import dev.imdmk.ordersystem.domain.event.DomainEvent;
import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

import java.time.Instant;

public record SubscriptionCancelledEvent(
        SubscriptionId subscriptionId,
        OrderId orderId,
        Instant occurredAt
) implements DomainEvent {

    public static SubscriptionCancelledEvent now(SubscriptionId subscriptionId, OrderId orderId) {
        return new SubscriptionCancelledEvent(subscriptionId, orderId, Instant.now());
    }
}
