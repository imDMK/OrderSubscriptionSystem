package dev.imdmk.ordersystem.domain.subscription.exception;

import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

public class SubscriptionAlreadyExpiredException extends RuntimeException {
    public SubscriptionAlreadyExpiredException(SubscriptionId id) {
        super("Subscription already expired: " + id);
    }
}
