package dev.imdmk.ordersystem.domain.subscription;

import dev.imdmk.ordersystem.domain.model.AggregateRoot;
import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.event.SubscriptionCancelledEvent;
import dev.imdmk.ordersystem.domain.subscription.event.SubscriptionExpiredEvent;
import dev.imdmk.ordersystem.domain.subscription.event.SubscriptionStartedEvent;
import dev.imdmk.ordersystem.domain.subscription.exception.CannotCancelExpiredSubscriptionException;
import dev.imdmk.ordersystem.domain.subscription.exception.CannotExpireCancelledSubscriptionException;
import dev.imdmk.ordersystem.domain.subscription.exception.CannotExpirePermanentSubscriptionException;
import dev.imdmk.ordersystem.domain.subscription.exception.SubscriptionAlreadyCancelledException;
import dev.imdmk.ordersystem.domain.subscription.exception.SubscriptionAlreadyExpiredException;
import dev.imdmk.ordersystem.domain.subscription.exception.SubscriptionNotYetExpiredException;

import java.util.Objects;

public final class Subscription extends AggregateRoot {

    private final SubscriptionId id;
    private final OrderId orderId;
    private final Expiration expiration;
    private SubscriptionStatus status;

    private Subscription(
            SubscriptionId id,
            OrderId orderId,
            Expiration expiration,
            SubscriptionStatus status
    ) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.orderId = Objects.requireNonNull(orderId, "orderId must not be null");
        this.expiration = Objects.requireNonNull(expiration, "expiration must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
    }

    public static Subscription from(
            SubscriptionId id,
            OrderId orderId,
            Expiration expiration,
            SubscriptionStatus status
    ) {
        return new Subscription(id, orderId, expiration, status);
    }

    public static Subscription start(
            OrderId orderId,
            Expiration expiration
    ) {
        final SubscriptionId subscriptionId = SubscriptionId.newId();
        final Subscription subscription = from(subscriptionId, orderId, expiration, SubscriptionStatus.ACTIVE);

        subscription.registerEvent(
                SubscriptionStartedEvent.now(subscription.id, subscription.orderId)
        );

        return subscription;
    }

    public void cancel() {
        if (status == SubscriptionStatus.CANCELLED) {
            throw new SubscriptionAlreadyCancelledException(id);
        }

        if (status == SubscriptionStatus.EXPIRED) {
            throw new CannotCancelExpiredSubscriptionException(id);
        }

        this.status = SubscriptionStatus.CANCELLED;
        registerEvent(SubscriptionCancelledEvent.now(id, orderId));
    }

    public void expire() {
        if (status == SubscriptionStatus.EXPIRED) {
            throw new SubscriptionAlreadyExpiredException(id);
        }

        if (status == SubscriptionStatus.CANCELLED) {
            throw new CannotExpireCancelledSubscriptionException(id);
        }

        if (expiration.isPermanent()) {
            throw new CannotExpirePermanentSubscriptionException(id);
        }

        if (!expiration.isExpired()) {
            throw new SubscriptionNotYetExpiredException(id);
        }

        this.status = SubscriptionStatus.EXPIRED;
        registerEvent(SubscriptionExpiredEvent.now(id, orderId));
    }

    public boolean isActive() {
        if (status != SubscriptionStatus.ACTIVE) {
            return false;
        }

        if (expiration.isPermanent()) {
            return true;
        }

        return !expiration.isExpired();
    }

    public boolean isCancelled() {
        return status == SubscriptionStatus.CANCELLED;
    }

    public boolean isExpired() {
        return status == SubscriptionStatus.EXPIRED;
    }

    public SubscriptionId getId() {
        return id;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public Expiration getExpiration() {
        return expiration;
    }
}
