package dev.imdmk.ordersystem.domain.subscription.event;

import dev.imdmk.ordersystem.domain.event.DomainEvent;
import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

import java.time.Instant;

public record SubscriptionStartedEvent(
        SubscriptionId subscriptionId,
        OrderId orderId,
        Instant occurredAt
) implements DomainEvent {

    public static SubscriptionStartedEvent now(SubscriptionId subscriptionId, OrderId orderId) {
        return new SubscriptionStartedEvent(subscriptionId, orderId, Instant.now());
    }
}
