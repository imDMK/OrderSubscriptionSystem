package dev.imdmk.ordersystem.application.subscription.policy;

import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.Expiration;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public final class DefaultSubscriptionPolicy implements SubscriptionPolicy {

    @Override
    public Expiration expirationForNewSubscription(OrderId orderId) {
        Objects.requireNonNull(orderId, "orderId must not be null");
        return Expiration.until(Instant.now().plus(30, ChronoUnit.DAYS));
    }
}

