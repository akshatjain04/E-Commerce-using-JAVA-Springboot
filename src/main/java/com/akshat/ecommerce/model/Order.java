package com.akshat.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Order Entity
 * Design Pattern: Aggregate Root Pattern, Composition Pattern
 * SOLID: Single Responsibility, manages order and its items
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class Order {
    @Id
    private String id;

    @NotNull(message = "Order items are required")
    private List<OrderItem> orderItems;

    private String shippingAddress1;
    private String shippingAddress2;
    private String city;
    private String zip;
    private String country;
    private String phone;

    @Builder.Default
    private String status = "Pending";

    private BigDecimal totalPrice;

    @DBRef
    @NotNull(message = "User is required")
    private User user;

    @CreatedDate
    private LocalDateTime dateOrdered;
}