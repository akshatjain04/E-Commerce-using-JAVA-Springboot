package com.akshat.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Product Entity
 * Design Pattern: Builder Pattern, Composition Pattern (Category reference)
 * SOLID: Single Responsibility, Open/Closed (extensible via inheritance)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
    @Id
    private String id;

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;
    private String richDescription;
    private String image;
    private List<String> images;
    private String brand;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @DBRef
    @NotNull(message = "Category is required")
    private Category category;

    @Min(value = 0, message = "Stock count cannot be negative")
    private Integer countInStock;

    @Builder.Default
    private Double rating = 0.0;

    @Builder.Default
    private Integer numReviews = 0;

    @Builder.Default
    private Boolean isFeatured = false;

    @CreatedDate
    private LocalDateTime dateCreated;
}