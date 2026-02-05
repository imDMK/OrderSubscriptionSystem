package dev.imdmk.ordersystem.domain.subscription.exception;

import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

public class SubscriptionNotYetExpiredException extends RuntimeException {
    public SubscriptionNotYetExpiredException(SubscriptionId id) {
        super("Subscription has not yet expired: " + id);
    }
}
