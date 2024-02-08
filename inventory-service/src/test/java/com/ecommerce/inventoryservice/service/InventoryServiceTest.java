package com.ecommerce.inventoryservice.service;


import com.ecommerce.inventoryservice.DTO.InventoryResponseDTO;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import com.ecommerce.inventoryservice.entity.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class InventoryServiceTest {

    @MockBean
    private InventoryRepository inventoryRepository;

    private InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        inventoryService = new InventoryService(inventoryRepository);
    }

    @Test
    public void testIsInStock_withAllItemsInStock() {
        // Arrange
        List<String> skuCodes = Arrays.asList("SKU1", "SKU2");
        List<Inventory> inventories = Arrays.asList(
                new Inventory(1L, "SKU1", 10L),
                new Inventory(2L, "SKU2", 5L)
        );
        when(inventoryRepository.findBySkuCodeIn(skuCodes)).thenReturn(inventories);

        // Act
        List<InventoryResponseDTO> result = inventoryService.isInStock(skuCodes);

        // Assert
        assertEquals(2, result.size());
        assertEquals(true, result.get(0).isInStock());
        assertEquals(true, result.get(1).isInStock());
    }

    @Test
    public void testIsInStock_withSomeItemsOutOfStock() {
        // Arrange
        List<String> skuCodes = Arrays.asList("SKU1", "SKU2", "SKU3");
        List<Inventory> inventories = Arrays.asList(
                new Inventory(1L ,"SKU1", 0L),
                new Inventory(2L, "SKU2", 5L),
                new Inventory(3L,"SKU3", 0L)
        );
        when(inventoryRepository.findBySkuCodeIn(skuCodes)).thenReturn(inventories);

        // Act
        List<InventoryResponseDTO> result = inventoryService.isInStock(skuCodes);

        // Assert
        assertEquals(3, result.size());
        assertEquals(false, result.get(0).isInStock());
        assertEquals(true, result.get(1).isInStock());
        assertEquals(false, result.get(2).isInStock());
    }

    @Test
    public void testIsInStock_withNoItemsFound() {
        // Arrange
        List<String> skuCodes = Arrays.asList("SKU1", "SKU2");
        when(inventoryRepository.findBySkuCodeIn(skuCodes)).thenReturn(Arrays.asList());

        // Act
        List<InventoryResponseDTO> result = inventoryService.isInStock(skuCodes);

        // Assert
        assertEquals(0, result.size());
    }
}
