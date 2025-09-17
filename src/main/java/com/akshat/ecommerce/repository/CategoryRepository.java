// Repository Layer (Data Access Layer)

package com.akshat.ecommerce.repository;

import com.akshat.ecommerce.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Category Repository
 * Design Pattern: Repository Pattern, Data Access Object Pattern
 * SOLID: Interface Segregation, Dependency Inversion
 */
@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByName(String name);

    boolean existsByName(String name);
}
