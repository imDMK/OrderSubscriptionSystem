package dev.imdmk.ordersystem.application.order.command;

import java.util.UUID;

public record PayOrderCommand(
        UUID orderId
) {
    public PayOrderCommand {
        if (orderId == null) {
            throw new IllegalArgumentException("OrderId cannot be null");
        }
    }
}
