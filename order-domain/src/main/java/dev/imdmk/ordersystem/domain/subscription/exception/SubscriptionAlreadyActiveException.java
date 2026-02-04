package dev.imdmk.ordersystem.domain.subscription.exception;

import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

public final class SubscriptionAlreadyActiveException extends RuntimeException {
    public SubscriptionAlreadyActiveException(SubscriptionId subscriptionId) {
        super("Subscription with id %s is already active".formatted(subscriptionId));
    }
}
