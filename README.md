# Ecommerce Backend API
A RESTful API built with Spring Boot for a full-stack Ecommerce Web Application.

## Project Description
Ecommerce Backend API is a robust, secure, and scalable backend service developed using Spring Boot.
It powers a modern ecommerce platform, enabling functionalities like user authentication, product management, cart handling, order processing, and payment integration.

## Key Features
-  JWT-based Authentication & Authorization
-  Product & Category Management with Image Upload (Cloudinary)
-  Wishlist & Cart Functionality
-  Order Management with Payment Integration
-  Admin APIs for User and Order Management
-  RESTful APIs with Error Handling
-  Secure with Role-Based Access (Admin/User)

## Technologies Used
- Java, Spring Boot, Spring Security, Hibernate, JPA
- MySQL, Cloudinary, JWT
- Maven, Docker, Postman

## API Endpoints Overview
| Resource    | Methods | Endpoint Example                  | Description            |
|--------------|---------|-----------------------------------|------------------------|
| Auth         | POST    | `/api/auth/login` `/register`    | Login & Register       |
| Product      | CRUD    | `/api/products`                  | Manage Products        |
| Category     | CRUD    | `/api/categories`                | Manage Categories      |
| Cart         | CRUD    | `/api/cart`                      | Manage Cart Items      |
| Wishlist     | CRUD    | `/api/wishlist`                  | Manage Wishlist Items  |
| Order        | GET/POST| `/api/orders`                    | Place/View Orders      |
| Admin        | GET/DELETE| `/api/admin/users` `/orders`   | Manage Users & Orders  |

## Project Architecture
   ecommerce-backend-api/
│
├── src/main/java/com/example/ecommerce
│   ├── controller/        → API Controllers
│   ├── model/             → Entity Models
│   ├── repository/        → JPA Repositories
│   ├── service/           → Business Logic
│   ├── security/          → JWT & Security Config
│   └── EcommerceApplication.java
│
├── src/main/resources/
│   ├── application.properties → DB, JWT, Cloudinary Config
│
├── uploads/                → Local file storage (optional)
├── Dockerfile               → For containerization
├── pom.xml                  → Dependencies & Build Config

