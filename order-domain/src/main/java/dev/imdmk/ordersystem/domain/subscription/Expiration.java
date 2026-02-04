package dev.imdmk.ordersystem.domain.subscription;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public final class Expiration {

    private final Instant expiresAt;

    private Expiration(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public static Expiration never() {
        return new Expiration(null);
    }

    public static Expiration until(Instant expiresAt) {
        Objects.requireNonNull(expiresAt, "expiresAt must not be null");
        return new Expiration(expiresAt);
    }

    public boolean isPermanent() {
        return expiresAt == null;
    }

    public boolean isExpired() {
        return expiresAt != null && expiresAt.isBefore(Instant.now());
    }

    public Optional<Instant> getExpiresAt() {
        return Optional.ofNullable(expiresAt);
    }
}
