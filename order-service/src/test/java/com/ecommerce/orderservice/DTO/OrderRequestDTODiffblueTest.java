package com.ecommerce.orderservice.DTO;

import static org.junit.jupiter.api.Assertions.assertSame;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class OrderRequestDTODiffblueTest {
  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link OrderRequestDTO#OrderRequestDTO()}
   *   <li>{@link OrderRequestDTO#setOrderItemsDto(List)}
   *   <li>{@link OrderRequestDTO#getOrderItemsDto()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    OrderRequestDTO actualOrderRequestDTO = new OrderRequestDTO();
    ArrayList<OrderItemsDto> orderItemsDto = new ArrayList<>();
    actualOrderRequestDTO.setOrderItemsDto(orderItemsDto);

    // Assert that nothing has changed
    assertSame(orderItemsDto, actualOrderRequestDTO.getOrderItemsDto());
  }
}
