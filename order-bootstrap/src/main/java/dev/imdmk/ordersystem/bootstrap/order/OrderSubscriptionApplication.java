package dev.imdmk.ordersystem.bootstrap.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
        scanBasePackages = "dev.imdmk.ordersystem"
)
@EnableJpaRepositories(
        basePackages = "dev.imdmk.ordersystem.infrastructure.order.persistence"
)
@EntityScan(
        basePackages = "dev.imdmk.ordersystem.infrastructure"
)
public class OrderSubscriptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderSubscriptionApplication.class, args);
    }
}
