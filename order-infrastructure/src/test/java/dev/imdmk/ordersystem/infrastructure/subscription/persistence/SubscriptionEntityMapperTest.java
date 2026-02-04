package dev.imdmk.ordersystem.infrastructure.subscription.persistence;

import dev.imdmk.ordersystem.domain.subscription.Subscription;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SubscriptionEntityMapperTest {

    @Test
    void shouldMapEntityToDomainAndBack() {
        UUID id = UUID.randomUUID();
        UUID orderId = UUID.randomUUID();
        Instant expiresAt = Instant.now();

        SubscriptionEntity entity = new SubscriptionEntity(
                id,
                orderId,
                SubscriptionStatusEntity.ACTIVE,
                expiresAt
        );

        Subscription domain = SubscriptionEntityMapper.toDomain(entity);

        assertThat(domain.getId().value()).isEqualTo(id);
        assertThat(domain.getOrderId().value()).isEqualTo(orderId);
        assertThat(domain.getExpiration().getExpiresAt()).contains(expiresAt);

        SubscriptionEntity mappedBack = SubscriptionEntityMapper.toEntity(domain);
        assertThat(mappedBack.getExpiresAt()).isEqualTo(expiresAt);
    }
}

