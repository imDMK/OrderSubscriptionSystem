package dev.imdmk.ordersystem.application.order.command;

import dev.imdmk.ordersystem.domain.order.Money;

import java.util.List;
import java.util.Objects;

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
            Objects.requireNonNull(productId, "productId must not be null");
            if (quantity <= 0) {
                throw new IllegalArgumentException("Order item quantity must be greater than zero");
            }
            Objects.requireNonNull(price, "price must not be null");
        }
    }
}



