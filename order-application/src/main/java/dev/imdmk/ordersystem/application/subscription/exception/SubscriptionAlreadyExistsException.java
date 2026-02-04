package dev.imdmk.ordersystem.application.subscription.exception;

import dev.imdmk.ordersystem.domain.order.OrderId;

public final class SubscriptionAlreadyExistsException extends RuntimeException {
    public SubscriptionAlreadyExistsException(OrderId orderId) {
        super("Subscription with order value %s already exists".formatted(orderId));
    }
}