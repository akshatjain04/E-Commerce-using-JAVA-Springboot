# E-commerce Backend API

A comprehensive Spring Boot e-commerce backend application with MongoDB, featuring JWT authentication, role-based access control, and RESTful APIs for managing categories, products, users, and orders.

## Features

- **Authentication & Authorization**: JWT-based authentication with role-based access control
- **User Management**: User registration, login, profile management
- **Product Management**: CRUD operations, search, filtering, pagination
- **Category Management**: Product categorization system
- **Order Management**: Order creation, tracking, statistics
- **Security**: Password encryption, JWT tokens, CORS configuration
- **Validation**: Input validation using Bean Validation
- **Error Handling**: Global exception handling with proper HTTP status codes
- **Logging**: Comprehensive logging with configurable levels
- **Database**: MongoDB with Spring Data MongoDB

## Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Database**: MongoDB
- **Security**: Spring Security with JWT
- **Documentation**: Built-in API documentation
- **Build Tool**: Maven
- **Java Version**: 17

## Architecture

This project follows Clean Architecture principles with clear separation of concerns:

```
├── controller/     # Presentation Layer (REST Controllers)
├── service/        # Business Logic Layer
├── repository/     # Data Access Layer
├── model/          # Domain Models/Entities
├── dto/            # Data Transfer Objects
├── config/         # Configuration Classes
├── security/       # Security Components
├── filter/         # Custom Filters
└── exception/      # Exception Handling
```

## Design Patterns Implemented

- **MVC Pattern**: Clear separation of concerns
- **Repository Pattern**: Data access abstraction
- **Service Layer Pattern**: Business logic encapsulation
- **Builder Pattern**: Object construction (via Lombok)
- **Dependency Injection**: Loose coupling
- **Strategy Pattern**: Authentication strategies
- **Factory Pattern**: Bean creation
- **Filter Pattern**: Request/response processing

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB 4.4+

### Installation

1. Clone the repository

```bash
git clone https://github.com/your-username/ecommerce-backend.git
cd ecommerce-backend
```

2. Configure MongoDB connection in `application.yml`

```yaml
spring:
  data:
    mongodb:
      uri: mongodb://localhost:27017/ecommerce
```

3. Build the project

```bash
mvn clean install
```

4. Run the application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication

- `POST /api/v1/users/register` - User registration
- `POST /api/v1/users/login` - User login

### Categories

- `GET /api/v1/categories` - Get all categories
- `POST /api/v1/categories` - Create category (Admin only)
- `GET /api/v1/categories/{id}` - Get category by ID
- `PUT /api/v1/categories/{id}` - Update category (Admin only)
- `DELETE /api/v1/categories/{id}` - Delete category (Admin only)

### Products

- `GET /api/v1/products` - Get all products (paginated)
- `POST /api/v1/products` - Create product (Admin only)
- `GET /api/v1/products/{id}` - Get product by ID
- `PUT /api/v1/products/{id}` - Update product (Admin only)
- `DELETE /api/v1/products/{id}` - Delete product (Admin only)
- `GET /api/v1/products/search` - Search products
- `GET /api/v1/products/featured` - Get featured products
- `GET /api/v1/products/category/{categoryId}` - Get products by category

### Users

- `GET /api/v1/users` - Get all users (Admin only)
- `GET /api/v1/users/{id}` - Get user by ID
- `PUT /api/v1/users/{id}` - Update user
- `DELETE /api/v1/users/{id}` - Delete user (Admin only)

### Orders

- `GET /api/v1/orders` - Get all orders (Admin only)
- `POST /api/v1/orders` - Create order
- `GET /api/v1/orders/{id}` - Get order by ID
- `PUT /api/v1/orders/{id}/status` - Update order status
- `GET /api/v1/orders/user/{userId}` - Get orders by user
- `GET /api/v1/orders/statistics` - Get order statistics (Admin only)

## Environment Variables

```bash
MONGODB_URI=mongodb://localhost:27017/ecommerce
MONGODB_DATABASE=ecommerce
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000
API_URL=/api/v1
CORS_ALLOWED_ORIGINS=*
```

## Testing

Run tests with:

```bash
mvn test
```

==================================================
PROJECT STRUCTURE SUMMARY:
==================================================

This Spring Boot E-commerce project follows industry best practices and design patterns:

### Design Patterns Implemented:

1. **MVC Pattern**: Clear separation between Model, View (REST API), and Controller
2. **Repository Pattern**: Data access abstraction through Spring Data repositories
3. **Service Layer Pattern**: Business logic separation from controllers and repositories
4. **Builder Pattern**: Used with Lombok for object construction
5. **Dependency Injection Pattern**: Spring's core pattern for loose coupling
6. **Strategy Pattern**: Different authentication strategies, JWT token handling
7. **Factory Pattern**: Spring's bean factory for object creation
8. **Adapter Pattern**: UserDetails adapter for Spring Security integration
9. **Template Method Pattern**: AbstractMongoClientConfiguration, Spring templates
10. **Filter Pattern**: JWT authentication filter, logging filter
11. **DTO Pattern**: Data transfer objects for API requests/responses
12. **Singleton Pattern**: Spring beans are singletons by default

### SOLID Principles Applied:

1. **Single Responsibility**: Each class has one reason to change
2. **Open/Closed**: Classes are open for extension, closed for modification
3. **Liskov Substitution**: Interfaces can be substituted with implementations
4. **Interface Segregation**: Small, focused interfaces
5. **Dependency Inversion**: Depend on abstractions, not concretions

### Key Features:

- **Security**: JWT authentication, password encryption, role-based access
- **Database**: MongoDB with Spring Data MongoDB
- **Validation**: Input validation using Bean Validation
- **Error Handling**: Global exception handling with proper HTTP status codes
- **Logging**: Comprehensive logging with configurable levels
- **CORS**: Cross-origin resource sharing configuration
- **Configuration**: Profile-based configuration (dev, prod)
- **Monitoring**: Actuator endpoints for health checks

### Additional Optimizations:

- **Performance**: Database indexing, pagination support
- **Security**: Password hashing, JWT token expiration
- **Maintainability**: Clear package structure, comprehensive documentation
- **Scalability**: Stateless design, horizontal scaling ready
- **Testing**: Structured for unit and integration testing

This project structure provides a solid foundation for an enterprise-level e-commerce application with room for future enhancements and scaling.

Directory Structure:

src/
├── main/
│ ├── java/
│ │ └── com/
│ │ └── ecommerce/
│ │ ├── EcommerceApplication.java
│ │ ├── config/
│ │ │ ├── DatabaseConfig.java
│ │ │ ├── SecurityConfig.java
│ │ │ ├── CorsConfig.java
│ │ │ └── ModelMapperConfig.java
│ │ ├── controller/
│ │ │ ├── CategoryController.java
│ │ │ ├── ProductController.java
│ │ │ ├── UserController.java
│ │ │ └── OrderController.java
│ │ ├── dto/
│ │ │ ├── request/
│ │ │ │ ├── CategoryRequestDto.java
│ │ │ │ ├── ProductRequestDto.java
│ │ │ │ ├── UserRegistrationDto.java
│ │ │ │ ├── UserLoginDto.java
│ │ │ │ └── OrderRequestDto.java
│ │ │ └── response/
│ │ │ ├── CategoryResponseDto.java
│ │ │ ├── ProductResponseDto.java
│ │ │ ├── UserResponseDto.java
│ │ │ ├── OrderResponseDto.java
│ │ │ └── AuthResponseDto.java
│ │ ├── exception/
│ │ │ ├── GlobalExceptionHandler.java
│ │ │ ├── ResourceNotFoundException.java
│ │ │ ├── BadRequestException.java
│ │ │ └── UnauthorizedException.java
│ │ ├── filter/
│ │ │ ├── JwtAuthenticationFilter.java
│ │ │ └── LoggingFilter.java
│ │ ├── model/
│ │ │ ├── Category.java
│ │ │ ├── Product.java
│ │ │ ├── User.java
│ │ │ ├── Order.java
│ │ │ └── OrderItem.java
│ │ ├── repository/
│ │ │ ├── CategoryRepository.java
│ │ │ ├── ProductRepository.java
│ │ │ ├── UserRepository.java
│ │ │ └── OrderRepository.java
│ │ ├── security/
│ │ │ ├── JwtUtil.java
│ │ │ ├── UserDetailsServiceImpl.java
│ │ │ └── CustomUserDetails.java
│ │ └── service/
│ │ ├── CategoryService.java
│ │ ├── ProductService.java
│ │ ├── UserService.java
│ │ ├── OrderService.java
│ │ └── impl/
│ │ ├── CategoryServiceImpl.java
│ │ ├── ProductServiceImpl.java
│ │ ├── UserServiceImpl.java
│ │ └── OrderServiceImpl.java
├── test/
│ └── java/
│ └── com/
│ └── ecommerce/
│ ├── EcommerceApplicationTests.java
│ ├── controller/
│ ├── service/
│ └── repository/
├── pom.xml
└── README.md
