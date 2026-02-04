package dev.imdmk.ordersystem.infrastructure.subscription.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaSubscriptionRepository
        extends JpaRepository<SubscriptionEntity, UUID> {

    Optional<SubscriptionEntity> findByOrderId(UUID orderId);

    List<SubscriptionEntity> findByStatus(SubscriptionStatusEntity status);

    List<SubscriptionEntity> findByStatusAndExpiresAtIsNotNullAndExpiresAtBefore(
            SubscriptionStatusEntity status,
            Instant expiresAt
    );
}

