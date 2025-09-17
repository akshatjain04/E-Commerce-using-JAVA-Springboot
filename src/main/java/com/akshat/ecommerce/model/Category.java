package com.akshat.ecommerce.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Category Entity
 * Design Pattern: Builder Pattern (via Lombok)
 * SOLID: Single Responsibility - represents only category data
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "categories")
public class Category {
    @Id
    private String id;

    @NotBlank(message = "Category name is required")
    @Indexed(unique = true)
    private String name;

    private String icon;
    private String color;
}
