package dev.imdmk.ordersystem.application.order.repository;

import dev.imdmk.ordersystem.domain.order.Order;
import dev.imdmk.ordersystem.domain.order.OrderId;

import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findByOrderId(OrderId orderId);

    Order save(Order order);
}
