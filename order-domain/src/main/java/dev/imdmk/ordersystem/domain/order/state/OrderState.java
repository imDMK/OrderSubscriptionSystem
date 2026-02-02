package dev.imdmk.ordersystem.domain.order.state;

public sealed interface OrderState
        permits NewOrder, PaidOrder, CancelledOrder {

    OrderState pay();
    OrderState cancel();
}



