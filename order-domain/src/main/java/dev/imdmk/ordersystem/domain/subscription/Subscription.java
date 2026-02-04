package dev.imdmk.ordersystem.domain.subscription;

import dev.imdmk.ordersystem.domain.common.AggregateRoot;
import dev.imdmk.ordersystem.domain.order.OrderId;
import dev.imdmk.ordersystem.domain.subscription.event.SubscriptionCancelledEvent;
import dev.imdmk.ordersystem.domain.subscription.event.SubscriptionExpiredEvent;
import dev.imdmk.ordersystem.domain.subscription.event.SubscriptionStartedEvent;
import dev.imdmk.ordersystem.domain.subscription.exception.CannotExpirePermanentSubscriptionException;
import dev.imdmk.ordersystem.domain.subscription.exception.SubscriptionAlreadyCancelledException;
import dev.imdmk.ordersystem.domain.subscription.exception.SubscriptionAlreadyExpiredException;

import java.util.Objects;

import static dev.imdmk.ordersystem.domain.subscription.SubscriptionStatus.ACTIVE;
import static dev.imdmk.ordersystem.domain.subscription.SubscriptionStatus.CANCELLED;

public final class Subscription extends AggregateRoot {

    private final SubscriptionId id;
    private final OrderId orderId;
    private SubscriptionStatus status;
    private final Expiration expiration;

    private Subscription(
            SubscriptionId id,
            OrderId orderId,
            SubscriptionStatus status,
            Expiration expiration
    ) {
        this.id = Objects.requireNonNull(id, "id");
        this.orderId = Objects.requireNonNull(orderId, "orderId");
        this.status = Objects.requireNonNull(status, "status");
        this.expiration = Objects.requireNonNull(expiration, "expiration");
    }

    public static Subscription from(SubscriptionId id, OrderId orderId, SubscriptionStatus status, Expiration expiration) {
        return new Subscription(id, orderId, status, expiration);
    }

    public static Subscription start(
            OrderId orderId,
            Expiration expiration
    ) {
        final SubscriptionId subscriptionId = SubscriptionId.newId();
        final Subscription subscription = from(subscriptionId, orderId, ACTIVE, expiration);

        subscription.registerEvent(SubscriptionStartedEvent.now(subscription.id, subscription.orderId));
        return subscription;
    }

    public void cancel() {
        if (status == CANCELLED) {
            throw new SubscriptionAlreadyCancelledException(id);
        }

        this.status = CANCELLED;
        registerEvent(SubscriptionCancelledEvent.now(id, orderId));
    }

    public void expire() {
        if (status == SubscriptionStatus.EXPIRED) {
            throw new SubscriptionAlreadyExpiredException(id);
        }

        if (expiration.isPermanent()) {
            throw new CannotExpirePermanentSubscriptionException(id);
        }

        this.status = SubscriptionStatus.EXPIRED;
        registerEvent(SubscriptionExpiredEvent.now(id, orderId));
    }

    public boolean isActive() {
        return status == ACTIVE && !expiration.isExpired();
    }

    public boolean isCancelled() {
        return status == CANCELLED;
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
