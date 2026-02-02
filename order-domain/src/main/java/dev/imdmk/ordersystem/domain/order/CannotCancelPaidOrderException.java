package dev.imdmk.ordersystem.domain.order;

public class CannotCancelPaidOrderException extends RuntimeException {
    public CannotCancelPaidOrderException(OrderId id) {
        super("Cannot cancel paid order: " + id.value());
    }
}
