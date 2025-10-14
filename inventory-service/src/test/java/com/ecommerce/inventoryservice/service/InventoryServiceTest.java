package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.DTO.InventoryResponseDTO;
import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class InventoryServiceTest {
    @Mock
    InventoryRepository inventoryRepository;
    @InjectMocks
    InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIsInStock_WithValidData_ReturnsCorrectResponse() {
        // Arrange
        List<String> skuCodes = List.of("SKU123", "SKU456");
        List<Inventory> inventoryList = List.of(
                new Inventory(1L, "SKU123", 10L),
                new Inventory(2L, "SKU456", 0L)
        );

        when(inventoryRepository.findBySkuCodeIn(skuCodes)).thenReturn(inventoryList);

        // Act
        List<InventoryResponseDTO> result = inventoryService.isInStock(skuCodes);

        // Assert
        assertEquals(2, result.size());
        assertEquals(new InventoryResponseDTO("SKU123", true), result.get(0));
        assertEquals(new InventoryResponseDTO("SKU456", false), result.get(1));
        verify(inventoryRepository, times(1)).findBySkuCodeIn(skuCodes);
    }

    @Test
    void testIsInStock_WithEmptyList_ReturnsEmptyResponse() {
        // Arrange
        List<String> skuCodes = Collections.emptyList();
        when(inventoryRepository.findBySkuCodeIn(skuCodes)).thenReturn(Collections.emptyList());

        // Act
        List<InventoryResponseDTO> result = inventoryService.isInStock(skuCodes);

        // Assert
        assertEquals(0, result.size());
        verify(inventoryRepository, times(1)).findBySkuCodeIn(skuCodes);
    }

    @Test
    void testIsInStock_WithNonExistingSkuCode_ReturnsEmptyResponse() {
        // Arrange
        List<String> skuCodes = List.of("NON_EXISTING_SKU");
        when(inventoryRepository.findBySkuCodeIn(skuCodes)).thenReturn(Collections.emptyList());

        // Act
        List<InventoryResponseDTO> result = inventoryService.isInStock(skuCodes);

        // Assert
        assertEquals(0, result.size());
        verify(inventoryRepository, times(1)).findBySkuCodeIn(skuCodes);
    }

    @Test
    void testIsInStock_WithRepositoryException_ThrowsException() {
        // Arrange
        List<String> skuCodes = List.of("SKU123", "SKU456");
        when(inventoryRepository.findBySkuCodeIn(any(List.class))).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> inventoryService.isInStock(skuCodes)
        );

        assertEquals("Database error", exception.getMessage());
        verify(inventoryRepository, times(1)).findBySkuCodeIn(skuCodes);
    }
}
