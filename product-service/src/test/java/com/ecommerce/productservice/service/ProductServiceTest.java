package com.ecommerce.productservice.service;

import com.ecommerce.productservice.DTO.ProductRequestDTO;
import com.ecommerce.productservice.DTO.ProductResponseDTO;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void testCreateProductWithValidInput() {
        ProductRequestDTO dto = new ProductRequestDTO("Test Product", "Description", new BigDecimal("19.99"));
        productService.createProduct(dto);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testGetAllProductsReturnsNonEmptyList() {
        when(productRepository.findAll()).thenReturn(List.of(new Product("1L", "Product 1", "Description 1", new BigDecimal("19.99"))));
        List<ProductResponseDTO> result = productService.getAllProducts();
        assertFalse(result.isEmpty(), "Result should not be empty");
        assertEquals(1, result.size(), "Result size should be 1");
    }

    @Test
    void testGetAllProductsReturnsEmptyList() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        List<ProductResponseDTO> result = productService.getAllProducts();
        assertTrue(result.isEmpty(), "Result should be empty");
    }

}
