package com.ecommerce.orderservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemsDto {

    private Long orderItemsId;
    private String skuCode;
    private BigDecimal price;
    private Long quantity;
}
