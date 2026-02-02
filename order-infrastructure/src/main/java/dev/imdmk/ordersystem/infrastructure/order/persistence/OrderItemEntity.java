package dev.imdmk.ordersystem.infrastructure.order.persistence;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Entity
@Table(name = "order_items")
@Access(AccessType.FIELD)
public final class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String productId;

    @Column(nullable = false)
    private int quantity;

    @Column(
            name = "unit_price",
            nullable = false,
            precision = 19,
            scale = 2
    )
    private BigDecimal unitPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    protected OrderItemEntity() {}

    public OrderItemEntity(
            String productId,
            int quantity,
            BigDecimal unitPrice,
            OrderEntity order
    ) {
        this.productId = Objects.requireNonNull(productId, "productId");
        this.unitPrice = Objects.requireNonNull(unitPrice, "unitPrice");
        this.order = Objects.requireNonNull(order, "order");

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = quantity;
    }
}

