package dev.imdmk.ordersystem.infrastructure.order.persistence;

import dev.imdmk.ordersystem.domain.order.*;

public enum OrderStatusEntity {
    NEW,
    PAID,
    CANCELLED;

    static OrderStatusEntity from(Order order) {
        if (order.isPaid()) {
            return PAID;
        }
        if (order.isCancelled()) {
            return CANCELLED;
        }
        return NEW;
    }
}
