package dev.imdmk.ordersystem.application.order.command;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PayOrderCommandTest {

    @Test
    void shouldNotAllowNullOrderId() {
        assertThatThrownBy(() ->
                new PayOrderCommand(null)
        ).isInstanceOf(NullPointerException.class);
    }
}

