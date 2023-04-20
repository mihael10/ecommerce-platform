package com.ecommerce.orderservice.controller;

import com.ecommerce.orderservice.DTO.OrderRequestDTO;
import com.ecommerce.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallBack")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(OrderRequestDTO orderRequestDTO) {
        return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequestDTO));
    }


    public CompletableFuture<String> fallBack(OrderRequestDTO orderRequestDTO, RuntimeException runtimeException) {
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time");
    }
}
