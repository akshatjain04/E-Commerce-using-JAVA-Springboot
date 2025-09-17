// Service Layer (Business Logic Layer)
package com.akshat.ecommerce.service;

import com.akshat.ecommerce.dto.request.CategoryRequestDto;
import com.akshat.ecommerce.dto.response.CategoryResponseDto;

import java.util.List;

/**
 * Category Service Interface
 * Design Pattern: Strategy Pattern, Interface Segregation Principle
 * SOLID: Dependency Inversion - depend on abstraction, not concrete
 * implementation
 */
public interface CategoryService {
    CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto getCategoryById(String id);

    List<CategoryResponseDto> getAllCategories();

    CategoryResponseDto updateCategory(String id, CategoryRequestDto categoryRequestDto);

    void deleteCategory(String id);
}