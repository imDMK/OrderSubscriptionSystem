package dev.imdmk.ordersystem.bootstrap.controller;

import dev.imdmk.ordersystem.application.order.command.CreateOrderCommand;
import dev.imdmk.ordersystem.application.order.command.PayOrderCommand;
import dev.imdmk.ordersystem.application.order.service.OrderService;
import dev.imdmk.ordersystem.bootstrap.dto.CreateOrderRequest;
import dev.imdmk.ordersystem.bootstrap.dto.OrderResponse;
import dev.imdmk.ordersystem.bootstrap.dto.PayOrderRequest;
import dev.imdmk.ordersystem.domain.order.Money;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponse create(@RequestBody @Valid CreateOrderRequest request) {
        var command = new CreateOrderCommand(
                request.items().stream()
                        .map(i -> new CreateOrderCommand.Item(
                                i.productId(),
                                i.quantity(),
                                Money.from(i.price())
                        ))
                        .toList()
        );

        var id = service.createOrder(command);
        return new OrderResponse(id);
    }

    @PostMapping("/pay")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void pay(@RequestBody @Valid PayOrderRequest request) {
        service.pay(new PayOrderCommand(request.orderId()));
    }
}

