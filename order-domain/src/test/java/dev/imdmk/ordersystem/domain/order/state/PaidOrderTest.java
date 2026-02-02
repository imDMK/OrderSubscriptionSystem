package dev.imdmk.ordersystem.domain.order.state;

import dev.imdmk.ordersystem.domain.order.exception.CannotCancelPaidOrderException;
import dev.imdmk.ordersystem.domain.order.exception.OrderAlreadyPaidException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PaidOrderTest {

    @Test
    void shouldNotAllowPayingAlreadyPaidOrder() {
        OrderState state = new PaidOrder();

        assertThatThrownBy(state::pay)
                .isInstanceOf(OrderAlreadyPaidException.class);
    }

    @Test
    void shouldNotAllowCancellingPaidOrder() {
        OrderState state = new PaidOrder();

        assertThatThrownBy(state::cancel)
                .isInstanceOf(CannotCancelPaidOrderException.class);
    }
}
