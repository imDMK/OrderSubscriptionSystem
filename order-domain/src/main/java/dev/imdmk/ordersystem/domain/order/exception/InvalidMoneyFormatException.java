package dev.imdmk.ordersystem.domain.order.exception;

public class InvalidMoneyFormatException extends RuntimeException {
    public InvalidMoneyFormatException(String value) {
        super("Invalid money value: " + value);
    }
}

