// Complete Order Service Implementation

package com.akshat.ecommerce.service.impl;

import com.akshat.ecommerce.dto.request.OrderRequestDto;
import com.akshat.ecommerce.dto.response.OrderResponseDto;
import com.akshat.ecommerce.dto.response.OrderStatisticsDto;
import com.akshat.ecommerce.exception.BadRequestException;
import com.akshat.ecommerce.exception.ResourceNotFoundException;
import com.akshat.ecommerce.model.*;
import com.akshat.ecommerce.repository.OrderRepository;
import com.akshat.ecommerce.repository.ProductRepository;
import com.akshat.ecommerce.repository.UserRepository;
import com.akshat.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Order Service Implementation
 * Design Pattern: Service Layer Pattern, Transaction Pattern, Command Pattern
 * SOLID: Single Responsibility, Open/Closed, Dependency Inversion
 * Business Logic: Order processing, inventory management, price calculation
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        // Validate user exists
        User user = userRepository.findById(orderRequestDto.getUserId())
                .orElseThrow(() -> new BadRequestException("User not found with id: " + orderRequestDto.getUserId()));

        // Process order items and calculate total
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderRequestDto.OrderItemRequestDto itemDto : orderRequestDto.getOrderItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new BadRequestException("Product not found with id: " + itemDto.getProductId()));

            // Check stock availability
            if (product.getCountInStock() < itemDto.getQuantity()) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }

            // Create order item
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .build();
            orderItems.add(orderItem);

            // Calculate item total
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity()));
            totalPrice = totalPrice.add(itemTotal);

            // Update product stock
            product.setCountInStock(product.getCountInStock() - itemDto.getQuantity());
            productRepository.save(product);
        }

        // Create order
        Order order = Order.builder()
                .orderItems(orderItems)
                .shippingAddress1(orderRequestDto.getShippingAddress1())
                .shippingAddress2(orderRequestDto.getShippingAddress2())
                .city(orderRequestDto.getCity())
                .zip(orderRequestDto.getZip())
                .country(orderRequestDto.getCountry())
                .phone(orderRequestDto.getPhone())
                .status("Pending")
                .totalPrice(totalPrice)
                .user(user)
                .dateOrdered(LocalDateTime.now())
                .build();

        Order savedOrder = orderRepository.save(order);
        log.info("Order created with id: {} for user: {}", savedOrder.getId(), user.getEmail());

        return mapToOrderResponseDto(savedOrder);
    }

    @Override
    public OrderResponseDto getOrderById(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return mapToOrderResponseDto(order);
    }

    @Override
    public Page<OrderResponseDto> getAllOrders(Pageable pageable) {
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(this::mapToOrderResponseDto);
    }

    @Override
    public List<OrderResponseDto> getOrdersByUser(String userId) {
        return orderRepository.findByUserId(userId).stream()
                .map(this::mapToOrderResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<OrderResponseDto> getOrdersByUser(String userId, Pageable pageable) {
        Page<Order> orders = orderRepository.findByUserId(userId, pageable);
        return orders.map(this::mapToOrderResponseDto);
    }

    @Override
    public OrderResponseDto updateOrderStatus(String id, String status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        String oldStatus = order.getStatus();
        order.setStatus(status);

        // If order is cancelled, restore product stock
        if ("Cancelled".equalsIgnoreCase(status) && !"Cancelled".equalsIgnoreCase(oldStatus)) {
            restoreProductStock(order);
        }

        Order updatedOrder = orderRepository.save(order);
        log.info("Order {} status updated from {} to {}", id, oldStatus, status);

        return mapToOrderResponseDto(updatedOrder);
    }

    @Override
    public void deleteOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        // Restore product stock if order was not cancelled
        if (!"Cancelled".equalsIgnoreCase(order.getStatus())) {
            restoreProductStock(order);
        }

        orderRepository.deleteById(id);
        log.info("Order deleted with id: {}", id);
    }

    @Override
    public OrderStatisticsDto getOrderStatistics() {
        long totalOrders = orderRepository.getTotalOrdersCount();

        List<Order> allOrders = orderRepository.findAll();

        BigDecimal totalRevenue = allOrders.stream()
                .filter(order -> "Completed".equalsIgnoreCase(order.getStatus()))
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageOrderValue = totalOrders > 0
                ? totalRevenue.divide(BigDecimal.valueOf(totalOrders), 2, BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;

        long pendingOrders = allOrders.stream()
                .filter(order -> "Pending".equalsIgnoreCase(order.getStatus()))
                .count();

        long completedOrders = allOrders.stream()
                .filter(order -> "Completed".equalsIgnoreCase(order.getStatus()))
                .count();

        long cancelledOrders = allOrders.stream()
                .filter(order -> "Cancelled".equalsIgnoreCase(order.getStatus()))
                .count();

        return OrderStatisticsDto.builder()
                .totalOrders(totalOrders)
                .totalRevenue(totalRevenue)
                .averageOrderValue(averageOrderValue)
                .pendingOrders(pendingOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .build();
    }

    /**
     * Helper method to restore product stock when order is cancelled
     * Design Pattern: Template Method Pattern
     */
    private void restoreProductStock(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            Product product = orderItem.getProduct();
            product.setCountInStock(product.getCountInStock() + orderItem.getQuantity());
            productRepository.save(product);
        }
    }

    /**
     * Helper method to map Order to OrderResponseDto
     * Design Pattern: Mapper Pattern, avoiding repetitive mapping code
     */
    private OrderResponseDto mapToOrderResponseDto(Order order) {
        List<OrderResponseDto.OrderItemResponseDto> orderItemDtos = order.getOrderItems().stream()
                .map(orderItem -> OrderResponseDto.OrderItemResponseDto.builder()
                        .quantity(orderItem.getQuantity())
                        .product(modelMapper.map(orderItem.getProduct(),
                                com.akshat.ecommerce.dto.response.ProductResponseDto.class))
                        .build())
                .collect(Collectors.toList());

        return OrderResponseDto.builder()
                .id(order.getId())
                .orderItems(orderItemDtos)
                .shippingAddress1(order.getShippingAddress1())
                .shippingAddress2(order.getShippingAddress2())
                .city(order.getCity())
                .zip(order.getZip())
                .country(order.getCountry())
                .phone(order.getPhone())
                .status(order.getStatus())
                .totalPrice(order.getTotalPrice())
                .user(modelMapper.map(order.getUser(), com.akshat.ecommerce.dto.response.UserResponseDto.class))
                .dateOrdered(order.getDateOrdered())
                .build();
    }
}
