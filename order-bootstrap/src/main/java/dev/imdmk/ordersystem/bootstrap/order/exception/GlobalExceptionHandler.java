package dev.imdmk.ordersystem.bootstrap.order.exception;

import dev.imdmk.ordersystem.application.order.exception.OrderNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ApiException> handleOrderNotFound(
            OrderNotFoundException e
    ) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiException.of(404, e.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiException> handleInvalidJson(
            HttpMessageNotReadableException e
    ) {
        LOGGER.debug("Invalid request body", e);

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiException.of(
                        400,
                        "Invalid request body or malformed JSON"
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException> handleUnexpected(
            Exception e
    ) {
        LOGGER.error("Unexpected error", e);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiException.of(
                        500,
                        "Unexpected server error"
                ));
    }
}
