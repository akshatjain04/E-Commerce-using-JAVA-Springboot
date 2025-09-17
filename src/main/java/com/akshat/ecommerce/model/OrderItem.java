package com.akshat.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * OrderItem Entity
 * Design Pattern: Composition Pattern (Product reference)
 * SOLID: Single Responsibility - represents order line item
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @DBRef
    @NotNull(message = "Product is required")
    private Product product;
}