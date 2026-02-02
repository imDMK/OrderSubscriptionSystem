package dev.imdmk.ordersystem.domain.order.state;

import dev.imdmk.ordersystem.domain.order.exception.OrderAlreadyCancelledException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CancelledOrderTest {

    @Test
    void shouldNotAllowPayingCancelledOrder() {
        OrderState state = new CancelledOrder();

        assertThatThrownBy(state::pay)
                .isInstanceOf(OrderAlreadyCancelledException.class);
    }

    @Test
    void shouldNotAllowCancellingAlreadyCancelledOrder() {
        OrderState state = new CancelledOrder();

        assertThatThrownBy(state::cancel)
                .isInstanceOf(OrderAlreadyCancelledException.class);
    }
}
