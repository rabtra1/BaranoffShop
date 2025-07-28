package ru.rabtra.baranoffShop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@IdClass(OrderItem.class)
@Data
public class OrderItem {

    @Id
    @ManyToOne()
    @JoinColumn(name = "order_id")
    private Order order;

    @Id
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @Column
    private int quantity;

    @Column(name = "price_at_time", precision = 10, scale = 2)
    private BigDecimal priceAtTime;

}
