package dev.imdmk.ordersystem.application.order.command;

import dev.imdmk.ordersystem.domain.order.Money;

import java.util.List;

public record CreateOrderCommand(
        List<Item> items
) {
    public CreateOrderCommand {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("OrderCommand must contain at least one item");
        }
    }

    public record Item(
            String productId,
            int quantity,
            Money price
    ) {
        public Item {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Order item quantity must be greater than zero");
            }
        }
    }
}



