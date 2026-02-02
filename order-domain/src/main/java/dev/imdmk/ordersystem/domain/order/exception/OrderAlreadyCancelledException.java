package dev.imdmk.ordersystem.domain.order.exception;

public final class OrderAlreadyCancelledException extends RuntimeException {
    public OrderAlreadyCancelledException() {
        super("Order already cancelled");
    }
}
