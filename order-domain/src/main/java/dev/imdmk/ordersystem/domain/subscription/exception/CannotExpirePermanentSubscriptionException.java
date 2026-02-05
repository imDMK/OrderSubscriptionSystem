package dev.imdmk.ordersystem.domain.subscription.exception;

import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

public class CannotExpirePermanentSubscriptionException extends RuntimeException {
    public CannotExpirePermanentSubscriptionException(SubscriptionId id) {
        super("Cannot expire permanent subscription: " + id);
    }
}
