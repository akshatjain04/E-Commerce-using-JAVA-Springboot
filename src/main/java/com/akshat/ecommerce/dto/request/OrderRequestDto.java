package com.akshat.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * Order Request DTO
 * Design Pattern: Data Transfer Object Pattern, Composite Pattern
 * Validation: Order and order items validation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    @NotEmpty(message = "Order items are required")
    @Valid
    private List<OrderItemRequestDto> orderItems;

    private String shippingAddress1;
    private String shippingAddress2;
    private String city;
    private String zip;
    private String country;
    private String phone;

    @NotBlank(message = "User ID is required")
    private String userId;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemRequestDto {
        @NotNull(message = "Quantity is required")
        private Integer quantity;

        @NotBlank(message = "Product ID is required")
        private String productId;
    }
}