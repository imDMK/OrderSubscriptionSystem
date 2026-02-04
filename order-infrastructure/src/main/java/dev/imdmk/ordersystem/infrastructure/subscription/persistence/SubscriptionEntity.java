package dev.imdmk.ordersystem.infrastructure.subscription.persistence;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Table(name = "subscriptions")
@Access(AccessType.FIELD)
public class SubscriptionEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionStatusEntity status;

    @Column(name = "expires_at")
    private Instant expiresAt;

    protected SubscriptionEntity() {}

    public SubscriptionEntity(
            UUID id,
            UUID orderId,
            SubscriptionStatusEntity status,
            Instant expiresAt
    ) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.orderId = Objects.requireNonNull(orderId, "orderId must not be null");
        this.status = Objects.requireNonNull(status, "status must not be null");
        this.expiresAt = expiresAt;
    }
}
