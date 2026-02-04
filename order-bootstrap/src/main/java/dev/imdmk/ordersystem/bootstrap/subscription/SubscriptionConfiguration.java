package dev.imdmk.ordersystem.bootstrap.subscription;

import dev.imdmk.ordersystem.application.subscription.policy.DefaultSubscriptionPolicy;
import dev.imdmk.ordersystem.application.subscription.policy.SubscriptionPolicy;
import dev.imdmk.ordersystem.application.subscription.repository.SubscriptionRepository;
import dev.imdmk.ordersystem.application.subscription.service.SubscriptionExpirationService;
import dev.imdmk.ordersystem.application.subscription.service.SubscriptionService;
import dev.imdmk.ordersystem.infrastructure.subscription.adapter.SubscriptionRepositoryAdapter;
import dev.imdmk.ordersystem.infrastructure.subscription.persistence.JpaSubscriptionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SubscriptionConfiguration {

    @Bean
    public SubscriptionRepository subscriptionRepository(
            JpaSubscriptionRepository jpaSubscriptionRepository
    ) {
        return new SubscriptionRepositoryAdapter(jpaSubscriptionRepository);
    }

    @Bean
    public SubscriptionPolicy subscriptionPolicy() {
        return new DefaultSubscriptionPolicy();
    }

    @Bean
    public SubscriptionService subscriptionService(
            SubscriptionRepository subscriptionRepository,
            SubscriptionPolicy subscriptionPolicy
    ) {
        return new SubscriptionService(subscriptionRepository, subscriptionPolicy);
    }

    @Bean
    public SubscriptionExpirationService subscriptionExpirationService(
            SubscriptionRepository subscriptionRepository
    ) {
        return new SubscriptionExpirationService(subscriptionRepository);
    }
}
