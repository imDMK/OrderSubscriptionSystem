package dev.imdmk.ordersystem.application.subscription.service;

import dev.imdmk.ordersystem.application.subscription.exception.SubscriptionAlreadyExistsException;
import dev.imdmk.ordersystem.application.subscription.exception.SubscriptionNotFoundException;
import dev.imdmk.ordersystem.application.subscription.policy.SubscriptionPolicy;
import dev.imdmk.ordersystem.application.subscription.repository.SubscriptionRepository;
import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.Expiration;
import dev.imdmk.ordersystem.domain.subscription.Subscription;
import dev.imdmk.ordersystem.domain.subscription.SubscriptionStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    SubscriptionRepository repository;

    @Mock
    SubscriptionPolicy policy;

    @InjectMocks
    SubscriptionService service;

    private final OrderId orderId = new OrderId(UUID.randomUUID());

    @Test
    void shouldStartSubscriptionForOrder() {
        when(repository.findByOrderId(orderId)).thenReturn(Optional.empty());
        when(policy.expirationForNewSubscription(orderId))
                .thenReturn(Expiration.never());
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Subscription subscription = service.startForOrder(orderId);

        assertThat(subscription.getOrderId()).isEqualTo(orderId);
        verify(repository).save(any());
    }

    @Test
    void shouldThrowWhenSubscriptionAlreadyExists() {
        when(repository.findByOrderId(orderId))
                .thenReturn(Optional.of(mock(Subscription.class)));

        assertThatThrownBy(() -> service.startForOrder(orderId))
                .isInstanceOf(SubscriptionAlreadyExistsException.class);
    }

    @Test
    void shouldCancelSubscription() {
        Subscription subscription = Subscription.start(orderId, Expiration.never());
        when(repository.findByOrderId(orderId)).thenReturn(Optional.of(subscription));

        service.cancelForOrder(orderId);

        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.CANCELLED);
        verify(repository).save(subscription);
    }

    @Test
    void shouldThrowWhenCancelingNonExistingSubscription() {
        when(repository.findByOrderId(orderId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.cancelForOrder(orderId))
                .isInstanceOf(SubscriptionNotFoundException.class);
    }
}

