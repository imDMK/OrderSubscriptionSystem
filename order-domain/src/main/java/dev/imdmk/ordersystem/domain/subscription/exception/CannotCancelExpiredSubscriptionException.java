package dev.imdmk.ordersystem.domain.subscription.exception;

import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

public class CannotCancelExpiredSubscriptionException extends RuntimeException {
    public CannotCancelExpiredSubscriptionException(SubscriptionId id) {
        super("Cannot cancel expired subscription: " + id);
    }
}
