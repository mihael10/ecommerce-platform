package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.DTO.InventoryResponseDTO;
import com.ecommerce.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Test
    void isInStock_ValidRequest_ReturnsOkWithListOfInventory() throws Exception {
        // Arrange
        List<String> skuCodes = List.of("SKU123", "SKU456");
        List<InventoryResponseDTO> expectedResponse = List.of(new InventoryResponseDTO("SKU123", true), new InventoryResponseDTO("SKU456", false));
        given(inventoryService.isInStock(skuCodes)).willReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(get("/api/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("skuCode", "SKU123", "SKU456"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].skuCode").value("SKU123"))
                .andExpect(jsonPath("$[0].inStock").value(true))
                .andExpect(jsonPath("$[1].skuCode").value("SKU456"))
                .andExpect(jsonPath("$[1].inStock").value(false));
    }

}
