package dev.imdmk.ordersystem.application.subscription.repository;

import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.Subscription;
import dev.imdmk.ordersystem.domain.subscription.SubscriptionId;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository {

    Optional<Subscription> findByOrderId(OrderId orderId);
    Optional<Subscription> findBySubscriptionId(SubscriptionId subscriptionId);

    List<Subscription> findActiveSubscriptions();
    List<Subscription> findActiveSubscriptionsExpiringBefore(Instant instant);

    Subscription save(Subscription subscription);
}

