package com.akshat.ecommerce.service.impl;

import com.akshat.ecommerce.dto.request.CategoryRequestDto;
import com.akshat.ecommerce.dto.response.CategoryResponseDto;
import com.akshat.ecommerce.exception.BadRequestException;
import com.akshat.ecommerce.exception.ResourceNotFoundException;
import com.akshat.ecommerce.model.Category;
import com.akshat.ecommerce.repository.CategoryRepository;
import com.akshat.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Category Service Implementation
 * Design Pattern: Service Layer Pattern, Facade Pattern
 * SOLID: Single Responsibility, Open/Closed, Dependency Inversion
 * DRY: Reusable mapping logic, consistent error handling
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto categoryRequestDto) {
        // Business logic validation
        if (categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new BadRequestException("Category with name '" + categoryRequestDto.getName() + "' already exists");
        }

        Category category = modelMapper.map(categoryRequestDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponseDto updateCategory(String id, CategoryRequestDto categoryRequestDto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        // Check if name already exists for other categories
        if (!existingCategory.getName().equals(categoryRequestDto.getName())
                && categoryRepository.existsByName(categoryRequestDto.getName())) {
            throw new BadRequestException("Category with name '" + categoryRequestDto.getName() + "' already exists");
        }

        modelMapper.map(categoryRequestDto, existingCategory);
        Category updatedCategory = categoryRepository.save(existingCategory);
        return modelMapper.map(updatedCategory, CategoryResponseDto.class);
    }

    @Override
    public void deleteCategory(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
