package dev.imdmk.ordersystem.application.order.service;

import dev.imdmk.ordersystem.application.event.DomainEventPublisher;
import dev.imdmk.ordersystem.application.order.command.CreateOrderCommand;
import dev.imdmk.ordersystem.application.order.command.PayOrderCommand;
import dev.imdmk.ordersystem.application.order.exception.OrderNotFoundException;
import dev.imdmk.ordersystem.application.order.repository.OrderRepository;
import dev.imdmk.ordersystem.domain.event.DomainEvent;
import dev.imdmk.ordersystem.domain.order.Money;
import dev.imdmk.ordersystem.domain.order.Order;
import dev.imdmk.ordersystem.domain.order.OrderId;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    OrderRepository repository = mock(OrderRepository.class);
    DomainEventPublisher publisher = mock(DomainEventPublisher.class);

    OrderService service = new OrderService(repository, publisher);

    @Test
    void shouldCreateOrderAndPersistIt() {
        CreateOrderCommand command = new CreateOrderCommand(List.of(
                new CreateOrderCommand.Item("P-1", 2, Money.from("10.00"))
        ));

        UUID id = service.createOrder(command);

        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(repository).save(captor.capture());

        Order saved = captor.getValue();
        assertThat(saved.getId().value()).isEqualTo(id);
    }

    @Test
    void shouldPayOrderAndPublishEvent() {
        Order order = Order.create(List.of(
                new dev.imdmk.ordersystem.domain.order.OrderItem("P-1", 1, Money.from("10.00"))
        ));

        when(repository.findById(order.getId()))
                .thenReturn(Optional.of(order));

        service.pay(new PayOrderCommand(order.getId().value()));

        verify(repository).save(order);
        verify(publisher, atLeastOnce()).publish(any(DomainEvent.class));
    }

    @Test
    void shouldThrowWhenPayingNonExistingOrder() {
        UUID id = UUID.randomUUID();

        when(repository.findById(new OrderId(id)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                service.pay(new PayOrderCommand(id))
        ).isInstanceOf(OrderNotFoundException.class);

        verifyNoInteractions(publisher);
    }
}
