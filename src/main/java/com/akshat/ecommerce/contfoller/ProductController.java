package com.akshat.ecommerce.contfoller;

import com.akshat.ecommerce.dto.request.ProductRequestDto;
import com.akshat.ecommerce.dto.response.ProductResponseDto;
import com.akshat.ecommerce.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Product Controller
 * Design Pattern: MVC Pattern, RESTful API Pattern
 * Features: CRUD operations, search, filtering, pagination
 * Security: Admin-only access for CUD operations
 */
@RestController
@RequestMapping("${api.url}/products")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto createdProduct = productService.createProduct(productRequestDto);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable String id) {
        ProductResponseDto product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(
            @PageableDefault(size = 20, sort = "dateCreated", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductResponseDto> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDto>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String categoryId,
            @PageableDefault(size = 20, sort = "dateCreated", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductResponseDto> products = productService.searchProducts(name, categoryId, pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/featured")
    public ResponseEntity<List<ProductResponseDto>> getFeaturedProducts() {
        List<ProductResponseDto> products = productService.getFeaturedProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCategory(@PathVariable String categoryId) {
        List<ProductResponseDto> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDto> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody ProductRequestDto productRequestDto) {
        ProductResponseDto updatedProduct = productService.updateProduct(id, productRequestDto);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}