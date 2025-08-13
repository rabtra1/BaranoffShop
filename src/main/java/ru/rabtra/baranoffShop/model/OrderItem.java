package ru.rabtra.baranoffShop.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {

    @EmbeddedId
    private OrderItemKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;


    @Column
    private int quantity;

    @Column(name = "price_at_time", precision = 10, scale = 2)
    private BigDecimal priceAtTime;

}
