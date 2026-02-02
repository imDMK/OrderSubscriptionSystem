package dev.imdmk.ordersystem.bootstrap.config;

import dev.imdmk.ordersystem.application.order.repository.OrderRepository;
import dev.imdmk.ordersystem.application.order.service.OrderService;
import dev.imdmk.ordersystem.infrastructure.order.adapter.OrderRepositoryAdapter;
import dev.imdmk.ordersystem.infrastructure.order.persistence.JpaOrderRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    public OrderRepository orderRepository(JpaOrderRepository jpaRepository) {
        return new OrderRepositoryAdapter(jpaRepository);
    }

    @Bean
    public OrderService orderService(OrderRepository repository) {
        return new OrderService(repository);
    }
}

