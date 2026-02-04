package dev.imdmk.ordersystem.domain.subscription.exception;

import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

public final class SubscriptionAlreadyCancelledException extends RuntimeException {
    public SubscriptionAlreadyCancelledException(SubscriptionId subscriptionId) {
        super("Subscription with id %s is already cancelled".formatted(subscriptionId));
    }
}
