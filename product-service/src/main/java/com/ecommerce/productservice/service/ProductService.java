package com.ecommerce.productservice.service;

import com.ecommerce.productservice.DTO.ProductRequestDTO;
import com.ecommerce.productservice.DTO.ProductResponseDTO;
import com.ecommerce.productservice.entity.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequestDTO productRequestDTO) {
        Product product = Product.builder()
                .name(productRequestDTO.getName())
                .description(productRequestDTO.getDescription())
                .price(productRequestDTO.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product is saved {} ", product);
    }

    public List<ProductResponseDTO> getAllProducts() {
       List<Product> products = productRepository.findAll();

      return products.stream().map(this::mapToGetProducts).toList();

    }

    private ProductResponseDTO mapToGetProducts (Product product) {
            return ProductResponseDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .build();
    }
}
