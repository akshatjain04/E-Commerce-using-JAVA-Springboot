package com.akshat.ecommerce.service;

import com.akshat.ecommerce.dto.request.OrderRequestDto;
import com.akshat.ecommerce.dto.response.OrderResponseDto;
import com.akshat.ecommerce.dto.response.OrderStatisticsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Order Service Interface
 * Design Pattern: Strategy Pattern for order operations
 * Business Logic: Order management and statistics
 */
public interface OrderService {
    OrderResponseDto createOrder(OrderRequestDto orderRequestDto);

    OrderResponseDto getOrderById(String id);

    Page<OrderResponseDto> getAllOrders(Pageable pageable);

    List<OrderResponseDto> getOrdersByUser(String userId);

    Page<OrderResponseDto> getOrdersByUser(String userId, Pageable pageable);

    OrderResponseDto updateOrderStatus(String id, String status);

    void deleteOrder(String id);

    OrderStatisticsDto getOrderStatistics();
}
