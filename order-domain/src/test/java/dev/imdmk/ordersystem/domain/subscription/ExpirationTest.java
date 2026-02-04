package dev.imdmk.ordersystem.domain.subscription;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ExpirationTest {

    @Test
    void permanentExpirationShouldBePermanent() {
        Expiration expiration = Expiration.never();

        assertThat(expiration.isPermanent()).isTrue();
        assertThat(expiration.isExpired()).isFalse();
    }

    @Test
    void timeLimitedExpirationShouldExpire() {
        Expiration expiration = Expiration.until(Instant.now().minusSeconds(5));

        assertThat(expiration.isPermanent()).isFalse();
        assertThat(expiration.isExpired()).isTrue();
    }
}

