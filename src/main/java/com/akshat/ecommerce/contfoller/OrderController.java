package com.akshat.ecommerce.contfoller;

import com.akshat.ecommerce.dto.request.OrderRequestDto;
import com.akshat.ecommerce.dto.response.OrderResponseDto;
import com.akshat.ecommerce.dto.response.OrderStatisticsDto;
import com.akshat.ecommerce.service.OrderService;

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
import java.util.Map;

/**
 * Order Controller
 * Design Pattern: MVC Pattern, Command Pattern (different order operations)
 * Security: Role-based access control, user-specific data access
 * Features: Order management, statistics, status updates
 */
@RestController
@RequestMapping("${api.url}/orders")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDto> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
        OrderResponseDto createdOrder = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @orderService.getOrderById(#id).user.email == authentication.name")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable String id) {
        OrderResponseDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(
            @PageableDefault(size = 20, sort = "dateOrdered", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OrderResponseDto> orders = orderService.getAllOrders(pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#userId).email == authentication.name")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByUser(@PathVariable String userId) {
        List<OrderResponseDto> orders = orderService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/user/{userId}/paginated")
    @PreAuthorize("hasRole('ADMIN') or @userService.getUserById(#userId).email == authentication.name")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByUserPaginated(
            @PathVariable String userId,
            @PageableDefault(size = 10, sort = "dateOrdered", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<OrderResponseDto> orders = orderService.getOrdersByUser(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderResponseDto> updateOrderStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> statusUpdate) {
        String status = statusUpdate.get("status");
        OrderResponseDto updatedOrder = orderService.updateOrderStatus(id, status);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderStatisticsDto> getOrderStatistics() {
        OrderStatisticsDto statistics = orderService.getOrderStatistics();
        return ResponseEntity.ok(statistics);
    }
}