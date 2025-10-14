package com.ecommerce.orderservice.DTO;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryResponseDTODiffblueTest {
  /**
   * Methods under test:
   *
   * <ul>
   *   <li>{@link InventoryResponseDTO#InventoryResponseDTO()}
   *   <li>{@link InventoryResponseDTO#setInStock(boolean)}
   *   <li>{@link InventoryResponseDTO#setSkuCode(String)}
   *   <li>{@link InventoryResponseDTO#toString()}
   *   <li>{@link InventoryResponseDTO#getSkuCode()}
   *   <li>{@link InventoryResponseDTO#isInStock()}
   * </ul>
   */
  @Test
  void testGettersAndSetters() {
    // Arrange and Act
    InventoryResponseDTO actualInventoryResponseDTO = new InventoryResponseDTO();
    actualInventoryResponseDTO.setInStock(true);
    actualInventoryResponseDTO.setSkuCode("Sku Code");
    String actualToStringResult = actualInventoryResponseDTO.toString();
    String actualSkuCode = actualInventoryResponseDTO.getSkuCode();

    // Assert that nothing has changed
    assertEquals("InventoryResponseDTO(skuCode=Sku Code, isInStock=true)", actualToStringResult);
    assertEquals("Sku Code", actualSkuCode);
    assertTrue(actualInventoryResponseDTO.isInStock());
  }
}
