package dev.imdmk.ordersystem.application.order.service;

import dev.imdmk.ordersystem.application.event.DomainEventPublisher;
import dev.imdmk.ordersystem.application.order.command.CancelOrderCommand;
import dev.imdmk.ordersystem.application.order.command.CreateOrderCommand;
import dev.imdmk.ordersystem.application.order.command.PayOrderCommand;
import dev.imdmk.ordersystem.application.order.exception.OrderNotFoundException;
import dev.imdmk.ordersystem.application.order.repository.OrderRepository;
import dev.imdmk.ordersystem.application.subscription.service.SubscriptionService;
import dev.imdmk.ordersystem.domain.order.Order;
import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.order.OrderItem;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class OrderService {

    private final OrderRepository orderRepository;
    private final SubscriptionService subscriptionService;
    private final DomainEventPublisher domainEventPublisher;

    public OrderService(
            OrderRepository orderRepository,
            SubscriptionService subscriptionService,
            DomainEventPublisher domainEventPublisher
    ) {
        this.orderRepository = Objects.requireNonNull(orderRepository, "orderRepository must not be null");
        this.subscriptionService = Objects.requireNonNull(subscriptionService, "subscriptionService must not be null");
        this.domainEventPublisher = Objects.requireNonNull(domainEventPublisher, "domainEventPublisher must not be null");
    }

    public UUID createOrder(CreateOrderCommand command) {
        final List<OrderItem> items = command.items().stream()
                .map(i -> new OrderItem(
                        i.productId(),
                        i.quantity(),
                        i.price()
                ))
                .toList();

        final Order order = Order.create(items);
        orderRepository.save(order);

        return order.getId().value();
    }

    public void pay(PayOrderCommand command) {
        final OrderId orderId = new OrderId(command.orderId());
        final Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.pay();
        orderRepository.save(order);

        subscriptionService.startForOrder(orderId);

        order.pullDomainEvents()
                .forEach(domainEventPublisher::publish);
    }

    public void cancel(CancelOrderCommand command) {
        final OrderId orderId = new OrderId(command.orderId());
        final Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        order.cancel();
        orderRepository.save(order);

        subscriptionService.cancelForOrder(orderId);

        order.pullDomainEvents()
                .forEach(domainEventPublisher::publish);
    }
}
