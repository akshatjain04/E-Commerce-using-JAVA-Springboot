// Additional Service and Controller Classes

package com.akshat.ecommerce.service;

import com.akshat.ecommerce.dto.request.ProductRequestDto;
import com.akshat.ecommerce.dto.response.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Product Service Interface
 * Design Pattern: Strategy Pattern for different product operations
 * SOLID: Interface Segregation Principle
 */
public interface ProductService {
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    ProductResponseDto getProductById(String id);

    Page<ProductResponseDto> getAllProducts(Pageable pageable);

    Page<ProductResponseDto> searchProducts(String name, String categoryId, Pageable pageable);

    List<ProductResponseDto> getFeaturedProducts();

    List<ProductResponseDto> getProductsByCategory(String categoryId);

    ProductResponseDto updateProduct(String id, ProductRequestDto productRequestDto);

    void deleteProduct(String id);
}