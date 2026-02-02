package dev.imdmk.ordersystem.domain.order.state;

public final class PaidOrder implements OrderState {

    @Override
    public OrderState pay() {
        throw new IllegalStateException("Order already paid");
    }

    @Override
    public OrderState cancel() {
        throw new IllegalStateException("Cannot cancel paid order");
    }
}

