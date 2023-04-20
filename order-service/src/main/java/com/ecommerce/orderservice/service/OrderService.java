package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.DTO.InventoryResponseDTO;
import com.ecommerce.orderservice.DTO.OrderRequestDTO;
import com.ecommerce.orderservice.DTO.OrderItemsDto;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.OrderItems;
import com.ecommerce.orderservice.event.OrderPlacedEvent;
import com.ecommerce.orderservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public String placeOrder(OrderRequestDTO orderRequestDTO) {

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderItems> orderLineItems = orderRequestDTO.getOrderItemsDto()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderItemsList().stream()
                .map(OrderItems::getSkuCode)
                .toList();

            InventoryResponseDTO[] inventoryResponseArray = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponseDTO[].class)
                    .block();

            boolean allProductsInStock = Arrays.stream(inventoryResponseArray)
                    .allMatch(InventoryResponseDTO::isInStock);

            if (allProductsInStock) {
                orderRepository.save(order);
                kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
                return "Order placed successfully";
            } else {
                throw new IllegalArgumentException("Product is out of stuck");
            }
        }

    private OrderItems mapToDto(OrderItemsDto orderItemsDto) {

        OrderItems orderItems = new OrderItems();
        orderItems.setOrderItemsId(orderItemsDto.getOrderItemsId());
        orderItems.setSkuCode(orderItemsDto.getSkuCode());
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setQuantity(orderItemsDto.getQuantity());
        log.info("mapToDTO method: {} ", orderItems);
        return orderItems;
    }
}
