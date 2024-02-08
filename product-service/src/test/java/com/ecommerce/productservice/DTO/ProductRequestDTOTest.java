package com.ecommerce.productservice.DTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

class ProductRequestDTOTest {
    @Test
    void testObjectInstantiationWithBuilder() {
        ProductRequestDTO product = ProductRequestDTO.builder()
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("19.99"))
                .build();

        assertNotNull(product, "Product should not be null");
        assertEquals("Test Product", product.getName(), "Name should match");
        assertEquals("Test Description", product.getDescription(), "Description should match");
        assertEquals(new BigDecimal("19.99"), product.getPrice(), "Price should match");
    }

    @Test
    void testObjectInstantiationWithAllArgsConstructor() {
        ProductRequestDTO product = new ProductRequestDTO("Test Product", "Test Description", new BigDecimal("29.99"));

        assertNotNull(product, "Product should not be null");
        assertEquals("Test Product", product.getName(), "Name should match");
        assertEquals("Test Description", product.getDescription(), "Description should match");
        assertEquals(new BigDecimal("29.99"), product.getPrice(), "Price should match");
    }

    @Test
    void testNullSafety() {
        ProductRequestDTO product = ProductRequestDTO.builder().build();

        assertNull(product.getName(), "Name should be null");
        assertNull(product.getDescription(), "Description should be null");
        assertNull(product.getPrice(), "Price should be null");
    }

    @Test
    void testEqualityAndHashCode() {
        ProductRequestDTO product1 = ProductRequestDTO.builder()
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("19.99"))
                .build();

        ProductRequestDTO product2 = ProductRequestDTO.builder()
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("19.99"))
                .build();

        assertEquals(product1, product2, "Two products with the same values should be equal");
        assertEquals(product1.hashCode(), product2.hashCode(), "Hash codes should match for equal objects");
    }

    @Test
    void testFieldModification() {
        ProductRequestDTO product = ProductRequestDTO.builder()
                .name("Initial Name")
                .build();

        product.setName("Modified Name");
        assertEquals("Modified Name", product.getName(), "Name should be updated");
    }
}