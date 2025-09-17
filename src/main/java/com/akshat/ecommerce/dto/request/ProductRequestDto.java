package com.akshat.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Product Request DTO
 * Design Pattern: Data Transfer Object Pattern
 * Validation: Input validation for product creation/update
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {
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

    @NotBlank(message = "Category is required")
    private String categoryId;

    @Min(value = 0, message = "Stock count cannot be negative")
    private Integer countInStock;

    private Double rating;
    private Integer numReviews;
    private Boolean isFeatured;
}