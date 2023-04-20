package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.DTO.InventoryResponseDTO;
import com.ecommerce.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDTO> isInStock(@RequestParam("skuCode") List<String> skuCode) {
       return inventoryService.isInStock(skuCode);
    }
}
