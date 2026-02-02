package dev.imdmk.ordersystem.application.order.service;

import dev.imdmk.ordersystem.application.order.command.CreateOrderCommand;
import dev.imdmk.ordersystem.application.order.command.PayOrderCommand;
import dev.imdmk.ordersystem.application.order.exception.OrderNotFoundException;
import dev.imdmk.ordersystem.application.order.repository.OrderRepository;
import dev.imdmk.ordersystem.domain.order.Order;
import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.order.OrderItem;

import java.util.List;
import java.util.UUID;

public final class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public UUID createOrder(CreateOrderCommand command) {

        List<OrderItem> items = command.items().stream()
                .map(i -> new OrderItem(
                        i.productId(),
                        i.quantity(),
                        i.price()
                ))
                .toList();

        Order order = Order.create(items);
        orderRepository.save(order);

        return order.getId().value();
    }

    public void pay(PayOrderCommand command) {
        OrderId orderId = new OrderId(command.orderId());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.pay();
        orderRepository.save(order);
    }
}
