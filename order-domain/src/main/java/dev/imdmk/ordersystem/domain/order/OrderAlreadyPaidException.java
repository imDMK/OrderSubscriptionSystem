package dev.imdmk.ordersystem.domain.order;

public class OrderAlreadyPaidException extends RuntimeException {
    public OrderAlreadyPaidException(OrderId id) {
        super("Order already paid: " + id.value());
    }
}

