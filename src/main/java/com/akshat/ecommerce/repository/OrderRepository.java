package com.akshat.ecommerce.repository;

import com.akshat.ecommerce.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Order Repository
 * Design Pattern: Repository Pattern, Specification Pattern (custom queries)
 * Analytics: Statistical queries for business intelligence
 */
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByUserId(String userId);

    Page<Order> findByUserId(String userId, Pageable pageable);

    @Query("{ 'status': ?0 }")
    List<Order> findByStatus(String status);

    @Query(value = "{}", count = true)
    long getTotalOrdersCount();

    @Query(value = "{}", fields = "{ 'totalPrice': 1 }")
    List<Order> findAllTotalPrices();
}