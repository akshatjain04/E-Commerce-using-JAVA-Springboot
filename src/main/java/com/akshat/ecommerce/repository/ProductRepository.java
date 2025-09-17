package com.akshat.ecommerce.repository;

import com.akshat.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Product Repository
 * Design Pattern: Repository Pattern, Query Object Pattern
 * Optimization: Pagination support, custom queries for performance
 */
@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    List<Product> findByCategoryId(String categoryId);

    List<Product> findByIsFeaturedTrue();

    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("{ 'category.id': ?0 }")
    Page<Product> findByCategoryId(String categoryId, Pageable pageable);

    @Query("{ $and: [ { 'name': { $regex: ?0, $options: 'i' } }, { 'category.id': ?1 } ] }")
    Page<Product> findByNameContainingIgnoreCaseAndCategoryId(String name, String categoryId, Pageable pageable);
}
