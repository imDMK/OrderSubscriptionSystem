package dev.imdmk.ordersystem.application.order.exception;

import dev.imdmk.ordersystem.domain.order.OrderId;

public final class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(OrderId id) {
        super("Order with value %s does not exist".formatted(id.value()));
    }
}
