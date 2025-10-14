package com.ecommerce.orderservice.entity;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class OrderDiffblueTest {
  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link Order#Order()}
   *   <li>{@link Order#setOrderId(Long)}
   *   <li>{@link Order#setOrderItemsList(List)}
   *   <li>{@link Order#setOrderNumber(String)}
   *   <li>{@link Order#getOrderId()}
   *   <li>{@link Order#getOrderItemsList()}
   *   <li>{@link Order#getOrderNumber()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    Order actualOrder = new Order();
    actualOrder.setOrderId(1L);
    ArrayList<OrderItems> orderItemsList = new ArrayList<>();
    actualOrder.setOrderItemsList(orderItemsList);
    actualOrder.setOrderNumber("42");
    Long actualOrderId = actualOrder.getOrderId();
    List<OrderItems> actualOrderItemsList = actualOrder.getOrderItemsList();

    // Assert that nothing has changed
    assertEquals("42", actualOrder.getOrderNumber());
    assertEquals(1L, actualOrderId.longValue());
    assertSame(orderItemsList, actualOrderItemsList);
  }
}
