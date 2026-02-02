package dev.imdmk.ordersystem.domain.order.state;

import dev.imdmk.ordersystem.domain.order.exception.OrderAlreadyCancelledException;

public final class CancelledOrder implements OrderState {

    @Override
    public OrderState pay() {
        throw new OrderAlreadyCancelledException();
    }

    @Override
    public OrderState cancel() {
        throw new OrderAlreadyCancelledException();
    }
}
