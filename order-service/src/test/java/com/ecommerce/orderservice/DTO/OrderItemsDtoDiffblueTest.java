package com.ecommerce.orderservice.DTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OrderItemsDtoDiffblueTest {
  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link OrderItemsDto#OrderItemsDto()}
   *   <li>{@link OrderItemsDto#setOrderItemsId(Long)}
   *   <li>{@link OrderItemsDto#setPrice(BigDecimal)}
   *   <li>{@link OrderItemsDto#setQuantity(Long)}
   *   <li>{@link OrderItemsDto#setSkuCode(String)}
   *   <li>{@link OrderItemsDto#getOrderItemsId()}
   *   <li>{@link OrderItemsDto#getPrice()}
   *   <li>{@link OrderItemsDto#getQuantity()}
   *   <li>{@link OrderItemsDto#getSkuCode()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    OrderItemsDto actualOrderItemsDto = new OrderItemsDto();
    actualOrderItemsDto.setOrderItemsId(1L);
    BigDecimal price = new BigDecimal("2.3");
    actualOrderItemsDto.setPrice(price);
    actualOrderItemsDto.setQuantity(1L);
    actualOrderItemsDto.setSkuCode("Sku Code");
    Long actualOrderItemsId = actualOrderItemsDto.getOrderItemsId();
    BigDecimal actualPrice = actualOrderItemsDto.getPrice();
    Long actualQuantity = actualOrderItemsDto.getQuantity();

    // Assert that nothing has changed
    assertEquals("Sku Code", actualOrderItemsDto.getSkuCode());
    assertEquals(1L, actualOrderItemsId.longValue());
    assertEquals(1L, actualQuantity.longValue());
    assertEquals(new BigDecimal("2.3"), actualPrice);
    assertSame(price, actualPrice);
  }
}
