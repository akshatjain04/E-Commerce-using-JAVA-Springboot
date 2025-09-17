package com.akshat.ecommerce.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * HTTP Request Logging Filter
 * Design Pattern: Filter Pattern, Decorator Pattern
 * Monitoring: HTTP request/response logging for debugging and monitoring
 */
@Component
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        long startTime = System.currentTimeMillis();

        log.info("Request: {} {} from {}",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                httpRequest.getRemoteAddr());

        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - startTime;

        log.info("Response: {} {} - Status: {} - Duration: {}ms",
                httpRequest.getMethod(),
                httpRequest.getRequestURI(),
                httpResponse.getStatus(),
                duration);
    }
}
