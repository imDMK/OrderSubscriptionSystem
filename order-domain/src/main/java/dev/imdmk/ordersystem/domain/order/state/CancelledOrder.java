package dev.imdmk.ordersystem.domain.order.state;

public final class CancelledOrder implements OrderState {

    @Override
    public OrderState pay() {
        throw new IllegalStateException("Cannot pay cancelled order");
    }

    @Override
    public OrderState cancel() {
        throw new IllegalStateException("Order already cancelled");
    }
}

