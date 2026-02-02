package dev.imdmk.ordersystem.domain.order;

import dev.imdmk.ordersystem.domain.event.DomainEvent;
import dev.imdmk.ordersystem.domain.order.event.OrderCancelEvent;
import dev.imdmk.ordersystem.domain.order.event.OrderPaidEvent;
import dev.imdmk.ordersystem.domain.order.exception.CannotCancelPaidOrderException;
import dev.imdmk.ordersystem.domain.order.exception.OrderAlreadyCancelledException;
import dev.imdmk.ordersystem.domain.order.exception.OrderAlreadyPaidException;
import dev.imdmk.ordersystem.domain.order.state.OrderState;
import dev.imdmk.ordersystem.domain.order.state.PaidOrder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest {

    private static List<OrderItem> sampleItems() {
        return List.of(
                new OrderItem("P-1", 2, Money.from("10.00")),
                new OrderItem("P-2", 1, Money.from("5.50"))
        );
    }


    @Test
    void shouldCreateOrderWithCalculatedTotal() {
        Order order = Order.create(sampleItems());

        assertThat(order.getTotal())
                .isEqualTo(Money.from("25.50"));
    }

    @Test
    void shouldNotAllowCreatingOrderWithoutItems() {
        assertThatThrownBy(() -> Order.create(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void shouldPayNewOrder() {
        Order order = Order.create(sampleItems());

        order.pay();

        assertThat(order.isPaid()).isTrue();
    }

    @Test
    void shouldEmitOrderPaidEventWhenPaid() {
        Order order = Order.create(sampleItems());

        order.pay();

        List<DomainEvent> events = order.pullDomainEvents();

        assertThat(events)
                .hasSize(1)
                .first()
                .isInstanceOf(OrderPaidEvent.class);
    }

    @Test
    void shouldNotPayAlreadyPaidOrder() {
        Order order = Order.create(sampleItems());
        order.pay();

        assertThatThrownBy(order::pay)
                .isInstanceOf(OrderAlreadyPaidException.class);
    }

    @Test
    void shouldCancelNewOrder() {
        Order order = Order.create(sampleItems());

        order.cancel();

        assertThat(order.isCancelled()).isTrue();
    }

    @Test
    void shouldEmitOrderCancelEventWhenCancelled() {
        Order order = Order.create(sampleItems());

        order.cancel();

        List<DomainEvent> events = order.pullDomainEvents();

        assertThat(events)
                .hasSize(1)
                .first()
                .isInstanceOf(OrderCancelEvent.class);
    }

    @Test
    void shouldNotCancelPaidOrder() {
        Order order = Order.create(sampleItems());
        order.pay();

        assertThatThrownBy(order::cancel)
                .isInstanceOf(CannotCancelPaidOrderException.class);
    }

    @Test
    void shouldNotPayCancelledOrder() {
        Order order = Order.create(sampleItems());
        order.cancel();

        assertThatThrownBy(order::pay)
                .isInstanceOf(OrderAlreadyCancelledException.class);
    }

    @Test
    void shouldClearDomainEventsAfterPull() {
        Order order = Order.create(sampleItems());
        order.pay();

        List<DomainEvent> firstPull = order.pullDomainEvents();
        List<DomainEvent> secondPull = order.pullDomainEvents();

        assertThat(firstPull).hasSize(1);
        assertThat(secondPull).isEmpty();
    }

    @Test
    void shouldNotEmitEventsWhenReconstructedFromState() {
        Order order = Order.from(
                OrderId.newId(),
                sampleItems(),
                Money.from("25.50"),
                new PaidOrder()
        );

        assertThat(order.pullDomainEvents()).isEmpty();
        assertThat(order.isPaid()).isTrue();
    }

    @Test
    void totalShouldAlwaysMatchSumOfItems() {
        Order order = Order.create(sampleItems());

        Money calculated = order.getItems().stream()
                .map(OrderItem::total)
                .reduce(Money.zero(), Money::add);

        assertThat(order.getTotal()).isEqualTo(calculated);
    }

    @Test
    void shouldProtectItemsFromExternalModification() {
        Order order = Order.create(sampleItems());

        assertThatThrownBy(() ->
                order.getItems().add(new OrderItem("X", 1, Money.from("1.00")))
        ).isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void payingOrderShouldReplaceStateInstance() {
        Order order = Order.create(sampleItems());
        OrderState before = order.getState();

        order.pay();

        OrderState after = order.getState();

        assertThat(after).isNotSameAs(before);
        assertThat(after).isInstanceOf(PaidOrder.class);
    }
}
