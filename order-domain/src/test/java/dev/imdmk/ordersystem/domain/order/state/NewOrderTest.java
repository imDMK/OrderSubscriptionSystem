package dev.imdmk.ordersystem.domain.order.state;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NewOrderTest {

    @Test
    void shouldTransitionToPaidOrderWhenPaid() {
        OrderState state = new NewOrder();

        OrderState next = state.pay();

        assertThat(next).isInstanceOf(PaidOrder.class);
    }

    @Test
    void shouldReturnNewInstanceWhenStateChanges() {
        OrderState state = new NewOrder();

        OrderState paid = state.pay();
        OrderState cancelled = state.cancel();

        assertThat(paid).isNotSameAs(state);
        assertThat(cancelled).isNotSameAs(state);
    }

    @Test
    void shouldTransitionToCancelledOrderWhenCancelled() {
        OrderState state = new NewOrder();

        OrderState next = state.cancel();

        assertThat(next).isInstanceOf(CancelledOrder.class);
    }
}

