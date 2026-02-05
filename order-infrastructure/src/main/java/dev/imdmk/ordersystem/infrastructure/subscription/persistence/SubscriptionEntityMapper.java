package dev.imdmk.ordersystem.infrastructure.subscription.persistence;

import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.Expiration;
import dev.imdmk.ordersystem.domain.subscription.Subscription;
import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;
import dev.imdmk.ordersystem.domain.subscription.SubscriptionStatus;

public final class SubscriptionEntityMapper {

    private SubscriptionEntityMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Subscription toDomain(SubscriptionEntity entity) {
        final Expiration expiration = entity.getExpiresAt() == null
                ? Expiration.never()
                : Expiration.until(entity.getExpiresAt());

        return Subscription.from(
                new SubscriptionId(entity.getId()),
                new OrderId(entity.getOrderId()),
                expiration,
                SubscriptionStatus.valueOf(entity.getStatus().name())
        );
    }

    public static SubscriptionEntity toEntity(Subscription subscription) {
        return new SubscriptionEntity(
                subscription.getId().value(),
                subscription.getOrderId().value(),
                SubscriptionStatusEntity.valueOf(subscription.getStatus().name()),
                subscription.getExpiration().getExpiresAt().orElse(null)
        );
    }
}
