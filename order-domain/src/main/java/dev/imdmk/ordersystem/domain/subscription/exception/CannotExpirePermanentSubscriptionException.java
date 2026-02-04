package dev.imdmk.ordersystem.domain.subscription.exception;

import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

public final class CannotExpirePermanentSubscriptionException extends RuntimeException {
    public CannotExpirePermanentSubscriptionException(SubscriptionId subscriptionId) {
        super("Permanent subscription with id %s cannot expire".formatted(subscriptionId));
    }
}
