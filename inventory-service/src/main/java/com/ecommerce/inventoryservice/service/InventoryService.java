package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.DTO.InventoryResponseDTO;
import com.ecommerce.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> isInStock(List<String> skuCode) {
        log.info("Waiting started ... ");
        // simulate slow behavior
        log.info("Waiting ended");
       return inventoryRepository.findBySkuCodeIn(skuCode).stream()
               .map(inventory ->
                   InventoryResponseDTO.builder()
                           .skuCode(inventory.getSkuCode())
                           .isInStock(inventory.getQuantity() > 0)
                           .build()
               )
               .toList();
    }
}
