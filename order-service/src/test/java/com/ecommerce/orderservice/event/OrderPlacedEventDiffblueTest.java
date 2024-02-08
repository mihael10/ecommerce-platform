package com.ecommerce.orderservice.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class OrderPlacedEventDiffblueTest {
  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link OrderPlacedEvent#OrderPlacedEvent()}
   *   <li>{@link OrderPlacedEvent#setOrderNumber(String)}
   *   <li>{@link OrderPlacedEvent#toString()}
   *   <li>{@link OrderPlacedEvent#getOrderNumber()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    OrderPlacedEvent actualOrderPlacedEvent = new OrderPlacedEvent();
    actualOrderPlacedEvent.setOrderNumber("42");
    String actualToStringResult = actualOrderPlacedEvent.toString();

    // Assert that nothing has changed
    assertEquals("42", actualOrderPlacedEvent.getOrderNumber());
    assertEquals("OrderPlacedEvent(orderNumber=42)", actualToStringResult);
  }
}
