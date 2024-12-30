package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.DTO.InventoryResponseDTO;
import com.ecommerce.orderservice.DTO.OrderItemsDto;
import com.ecommerce.orderservice.DTO.OrderRequestDTO;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.OrderItems;
import com.ecommerce.orderservice.event.OrderPlacedEvent;
import com.ecommerce.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @InjectMocks
    private OrderService orderService;

    private WebClient webClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        webClient = mock(WebClient.class, RETURNS_DEEP_STUBS);
        when(webClientBuilder.build()).thenReturn(webClient);
    }

    void testPlaceOrder_AllProductsInStock() {
        // Arrange
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(
                List.of(new OrderItemsDto(1L, "SKU123", BigDecimal.valueOf(100), 1L))
        );
        InventoryResponseDTO[] inventoryResponse = {
                new InventoryResponseDTO("SKU123", true)
        };

        when(webClient.get()
                .uri(anyString(), Optional.ofNullable(any()))
                .retrieve()
                .bodyToMono(InventoryResponseDTO[].class)
                .block()).thenReturn(inventoryResponse);

        // Act
        String result = orderService.placeOrder(orderRequestDTO);

        // Assert
        assertEquals("Order placed successfully", result);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(kafkaTemplate, times(1)).send(eq("notificationTopic"), any(OrderPlacedEvent.class));
    }


    void testPlaceOrder_ProductOutOfStock() {
        // Arrange
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(
                List.of(new OrderItemsDto(1L, "SKU123", BigDecimal.valueOf(100), 1L))
        );
        InventoryResponseDTO[] inventoryResponse = {
                new InventoryResponseDTO("SKU123", false)
        };

        when(webClient.get()
                .uri(anyString(), Optional.ofNullable(any()))
                .retrieve()
                .bodyToMono(InventoryResponseDTO[].class)
                .block()).thenReturn(inventoryResponse);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                orderService.placeOrder(orderRequestDTO));
        assertEquals("Product is out of stuck", exception.getMessage());
        verify(orderRepository, never()).save(any(Order.class));
        verify(kafkaTemplate, never()).send(anyString(), any(OrderPlacedEvent.class));
    }

    void testPlaceOrder_InventoryServiceFailure() {
        // Arrange
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO(
                List.of(new OrderItemsDto(1L, "SKU123", BigDecimal.valueOf(100), 1L))
        );

        when(webClient.get()
                .uri(anyString(), Optional.ofNullable(any()))
                .retrieve()
                .bodyToMono(InventoryResponseDTO[].class)
                .block()).thenThrow(WebClientResponseException.class);

        // Act & Assert
        WebClientResponseException exception = assertThrows(WebClientResponseException.class, () ->
                orderService.placeOrder(orderRequestDTO));
        verify(orderRepository, never()).save(any(Order.class));
        verify(kafkaTemplate, never()).send(anyString(), any(OrderPlacedEvent.class));
    }

    @Test
    void testMapToDto() {
        // Arrange
        OrderItemsDto orderItemsDto = new OrderItemsDto(1L, "SKU123", BigDecimal.valueOf(100), 2L);

        // Act
        OrderItems orderItems = orderService.mapToDto(orderItemsDto);

        // Assert
        assertNotNull(orderItems);
        assertEquals(orderItemsDto.getOrderItemsId(), orderItems.getOrderItemsId());
        assertEquals(orderItemsDto.getSkuCode(), orderItems.getSkuCode());
        assertEquals(orderItemsDto.getPrice(), orderItems.getPrice());
        assertEquals(orderItemsDto.getQuantity(), orderItems.getQuantity());
    }
}
