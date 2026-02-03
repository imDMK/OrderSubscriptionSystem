package dev.imdmk.ordersystem.infrastructure.order.persistence;

import dev.imdmk.ordersystem.domain.order.Money;
import dev.imdmk.ordersystem.domain.order.Order;
import dev.imdmk.ordersystem.domain.order.OrderItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderEntityMapperTest {

    @Test
    void shouldMapDomainOrderToEntityAndBack() {
        Order order = Order.create(List.of(
                new OrderItem("P-1", 2, Money.from("10.00")),
                new OrderItem("P-2", 1, Money.from("5.50"))
        ));

        OrderEntity entity = OrderEntityMapper.toEntity(order);
        Order mappedBack = OrderEntityMapper.toDomain(entity);

        assertThat(mappedBack.getId()).isEqualTo(order.getId());
        assertThat(mappedBack.getTotal()).isEqualTo(order.getTotal());
        assertThat(mappedBack.getState().getClass())
                .isEqualTo(order.getState().getClass());
    }

    @Test
    void shouldMapPaidOrderStateCorrectly() {
        Order order = Order.create(List.of(
                new OrderItem("P-1", 1, Money.from("10.00"))
        ));
        order.pay();

        OrderEntity entity = OrderEntityMapper.toEntity(order);

        assertThat(entity.getStatus()).isEqualTo(OrderStatusEntity.PAID);
    }
}

