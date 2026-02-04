package dev.imdmk.ordersystem.application.subscription.exception;

import dev.imdmk.ordersystem.domain.order.OrderId;

public final class SubscriptionNotFoundException extends RuntimeException {
    public SubscriptionNotFoundException(OrderId orderId) {
        super("Subscription with order value %s does not exist".formatted(orderId));
    }
}
