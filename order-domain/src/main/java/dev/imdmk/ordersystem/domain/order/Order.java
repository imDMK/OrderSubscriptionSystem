package dev.imdmk.ordersystem.domain.order;

import dev.imdmk.ordersystem.domain.common.AggregateRoot;
import dev.imdmk.ordersystem.domain.order.event.OrderCancelEvent;
import dev.imdmk.ordersystem.domain.order.event.OrderPaidEvent;
import dev.imdmk.ordersystem.domain.order.state.CancelledOrder;
import dev.imdmk.ordersystem.domain.order.state.NewOrder;
import dev.imdmk.ordersystem.domain.order.state.OrderState;
import dev.imdmk.ordersystem.domain.order.state.PaidOrder;

import java.util.List;
import java.util.Objects;

public final class Order extends AggregateRoot {

    private final OrderId id;
    private final List<OrderItem> items;
    private final Money total;
    private OrderState state;

    private Order(
            OrderId id,
            List<OrderItem> items,
            Money total,
            OrderState state
    ) {
        this.id = Objects.requireNonNull(id, "id");
        this.items = List.copyOf(items);
        this.total = Objects.requireNonNull(total, "total");
        this.state = Objects.requireNonNull(state, "state");
    }

    public static Order create(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain items");
        }

        final Money total = items.stream()
                .map(OrderItem::total)
                .reduce(Money.zero(), Money::add);

        return new Order(
                OrderId.newId(),
                items,
                total,
                new NewOrder()
        );
    }

    public static Order from(
            OrderId id,
            List<OrderItem> items,
            Money total,
            OrderState state
    ) {
        return new Order(id, items, total, state);
    }

    public void pay() {
        this.state = state.pay();
        registerEvent(OrderPaidEvent.now(id));
    }

    public void cancel() {
        this.state = state.cancel();
        registerEvent(OrderCancelEvent.now(id));
    }

    public boolean isPaid() {
        return state instanceof PaidOrder;
    }

    public boolean isCancelled() {
        return state instanceof CancelledOrder;
    }

    public OrderId getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public Money getTotal() {
        return total;
    }

    public OrderState getState() {
        return state;
    }
}
