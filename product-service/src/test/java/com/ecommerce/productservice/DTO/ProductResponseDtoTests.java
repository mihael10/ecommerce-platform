package com.ecommerce.productservice.DTO;

import com.ecommerce.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
class ProductResponseDtoTests {

    @Test
    void shouldMapProductToProductResponseDTO() {
        // Create a Product object
        Product product = Product.builder()
                .id("1")
                .name("Samsung s7")
                .description("mobile phone")
                .price(BigDecimal.valueOf(400))
                .build();

        // Map the Product object to ProductResponseDTO
        ProductResponseDTO productResponseDTO = mapToProductResponseDTO(product);

        // Test the mapping
        assertEquals(product.getId(), productResponseDTO.getId());
        assertEquals(product.getName(), productResponseDTO.getName());
        assertEquals(product.getDescription(), productResponseDTO.getDescription());
        assertEquals(product.getPrice(), productResponseDTO.getPrice());
    }

    private ProductResponseDTO mapToProductResponseDTO(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
