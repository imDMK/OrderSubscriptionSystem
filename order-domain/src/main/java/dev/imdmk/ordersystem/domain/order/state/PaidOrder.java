package dev.imdmk.ordersystem.domain.order.state;

import dev.imdmk.ordersystem.domain.order.exception.CannotCancelPaidOrderException;
import dev.imdmk.ordersystem.domain.order.exception.OrderAlreadyPaidException;

public final class PaidOrder implements OrderState {

    @Override
    public OrderState pay() {
        throw new OrderAlreadyPaidException();
    }

    @Override
    public OrderState cancel() {
        throw new CannotCancelPaidOrderException();
    }
}
