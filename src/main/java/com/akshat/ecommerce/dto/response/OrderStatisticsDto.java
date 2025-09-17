package com.akshat.ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Order Statistics DTO
 * Design Pattern: Data Transfer Object Pattern
 * Analytics: Business intelligence data structure
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatisticsDto {
    private Long totalOrders;
    private BigDecimal totalRevenue;
    private BigDecimal averageOrderValue;
    private Long pendingOrders;
    private Long completedOrders;
    private Long cancelledOrders;
}
