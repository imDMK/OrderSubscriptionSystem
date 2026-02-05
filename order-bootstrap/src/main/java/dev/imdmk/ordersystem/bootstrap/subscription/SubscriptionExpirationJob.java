package dev.imdmk.ordersystem.bootstrap.subscription;

import dev.imdmk.ordersystem.application.subscription.service.SubscriptionExpirationService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Objects;

@Component
public final class SubscriptionExpirationJob {

    private final SubscriptionExpirationService expirationService;

    public SubscriptionExpirationJob(
            SubscriptionExpirationService expirationService
    ) {
        this.expirationService = Objects.requireNonNull(expirationService, "expirationService must not be null");
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void run() {
        expirationService.expireDueSubscriptions(Instant.now());
    }
}
