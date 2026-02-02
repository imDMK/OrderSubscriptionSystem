package dev.imdmk.ordersystem.domain.order;

import java.util.Objects;

public record OrderItem(String productId, int quantity, Money unitPrice) {

    public OrderItem(String productId, int quantity, Money unitPrice) {
        this.productId = Objects.requireNonNull(productId, "productId");
        this.unitPrice = Objects.requireNonNull(unitPrice, "unitPrice");

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = quantity;
    }

    public Money total() {
        return unitPrice.multiply(quantity);
    }
}
