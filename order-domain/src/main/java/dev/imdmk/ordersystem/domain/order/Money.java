package dev.imdmk.ordersystem.domain.order;

import dev.imdmk.ordersystem.domain.order.exception.InvalidMoneyFormatException;

import java.math.BigDecimal;
import java.util.Objects;

public record Money(BigDecimal amount) {

    public Money {
        Objects.requireNonNull(amount, "amount must not be null");
        if (amount.scale() > 2) {
            throw new IllegalArgumentException("Money scale cannot exceed 2 decimal places");
        }
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }
    }

    public static Money from(String value) {
        Objects.requireNonNull(value, "value must not be null");
        try {
            return new Money(new BigDecimal(value));
        } catch (RuntimeException e) {
            throw new InvalidMoneyFormatException(value);
        }
    }

    public static Money zero() {
        return new Money(BigDecimal.ZERO);
    }

    public Money add(Money other) {
        Objects.requireNonNull(other, "other must not be null");
        return new Money(this.amount.add(other.amount));
    }

    public Money multiply(int multiplier) {
        if (multiplier <= 0) {
            throw new IllegalArgumentException("Multiplier must be positive");
        }
        return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)));
    }
}
