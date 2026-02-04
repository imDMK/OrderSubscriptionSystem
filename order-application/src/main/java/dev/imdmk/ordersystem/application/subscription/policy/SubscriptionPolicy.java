package dev.imdmk.ordersystem.application.subscription.policy;

import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.Expiration;

public interface SubscriptionPolicy {
    Expiration expirationForNewSubscription(OrderId orderId);
}

