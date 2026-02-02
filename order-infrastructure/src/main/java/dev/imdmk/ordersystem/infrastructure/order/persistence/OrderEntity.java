package dev.imdmk.ordersystem.infrastructure.order.persistence;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "orders")
@Access(AccessType.FIELD)
public class OrderEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatusEntity status;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal total;

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItemEntity> items = new ArrayList<>();

    protected OrderEntity() {}

    public OrderEntity(UUID id, OrderStatusEntity status, BigDecimal total) {
        this.id = id;
        this.status = status;
        this.total = total;
    }

    public void addItem(OrderItemEntity item) {
        items.add(item);
    }
}
