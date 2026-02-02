package dev.imdmk.ordersystem.bootstrap.order.exception;

import java.time.Instant;

public record ApiException(
        int status,
        String message,
        Instant timestamp
) {
    public static ApiException of(int status, String message) {
        return new ApiException(status, message, Instant.now());
    }
}

