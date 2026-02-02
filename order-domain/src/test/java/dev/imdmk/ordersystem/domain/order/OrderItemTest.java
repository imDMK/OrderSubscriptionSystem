package dev.imdmk.ordersystem.domain.order;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderItemTest {

    @Test
    void shouldCreateOrderItemWithValidData() {
        OrderItem item = new OrderItem("P-1", 2, Money.from("10.00"));

        assertThat(item.productId()).isEqualTo("P-1");
        assertThat(item.quantity()).isEqualTo(2);
        assertThat(item.unitPrice()).isEqualTo(Money.from("10.00"));
    }

    @Test
    void shouldCalculateTotalPrice() {
        OrderItem item = new OrderItem("P-1", 3, Money.from("5.50"));

        Money total = item.total();

        assertThat(total.amount()).isEqualByComparingTo("16.50");
    }

    @Test
    void shouldNotAllowZeroOrNegativeQuantity() {
        assertThatThrownBy(() ->
                new OrderItem("P-1", 0, Money.from("10.00"))
        ).isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() ->
                new OrderItem("P-1", -1, Money.from("10.00"))
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldNotAllowNullProductId() {
        assertThatThrownBy(() ->
                new OrderItem(null, 1, Money.from("10.00"))
        ).isInstanceOf(NullPointerException.class);
    }

    @Test
    void shouldNotAllowNullUnitPrice() {
        assertThatThrownBy(() ->
                new OrderItem("P-1", 1, null)
        ).isInstanceOf(NullPointerException.class);
    }
}

