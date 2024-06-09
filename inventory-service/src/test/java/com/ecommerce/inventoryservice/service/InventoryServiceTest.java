package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.DTO.InventoryResponseDTO;
import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

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
    void testIsInStock() {
        when(inventoryRepository.findBySkuCodeIn(any(List.class))).thenReturn(List.of(new Inventory(Long.valueOf(1), "100", Long.valueOf(1))));

        List<InventoryResponseDTO> result = inventoryService.isInStock(List.of("100"));
        Assertions.assertEquals(List.of(new InventoryResponseDTO("100", true)), result);
    }
}
