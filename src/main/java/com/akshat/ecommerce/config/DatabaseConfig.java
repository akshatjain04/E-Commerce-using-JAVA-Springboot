package com.akshat.ecommerce.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

/**
 * Database Configuration
 * Design Pattern: Template Method Pattern (AbstractMongoClientConfiguration)
 * SOLID: Single Responsibility - database configuration only
 */
@Configuration
@Slf4j
public class DatabaseConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.database}")
    private String databaseName;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    protected boolean autoIndexCreation() {
        return true;
    }
}