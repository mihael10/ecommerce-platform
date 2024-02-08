package com.ecommerce.orderservice.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OrderItemsDiffblueTest {
  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link OrderItems#OrderItems()}
   *   <li>{@link OrderItems#setOrderItemsId(Long)}
   *   <li>{@link OrderItems#setPrice(BigDecimal)}
   *   <li>{@link OrderItems#setQuantity(Long)}
   *   <li>{@link OrderItems#setSkuCode(String)}
   *   <li>{@link OrderItems#getOrderItemsId()}
   *   <li>{@link OrderItems#getPrice()}
   *   <li>{@link OrderItems#getQuantity()}
   *   <li>{@link OrderItems#getSkuCode()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    OrderItems actualOrderItems = new OrderItems();
    actualOrderItems.setOrderItemsId(1L);
    BigDecimal price = new BigDecimal("2.3");
    actualOrderItems.setPrice(price);
    actualOrderItems.setQuantity(1L);
    actualOrderItems.setSkuCode("Sku Code");
    Long actualOrderItemsId = actualOrderItems.getOrderItemsId();
    BigDecimal actualPrice = actualOrderItems.getPrice();
    Long actualQuantity = actualOrderItems.getQuantity();

    // Assert that nothing has changed
    assertEquals("Sku Code", actualOrderItems.getSkuCode());
    assertEquals(1L, actualOrderItemsId.longValue());
    assertEquals(1L, actualQuantity.longValue());
    assertEquals(new BigDecimal("2.3"), actualPrice);
    assertSame(price, actualPrice);
  }
}
