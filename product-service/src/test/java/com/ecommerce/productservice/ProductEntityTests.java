package com.ecommerce.productservice;


import com.ecommerce.productservice.DTO.ProductResponseDTO;
import com.ecommerce.productservice.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Testcontainers
public class ProductEntityTests {


    @Test
    public void testCreationOfProductEntity() {
        // Create a Product object
        Product product = new Product();
        product.setId("1");
        product.setName("Samsung s7");
        product.setDescription("mobile phone");
        product.setPrice(BigDecimal.valueOf(400));

        // Test the getters
        assertEquals("1", product.getId());
        assertEquals("Samsung s7", product.getName());
        assertEquals("mobile phone", product.getDescription());
        assertEquals(BigDecimal.valueOf(400), product.getPrice());

        // Test the builder pattern
        Product productWithBuilder = Product.builder()
                .id("2")
                .name("Samsung s8")
                .description("mobile phone")
                .price(BigDecimal.valueOf(500))
                .build();

        assertEquals("2", productWithBuilder.getId());
        assertEquals("Samsung s8", productWithBuilder.getName());
        assertEquals("mobile phone", productWithBuilder.getDescription());
        assertEquals(BigDecimal.valueOf(500), productWithBuilder.getPrice());
    }
}
