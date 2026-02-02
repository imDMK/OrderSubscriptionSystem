package dev.imdmk.ordersystem.domain.order.exception;

public final class OrderAlreadyPaidException extends RuntimeException {
    public OrderAlreadyPaidException() {
        super("Order already paid");
    }
}

