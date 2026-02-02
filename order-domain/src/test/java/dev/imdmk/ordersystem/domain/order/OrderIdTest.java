package dev.imdmk.ordersystem.domain.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderIdTest {

    @Test
    void shouldCreateNewUniqueOrderId() {
        OrderId id1 = OrderId.newId();
        OrderId id2 = OrderId.newId();

        assertThat(id1).isNotEqualTo(id2);
        assertThat(id1.value()).isNotNull();
    }

    @Test
    void shouldNotAllowNullValue() {
        assertThatThrownBy(() -> new OrderId(null))
                .isInstanceOf(NullPointerException.class);
    }
}

