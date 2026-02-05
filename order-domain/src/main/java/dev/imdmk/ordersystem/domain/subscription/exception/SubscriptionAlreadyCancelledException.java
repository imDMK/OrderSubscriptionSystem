package dev.imdmk.ordersystem.domain.subscription.exception;

import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

public class SubscriptionAlreadyCancelledException extends RuntimeException {
    public SubscriptionAlreadyCancelledException(SubscriptionId id) {
        super("Subscription already cancelled: " + id);
    }
}
