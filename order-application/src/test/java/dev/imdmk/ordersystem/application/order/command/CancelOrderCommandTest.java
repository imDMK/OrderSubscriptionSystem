package dev.imdmk.ordersystem.application.order.command;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CancelOrderCommandTest {

    @Test
    void shouldNotAllowNullOrderId() {
        assertThatThrownBy(() ->
                new CancelOrderCommand(null)
        ).isInstanceOf(NullPointerException.class);
    }
}

