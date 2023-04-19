package com.ecommerce.orderservice.service;

import com.ecommerce.orderservice.DTO.OrderRequestDTO;
import com.ecommerce.orderservice.DTO.OrderItemsDto;
import com.ecommerce.orderservice.entity.Order;
import com.ecommerce.orderservice.entity.OrderItems;
import com.ecommerce.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequestDTO orderRequestDTO) {

        Order order = new Order();

        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItems> orderItemsList = orderRequestDTO.getOrderItemsDto().stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderItemsList(orderItemsList);

        orderRepository.save(order);
    }

    private OrderItems mapToDto(OrderItemsDto orderItemsDto) {

        OrderItems orderItems = new OrderItems();
        orderItems.setOrderItemsId(orderItemsDto.getOrderItemsId());
        orderItems.setSkuCode(orderItemsDto.getSkuCode());
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setQuantity(orderItemsDto.getQuantity());

        return orderItems;
    }
}
