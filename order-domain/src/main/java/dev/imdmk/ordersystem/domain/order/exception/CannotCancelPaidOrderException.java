package dev.imdmk.ordersystem.domain.order.exception;

public final class CannotCancelPaidOrderException extends RuntimeException {
    public CannotCancelPaidOrderException() {
        super("Cannot cancel paid order");
    }
}

