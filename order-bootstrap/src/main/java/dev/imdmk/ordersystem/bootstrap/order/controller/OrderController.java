package dev.imdmk.ordersystem.bootstrap.order.controller;

import dev.imdmk.ordersystem.application.order.command.CancelOrderCommand;
import dev.imdmk.ordersystem.application.order.command.CreateOrderCommand;
import dev.imdmk.ordersystem.application.order.command.PayOrderCommand;
import dev.imdmk.ordersystem.application.order.service.OrderService;
import dev.imdmk.ordersystem.bootstrap.order.dto.CancelOrderRequest;
import dev.imdmk.ordersystem.bootstrap.order.dto.CreateOrderRequest;
import dev.imdmk.ordersystem.bootstrap.order.dto.OrderResponse;
import dev.imdmk.ordersystem.bootstrap.order.dto.PayOrderRequest;
import dev.imdmk.ordersystem.domain.order.Money;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public final class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = Objects.requireNonNull(service, "service cannot be null");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@RequestBody @Valid CreateOrderRequest request) {
        final CreateOrderCommand command = new CreateOrderCommand(
                request.items().stream()
                        .map(i -> new CreateOrderCommand.Item(
                                i.productId(),
                                i.quantity(),
                                Money.from(i.price())
                        ))
                        .toList()
        );

        final UUID id = service.createOrder(command);
        return new OrderResponse(id);
    }

    @PostMapping("/pay")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pay(@RequestBody @Valid PayOrderRequest request) {
        service.pay(new PayOrderCommand(request.orderId()));
    }

    @PostMapping("/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@RequestBody @Valid CancelOrderRequest request) {
        service.cancel(new CancelOrderCommand(request.orderId()));
    }
}

