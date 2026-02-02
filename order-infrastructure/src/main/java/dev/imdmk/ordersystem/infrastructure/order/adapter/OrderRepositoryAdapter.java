package dev.imdmk.ordersystem.infrastructure.order.adapter;

import dev.imdmk.ordersystem.application.order.repository.OrderRepository;
import dev.imdmk.ordersystem.domain.order.Order;
import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.infrastructure.order.persistence.JpaOrderRepository;
import dev.imdmk.ordersystem.infrastructure.order.persistence.OrderEntity;
import dev.imdmk.ordersystem.infrastructure.order.persistence.OrderEntityMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public class OrderRepositoryAdapter implements OrderRepository {

    private final JpaOrderRepository jpaRepository;

    public OrderRepositoryAdapter(JpaOrderRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Order save(Order order) {
        final OrderEntity entity = OrderEntityMapper.toEntity(order);
        final OrderEntity saved = jpaRepository.save(entity);
        return OrderEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(OrderId id) {
        return jpaRepository.findById(id.value())
                .map(OrderEntityMapper::toDomain);
    }
}
