package dev.imdmk.ordersystem.application.order.exception;

import dev.imdmk.ordersystem.domain.order.OrderId;

public class OrderNotFoundException extends RuntimeException {

    public OrderNotFoundException(OrderId id) {
        super("Order with id %s does not exist".formatted(id.value()));
    }
}
