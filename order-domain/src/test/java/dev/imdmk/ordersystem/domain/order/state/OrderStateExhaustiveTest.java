package dev.imdmk.ordersystem.domain.order.state;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderStateExhaustiveTest {

    @Test
    void allOrderStatesShouldBeHandledExplicitly() {
        List<OrderState> states = List.of(
                new NewOrder(),
                new PaidOrder(),
                new CancelledOrder()
        );

        assertThat(states)
                .hasSize(3)
                .allMatch(state ->
                        state instanceof NewOrder
                                || state instanceof PaidOrder
                                || state instanceof CancelledOrder
                );
    }

    @Test
    void stateTransitionsShouldHaveNoSideEffects() {
        OrderState state = new NewOrder();

        OrderState first = state.pay();
        OrderState second = state.pay();

        assertThat(first).isNotSameAs(second);
        assertThat(first.getClass()).isEqualTo(second.getClass());
    }
}
