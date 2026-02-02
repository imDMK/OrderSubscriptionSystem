package dev.imdmk.ordersystem.bootstrap.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(
        @NotEmpty List<Item> items
) {

    public record Item(
            @NotNull String productId,
            int quantity,
            @NotNull String price
    ) {}

}
