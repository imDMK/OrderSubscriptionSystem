package dev.imdmk.ordersystem.application.subscription.service;

import dev.imdmk.ordersystem.application.subscription.repository.SubscriptionRepository;
import dev.imdmk.ordersystem.domain.subscription.Subscription;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

public final class SubscriptionExpirationService {

    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionExpirationService(
            SubscriptionRepository subscriptionRepository
    ) {
        this.subscriptionRepository = Objects.requireNonNull(subscriptionRepository, "subscriptionRepository must not be null");
    }

    public void expireDueSubscriptions(Instant now) {
        Objects.requireNonNull(now, "now must not be null");

        final List<Subscription> dueSubscriptions = subscriptionRepository.findActiveSubscriptionsExpiringBefore(now);
        for (final Subscription subscription : dueSubscriptions) {
            subscription.expire();
            subscriptionRepository.save(subscription);
        }
    }
}
