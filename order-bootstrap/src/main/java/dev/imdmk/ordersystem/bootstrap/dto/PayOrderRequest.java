package dev.imdmk.ordersystem.bootstrap.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PayOrderRequest(
        @NotNull UUID orderId
) {}

