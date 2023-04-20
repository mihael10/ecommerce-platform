package com.ecommerce.orderservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_items_id")
    private Long orderItemsId;

    @Column(name = "sku_code")
    private String skuCode;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Long quantity;
}
