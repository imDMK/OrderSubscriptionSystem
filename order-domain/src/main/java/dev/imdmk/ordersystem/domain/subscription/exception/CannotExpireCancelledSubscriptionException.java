package dev.imdmk.ordersystem.domain.subscription.exception;

import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

public class CannotExpireCancelledSubscriptionException extends RuntimeException {
    public CannotExpireCancelledSubscriptionException(SubscriptionId id) {
        super("Cannot expire cancelled subscription: " + id);
    }
}
