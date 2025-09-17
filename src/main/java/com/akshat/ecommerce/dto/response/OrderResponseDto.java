package com.akshat.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Order Response DTO
 * Design Pattern: Data Transfer Object Pattern
 * Structure: Complete order information for API responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private String id;
    private List<OrderItemResponseDto> orderItems;
    private String shippingAddress1;
    private String shippingAddress2;
    private String city;
    private String zip;
    private String country;
    private String phone;
    private String status;
    private BigDecimal totalPrice;
    private UserResponseDto user;
    private LocalDateTime dateOrdered;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemResponseDto {
        private Integer quantity;
        private ProductResponseDto product;
    }
}