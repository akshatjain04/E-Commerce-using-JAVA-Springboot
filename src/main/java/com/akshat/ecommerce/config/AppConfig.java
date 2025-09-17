package com.akshat.ecommerce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * Application Configuration Properties
 * Design Pattern: Configuration Pattern, Properties Pattern
 * SOLID: Single Responsibility - application-wide configuration
 */
@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppConfig {

    private String name = "E-commerce Backend";
    private String version = "1.0.0";
    private String description = "Spring Boot E-commerce API";

    private Pagination pagination = new Pagination();
    private Upload upload = new Upload();

    @Data
    public static class Pagination {
        private int defaultSize = 20;
        private int maxSize = 100;
    }

    @Data
    public static class Upload {
        private String path = "/uploads";
        private long maxFileSize = 5242880; // 5MB
        private String[] allowedTypes = { "image/jpeg", "image/png", "image/gif" };
    }
}