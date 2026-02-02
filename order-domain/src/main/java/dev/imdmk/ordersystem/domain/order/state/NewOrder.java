package dev.imdmk.ordersystem.domain.order.state;

public final class NewOrder implements OrderState {

    @Override
    public OrderState pay() {
        return new PaidOrder();
    }

    @Override
    public OrderState cancel() {
        return new CancelledOrder();
    }
}

