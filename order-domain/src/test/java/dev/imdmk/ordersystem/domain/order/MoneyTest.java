package dev.imdmk.ordersystem.domain.order;

import dev.imdmk.ordersystem.domain.order.exception.InvalidMoneyFormatException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MoneyTest {

    @Test
    void shouldCreateMoneyFromValidString() {
        Money money = Money.from("10.50");

        assertThat(money.amount()).isEqualByComparingTo("10.50");
    }

    @Test
    void shouldThrowExceptionForInvalidMoneyFormat() {
        assertThatThrownBy(() -> Money.from("abc"))
                .isInstanceOf(InvalidMoneyFormatException.class)
                .hasMessageContaining("abc");
    }

    @Test
    void shouldNotAllowNegativeMoney() {
        assertThatThrownBy(() -> new Money(new BigDecimal("-1.00")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("negative");
    }

    @Test
    void shouldNotAllowMoreThanTwoDecimalPlaces() {
        assertThatThrownBy(() -> new Money(new BigDecimal("1.123")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("scale");
    }

    @Test
    void shouldAddTwoMoneyValues() {
        Money a = Money.from("10.00");
        Money b = Money.from("5.50");

        Money result = a.add(b);

        assertThat(result.amount()).isEqualByComparingTo("15.50");
    }

    @Test
    void shouldMultiplyMoneyByPositiveInteger() {
        Money money = Money.from("2.50");

        Money result = money.multiply(4);

        assertThat(result.amount()).isEqualByComparingTo("10.00");
    }

    @Test
    void shouldNotAllowMultiplyByZeroOrNegative() {
        Money money = Money.from("10.00");

        assertThatThrownBy(() -> money.multiply(0))
                .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> money.multiply(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void zeroMoneyShouldBeZero() {
        assertThat(Money.zero().amount())
                .isEqualByComparingTo("0");
    }
}

