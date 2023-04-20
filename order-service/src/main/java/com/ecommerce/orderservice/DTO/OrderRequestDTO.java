package com.ecommerce.orderservice.DTO;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private List<OrderItemsDto> orderItemsDto = new ArrayList<>();
}
