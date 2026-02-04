package dev.imdmk.ordersystem.bootstrap.order.configuration;

import dev.imdmk.ordersystem.application.event.DomainEventPublisher;
import dev.imdmk.ordersystem.application.order.repository.OrderRepository;
import dev.imdmk.ordersystem.application.order.service.OrderService;
import dev.imdmk.ordersystem.application.subscription.service.SubscriptionService;
import dev.imdmk.ordersystem.infrastructure.event.SpringDomainEventPublisher;
import dev.imdmk.ordersystem.infrastructure.order.adapter.OrderRepositoryAdapter;
import dev.imdmk.ordersystem.infrastructure.order.persistence.JpaOrderRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderConfiguration {

    @Bean
    public OrderRepository orderRepository(
            JpaOrderRepository jpaRepository
    ) {
        return new OrderRepositoryAdapter(jpaRepository);
    }

    @Bean
    public DomainEventPublisher domainEventPublisher(
            ApplicationEventPublisher applicationEventPublisher
    ) {
        return new SpringDomainEventPublisher(applicationEventPublisher);
    }

    @Bean
    public OrderService orderService(
            OrderRepository orderRepository,
            SubscriptionService subscriptionService,
            DomainEventPublisher domainEventPublisher
    ) {
        return new OrderService(orderRepository, subscriptionService, domainEventPublisher);
    }
}
