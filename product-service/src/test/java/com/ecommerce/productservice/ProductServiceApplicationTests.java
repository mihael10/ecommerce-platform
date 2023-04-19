package com.ecommerce.productservice;

import com.ecommerce.productservice.DTO.ProductRequestDTO;
import com.ecommerce.productservice.DTO.ProductResponseDTO;
import com.ecommerce.productservice.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.assertions.Assertions;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@DynamicPropertySource
	static void setProperties (DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {

		ProductRequestDTO productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString))
				.andExpect(status().isCreated());
		assertEquals(1,productRepository.findAll().size());
	}

	@Ignore
	void shouldGetTheProduct() throws Exception {

		ProductResponseDTO productRequest = getProductResponse();
		String productResponseString = objectMapper.writeValueAsString(productRequest);

		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productResponseString))
				.andExpect(status().isOk());
		assertEquals(1,productRepository.findAll().size());
	}

	private ProductResponseDTO getProductResponse() {
		return ProductResponseDTO.builder()
				.id(null)
				.name("Samsung s7")
				.description("mobile phone")
				.price(BigDecimal.valueOf(400))
				.build();
	}

	//id=643fcfb59e91280ed28155c0, name=Samsung s7, description=mobile phone, price=400

	private ProductRequestDTO getProductRequest() {
		return ProductRequestDTO.builder()
				.name("Samsung s7")
				.description("mobile phone")
				.price(BigDecimal.valueOf(400))
				.build();
	}

}
