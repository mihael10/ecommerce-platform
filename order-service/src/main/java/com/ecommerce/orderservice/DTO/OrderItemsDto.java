package com.ecommerce.orderservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto {

    private Long orderItemsId;
    private String skuCode;
    private BigDecimal price;
    private Long quantity;
}
