package com.akshat.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Product Response DTO
 * Design Pattern: Data Transfer Object Pattern
 * Optimization: Structured response for API consumers
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {
    private String id;
    private String name;
    private String description;
    private String richDescription;
    private String image;
    private List<String> images;
    private String brand;
    private BigDecimal price;
    private CategoryResponseDto category;
    private Integer countInStock;
    private Double rating;
    private Integer numReviews;
    private Boolean isFeatured;
    private LocalDateTime dateCreated;
}