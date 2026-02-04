package dev.imdmk.ordersystem.application.order.command;

import java.util.Objects;
import java.util.UUID;

public record PayOrderCommand(
        UUID orderId
) {
    public PayOrderCommand {
        Objects.requireNonNull(orderId, "orderId must not be null");
    }
}
