package com.akshat.ecommerce.service.impl;

import com.akshat.ecommerce.dto.request.ProductRequestDto;
import com.akshat.ecommerce.dto.response.ProductResponseDto;
import com.akshat.ecommerce.exception.BadRequestException;
import com.akshat.ecommerce.exception.ResourceNotFoundException;
import com.akshat.ecommerce.model.Category;
import com.akshat.ecommerce.model.Product;
import com.akshat.ecommerce.repository.CategoryRepository;
import com.akshat.ecommerce.repository.ProductRepository;
import com.akshat.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Product Service Implementation
 * Design Pattern: Service Layer Pattern, Repository Pattern integration
 * SOLID: Single Responsibility, Dependency Inversion
 * Business Logic: Product management with category validation
 */
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        // Validate category exists
        Category category = categoryRepository.findById(productRequestDto.getCategoryId())
                .orElseThrow(() -> new BadRequestException(
                        "Category not found with id: " + productRequestDto.getCategoryId()));

        Product product = modelMapper.map(productRequestDto, Product.class);
        product.setCategory(category);
        product.setDateCreated(LocalDateTime.now());

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductResponseDto.class);
    }

    @Override
    public ProductResponseDto getProductById(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return modelMapper.map(product, ProductResponseDto.class);
    }

    @Override
    public Page<ProductResponseDto> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(product -> modelMapper.map(product, ProductResponseDto.class));
    }

    @Override
    public Page<ProductResponseDto> searchProducts(String name, String categoryId, Pageable pageable) {
        Page<Product> products;

        if (name != null && categoryId != null) {
            products = productRepository.findByNameContainingIgnoreCaseAndCategoryId(name, categoryId, pageable);
        } else if (name != null) {
            products = productRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }

        return products.map(product -> modelMapper.map(product, ProductResponseDto.class));
    }

    @Override
    public List<ProductResponseDto> getFeaturedProducts() {
        return productRepository.findByIsFeaturedTrue().stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getProductsByCategory(String categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto updateProduct(String id, ProductRequestDto productRequestDto) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        // Validate category if changed
        if (!existingProduct.getCategory().getId().equals(productRequestDto.getCategoryId())) {
            Category category = categoryRepository.findById(productRequestDto.getCategoryId())
                    .orElseThrow(() -> new BadRequestException(
                            "Category not found with id: " + productRequestDto.getCategoryId()));
            existingProduct.setCategory(category);
        }

        modelMapper.map(productRequestDto, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductResponseDto.class);
    }

    @Override
    public void deleteProduct(String id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
