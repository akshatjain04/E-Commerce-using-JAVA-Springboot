package com.akshat.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Category Response DTO
 * Design Pattern: Data Transfer Object Pattern
 * Optimization: Excludes sensitive information, optimized for API responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDto {
    private String id;
    private String name;
    private String icon;
    private String color;
}
