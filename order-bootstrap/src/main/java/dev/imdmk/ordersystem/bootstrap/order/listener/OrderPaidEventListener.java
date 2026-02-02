package dev.imdmk.ordersystem.bootstrap.order.listener;

import dev.imdmk.ordersystem.domain.order.event.OrderPaidEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class OrderPaidEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderPaidEventListener.class);

    @EventListener
    public void handle(OrderPaidEvent event) {
        LOGGER.info("Order {} paid at {}", event.orderId(), event.occurredAt());
    }
}

