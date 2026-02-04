package dev.imdmk.ordersystem.application.subscription.service;

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

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionExpirationServiceTest {

    @Mock
    SubscriptionRepository repository;

    @InjectMocks
    SubscriptionExpirationService service;

    @Test
    void shouldExpireOnlyDueSubscriptions() {
        Subscription expired = Subscription.start(
                new OrderId(UUID.randomUUID()),
                Expiration.until(Instant.now().minusSeconds(10))
        );

        when(repository.findActiveSubscriptionsExpiringBefore(any()))
                .thenReturn(List.of(expired));

        service.expireDueSubscriptions(Instant.now());

        assertThat(expired.getStatus()).isEqualTo(SubscriptionStatus.EXPIRED);
        verify(repository).save(expired);
    }
}

