package dev.imdmk.ordersystem.domain.subscription.exception;

import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

public final class SubscriptionAlreadyExpiredException extends RuntimeException {
    public SubscriptionAlreadyExpiredException(SubscriptionId subscriptionId) {
        super("Subscription with id %s already expired".formatted(subscriptionId));
    }
}
