package dev.imdmk.ordersystem.domain.order;

public class OrderAlreadyCancelledException extends RuntimeException {
    public OrderAlreadyCancelledException(OrderId id) {
        super("Order already cancelled: " + id.value());
    }
}
