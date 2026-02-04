package dev.imdmk.ordersystem.domain.subscription;

import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.event.SubscriptionExpiredEvent;
import dev.imdmk.ordersystem.domain.subscription.event.SubscriptionStartedEvent;
import dev.imdmk.ordersystem.domain.subscription.exception.CannotExpirePermanentSubscriptionException;
import dev.imdmk.ordersystem.domain.subscription.exception.SubscriptionAlreadyExpiredException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class SubscriptionTest {

    private final OrderId orderId = new OrderId(UUID.randomUUID());

    @Test
    void shouldStartSubscriptionAndEmitEvent() {
        Subscription subscription = Subscription.start(orderId, Expiration.never());

        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.ACTIVE);
        assertThat(subscription.pullDomainEvents())
                .hasSize(1)
                .first()
                .isInstanceOf(SubscriptionStartedEvent.class);
    }

    @Test
    void shouldCancelActiveSubscription() {
        Subscription subscription = Subscription.start(orderId, Expiration.never());

        subscription.cancel();

        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.CANCELLED);
        assertThat(subscription.pullDomainEvents())
                .hasSize(2); // started + cancelled
    }

    @Test
    void shouldExpireTimeLimitedSubscription() {
        Subscription subscription = Subscription.start(
                orderId,
                Expiration.until(Instant.now().minusSeconds(10))
        );

        subscription.expire();

        assertThat(subscription.getStatus()).isEqualTo(SubscriptionStatus.EXPIRED);
        assertThat(subscription.pullDomainEvents())
                .anyMatch(e -> e instanceof SubscriptionExpiredEvent);
    }

    @Test
    void shouldNotExpirePermanentSubscription() {
        Subscription subscription = Subscription.start(orderId, Expiration.never());

        assertThatThrownBy(subscription::expire)
                .isInstanceOf(CannotExpirePermanentSubscriptionException.class);
    }

    @Test
    void shouldNotExpireAlreadyExpiredSubscription() {
        Subscription subscription = Subscription.start(
                orderId,
                Expiration.until(Instant.now().minusSeconds(10))
        );
        subscription.expire();

        assertThatThrownBy(subscription::expire)
                .isInstanceOf(SubscriptionAlreadyExpiredException.class);
    }
}

