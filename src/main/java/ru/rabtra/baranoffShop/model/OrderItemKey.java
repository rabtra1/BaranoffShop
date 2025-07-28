package ru.rabtra.baranoffShop.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class OrderItemKey implements Serializable {
    private Long orderId;
    private Long productId;
}
