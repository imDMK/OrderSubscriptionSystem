package dev.imdmk.ordersystem.domain.subscription;

import java.util.Objects;
import java.util.UUID;

public record SubscriptionId(UUID value) {

    public SubscriptionId {
        Objects.requireNonNull(value, "subscriptionId value must not be null");
    }

    public static SubscriptionId newId() {
        return new SubscriptionId(UUID.randomUUID());
    }
}
