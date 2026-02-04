package dev.imdmk.ordersystem.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(
        scanBasePackages = "dev.imdmk.ordersystem"
)
@EnableJpaRepositories(
        basePackages = {
                "dev.imdmk.ordersystem.infrastructure.order.persistence",
                "dev.imdmk.ordersystem.infrastructure.subscription.persistence"
        }
)
@EntityScan(
        basePackages = "dev.imdmk.ordersystem.infrastructure"
)
@EnableScheduling
public class OrderSubscriptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderSubscriptionApplication.class, args);
    }
}
