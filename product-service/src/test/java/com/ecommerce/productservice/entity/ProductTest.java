package com.ecommerce.productservice.entity;

import com.ecommerce.productservice.repository.ProductRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class ProductTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    // If using an actual MongoDB instance or a container, configure the connection details
    @DynamicPropertySource
    static void mongoDbProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () -> "mongodb://localhost:27017/testdb");
    }


  @Ignore
    void testProductPersistence() {
        Product product = Product.builder()
                .name("Test Product")
                .description("Test Description")
                .price(new BigDecimal("19.99"))
                .build();

        // Save the product
        Product savedProduct = productRepository.save(product);
        assertNotNull(savedProduct.getId(), "Product ID should be generated");

        // Fetch the product
        Optional<Product> fetchedProduct = productRepository.findById(savedProduct.getId());
        assertTrue(fetchedProduct.isPresent(), "Product should be found");
        fetchedProduct.ifPresent(p -> {
            assertEquals("Test Product", p.getName());
            assertEquals("Test Description", p.getDescription());
            assertEquals(new BigDecimal("19.99"), p.getPrice());
        });

        // Clean up
        productRepository.delete(savedProduct);
        assertFalse(productRepository.existsById(savedProduct.getId()), "Product should be deleted");
    }
}
