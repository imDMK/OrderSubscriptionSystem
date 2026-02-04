package dev.imdmk.ordersystem.application.subscription.service;

import dev.imdmk.ordersystem.application.subscription.exception.SubscriptionAlreadyExistsException;
import dev.imdmk.ordersystem.application.subscription.exception.SubscriptionNotFoundException;
import dev.imdmk.ordersystem.application.subscription.policy.SubscriptionPolicy;
import dev.imdmk.ordersystem.application.subscription.repository.SubscriptionRepository;
import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.Expiration;
import dev.imdmk.ordersystem.domain.subscription.Subscription;

import java.util.Objects;

public final class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionPolicy subscriptionPolicy;

    public SubscriptionService(
            SubscriptionRepository subscriptionRepository,
            SubscriptionPolicy subscriptionPolicy
    ) {
        this.subscriptionRepository = Objects.requireNonNull(subscriptionRepository, "subscriptionRepository must not be null");
        this.subscriptionPolicy = Objects.requireNonNull(subscriptionPolicy, "subscriptionPolicy must not be null");
    }

    public Subscription startForOrder(OrderId orderId) {
        Objects.requireNonNull(orderId, "orderId must not be null");

        subscriptionRepository.findByOrderId(orderId)
                .ifPresent(existing -> {
                    throw new SubscriptionAlreadyExistsException(orderId);
                });

        final Expiration expiration = subscriptionPolicy.expirationForNewSubscription(orderId);
        final Subscription subscription = Subscription.start(orderId, expiration);
        return subscriptionRepository.save(subscription);
    }

    public void cancelForOrder(OrderId orderId) {
        Objects.requireNonNull(orderId, "orderId must not be null");

        final Subscription subscription = subscriptionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new SubscriptionNotFoundException(orderId));

        subscription.cancel();
        subscriptionRepository.save(subscription);
    }
}
