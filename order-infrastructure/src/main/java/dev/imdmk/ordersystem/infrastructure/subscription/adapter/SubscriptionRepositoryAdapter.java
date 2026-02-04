package dev.imdmk.ordersystem.infrastructure.subscription.adapter;

import dev.imdmk.ordersystem.application.subscription.repository.SubscriptionRepository;
import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.Subscription;
import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;
import dev.imdmk.ordersystem.infrastructure.subscription.persistence.JpaSubscriptionRepository;
import dev.imdmk.ordersystem.infrastructure.subscription.persistence.SubscriptionEntity;
import dev.imdmk.ordersystem.infrastructure.subscription.persistence.SubscriptionEntityMapper;
import dev.imdmk.ordersystem.infrastructure.subscription.persistence.SubscriptionStatusEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Transactional
public class SubscriptionRepositoryAdapter
        implements SubscriptionRepository {

    private final JpaSubscriptionRepository jpaRepository;

    public SubscriptionRepositoryAdapter(
            JpaSubscriptionRepository jpaRepository
    ) {
        this.jpaRepository = Objects.requireNonNull(jpaRepository, "jpaRepository must not be null");
    }

    @Override
    public Optional<Subscription> findByOrderId(OrderId orderId) {
        return jpaRepository.findByOrderId(orderId.value())
                .map(SubscriptionEntityMapper::toDomain);
    }

    @Override
    public Optional<Subscription> findBySubscriptionId(
            SubscriptionId subscriptionId
    ) {
        return jpaRepository.findById(subscriptionId.value())
                .map(SubscriptionEntityMapper::toDomain);
    }

    @Override
    public List<Subscription> findActiveSubscriptions() {
        return jpaRepository.findByStatus(SubscriptionStatusEntity.ACTIVE).stream()
                .map(SubscriptionEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Subscription> findActiveSubscriptionsExpiringBefore(
            Instant instant
    ) {
        return jpaRepository
                .findByStatusAndExpiresAtIsNotNullAndExpiresAtBefore(
                        SubscriptionStatusEntity.ACTIVE,
                        instant
                ).stream()
                .map(SubscriptionEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Subscription save(Subscription subscription) {
        final SubscriptionEntity entity = SubscriptionEntityMapper.toEntity(subscription);
        final SubscriptionEntity saved = jpaRepository.save(entity);
        return SubscriptionEntityMapper.toDomain(saved);
    }
}

