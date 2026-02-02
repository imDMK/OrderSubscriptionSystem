package dev.imdmk.ordersystem.application.order.command;

import java.util.Objects;
import java.util.UUID;

public record CancelOrderCommand(UUID orderId) {

    public CancelOrderCommand {
        Objects.requireNonNull(orderId, "orderId cannot be null");
    }
}
