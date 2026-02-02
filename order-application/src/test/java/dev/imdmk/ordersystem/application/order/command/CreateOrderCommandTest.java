package dev.imdmk.ordersystem.application.order.command;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class CreateOrderCommandTest {

    @Test
    void shouldNotAllowEmptyItems() {
        assertThatThrownBy(() ->
                new CreateOrderCommand(List.of())
        ).isInstanceOf(IllegalArgumentException.class);
    }
}

