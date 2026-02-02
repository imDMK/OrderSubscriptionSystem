package dev.imdmk.ordersystem.bootstrap.order.listener;

import dev.imdmk.ordersystem.domain.order.event.OrderCancelEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public final class OrderCancelEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderCancelEventListener.class);

    @EventListener
    public void handle(OrderCancelEvent event) {
        LOGGER.info("Order {} cancel at {}", event.orderId(), event.occurredAt());
    }
}
