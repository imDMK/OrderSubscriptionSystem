package dev.imdmk.ordersystem.infrastructure.order.persistence;

import dev.imdmk.ordersystem.domain.order.Money;
import dev.imdmk.ordersystem.domain.order.Order;
import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.order.OrderItem;
import dev.imdmk.ordersystem.domain.order.state.CancelledOrder;
import dev.imdmk.ordersystem.domain.order.state.NewOrder;
import dev.imdmk.ordersystem.domain.order.state.OrderState;
import dev.imdmk.ordersystem.domain.order.state.PaidOrder;

import java.util.List;

public final class OrderEntityMapper {

    private OrderEntityMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Order toDomain(OrderEntity entity) {
        final List<OrderItem> items = entity.getItems().stream()
                .map(e -> new OrderItem(
                        e.getProductId(),
                        e.getQuantity(),
                        new Money(e.getUnitPrice())
                ))
                .toList();

        final OrderState state = switch (entity.getStatus()) {
            case NEW -> new NewOrder();
            case PAID -> new PaidOrder();
            case CANCELLED -> new CancelledOrder();
        };

        return Order.from(
                new OrderId(entity.getId()),
                items,
                new Money(entity.getTotal()),
                state
        );
    }

    public static OrderEntity toEntity(Order order) {
        final OrderEntity entity = new OrderEntity(
                order.getId().value(),
                mapStatus(order.getState()),
                order.getTotal().amount()
        );

        order.getItems().forEach(item -> {
            final OrderItemEntity itemEntity = new OrderItemEntity(
                    item.productId(),
                    item.quantity(),
                    item.unitPrice().amount(),
                    entity
            );
            entity.addItem(itemEntity);
        });

        return entity;
    }

    private static OrderStatusEntity mapStatus(OrderState state) {
        if (state instanceof NewOrder) return OrderStatusEntity.NEW;
        if (state instanceof PaidOrder) return OrderStatusEntity.PAID;
        if (state instanceof CancelledOrder) return OrderStatusEntity.CANCELLED;

        throw new IllegalStateException("Unknown OrderState: " + state.getClass());
    }
}
