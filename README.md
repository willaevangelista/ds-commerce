<div align='center' id='top'>

# DSCommerce - E-commerce REST API with OAuth2 Authentication

  ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
  ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
  ![Spring Security](https://img.shields.io/badge/spring%20security-%236DB33F.svg?style=for-the-badge&logo=springsecurity&logoColor=white)
  ![Hibernate](https://img.shields.io/badge/hibernate-%2359666C.svg?style=for-the-badge&logo=hibernate&logoColor=white)
  ![H2](https://img.shields.io/badge/h2database-%23336791.svg?style=for-the-badge&logo=h2&logoColor=white)
  ![Maven](https://img.shields.io/badge/maven-%23C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)
  ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)

</div>

A Spring Boot application built to practice **REST API development, OAuth2/JWT authentication, and role-based authorization** in a realistic e-commerce domain. The project implements product catalog management, category listing, user profile access, and order placement, secured through a **custom OAuth2 Resource Owner Password Credentials grant** and stateless JWT-based resource protection.
<br><br>
The goal of this project is to expose a REST web service for a small e-commerce platform, where users can browse products and categories, authenticate to obtain a JWT access token, view their own profile and orders, and place new orders. Administrators have additional privileges to manage the product catalog. The API enforces field-level validation, ownership-based authorization, and consistent, structured error responses.

---

## Table of Contents
- [Technologies](#technologies)
- [Architecture](#architecture)
- [Project Structure](#projectStructure)
- [Domain Model](#domainModel)
- [Authentication & Authorization](#authAndAuthz)
- [Endpoints](#endpoints)
- [Exception Handling](#exceptionHandling)
- [Validation Rules](#validationRules)
- [Seeding Data](#seedingData)
- [Running Locally](#runningLocally)
- [Running Tests](#runningTests)
- [License](#license)

---

<div id='technologies'/>

## Technologies

| Badge | Technology | Purpose |
|---|---|---|
| ![Java](https://img.shields.io/badge/Java_25-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) | Java | Programming language |
| ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) | Spring Boot | Application framework |
| ![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) | Spring Security / OAuth2 | Authentication and authorization |
| ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white) | Hibernate / JPA | Object-Relational Mapping (ORM) |
| ![H2](https://img.shields.io/badge/H2_Database-336791?style=for-the-badge&logo=h2&logoColor=white) | H2 Database | In-memory relational database (test/dev environment) |
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) | Maven | Build and dependency management |
| ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) | JWT (Nimbus JOSE + JWT) | Stateless, self-contained access tokens |

---

<div id='architecture'/>

## Architecture

This project follows a classic **layered architecture**, separating responsibilities across controllers, services, DTOs, security configuration, and exception handling:

- **controllers**: expose the REST endpoints, apply method-level authorization (`@PreAuthorize`), and delegate business logic to the service layer.
- **services**: contain the business logic, orchestrate repository calls, resolve the authenticated user from the JWT, and throw domain-specific exceptions when needed.
- **entities**: JPA entities (`User`, `Role`, `Product`, `Category`, `Order`, `OrderItem`, `Payment`), mapped through standard Hibernate annotations. `User` implements `UserDetails` and `Role` implements `GrantedAuthority` to integrate directly with Spring Security.
- **dtos**: Data Transfer Objects used to decouple the REST API contract from the persistence model, including Bean Validation constraints (`ProductDTO`, `ProductMinDTO`, `CategoryDTO`, `UserDTO`, `OrderDTO`, `OrderItemDTO`, `ClientDTO`, `PaymentDTO`).
- **repositories**: Spring Data JPA repositories responsible for database access, including a custom projection (`UserDetailsProjection`) used to load user credentials and roles in a single query.
- **security / config**: the OAuth2 Authorization Server configuration (JWT issuing, RSA key pair, custom password grant), the Resource Server configuration (JWT validation, CORS, method security), and the custom authentication classes that implement the password grant.
- **exceptions**: custom exceptions (`ResourceNotFoundException`, `DatabaseException`, `ForbiddenException`) and supporting error-response classes (`CustomError`, `ValidationError`, `FieldMessage`), handled globally by `ControllerExceptionHandler`.
- **resources**: application configuration files (`application.properties`, `application-test.properties`) and the seed data script (`import.sql`).

---

<div id='projectStructure'/>

## Project Structure

```
dscommerce/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/willaevangelista/dscommerce/
│   │   │       ├── config/
│   │   │       │   ├── customgrant/
│   │   │       │   │   ├── CustomPasswordAuthenticationConverter.java
│   │   │       │   │   ├── CustomPasswordAuthenticationProvider.java
│   │   │       │   │   ├── CustomPasswordAuthenticationToken.java
│   │   │       │   │   └── CustomUserAuthorities.java
│   │   │       │   ├── AuthorizationServerConfig.java
│   │   │       │   └── ResourceServerConfig.java
│   │   │       ├── controllers/
│   │   │       │   ├── CategoryController.java
│   │   │       │   ├── OrderController.java
│   │   │       │   ├── ProductController.java
│   │   │       │   ├── UserController.java
│   │   │       │   └── ControllerExceptionHandler.java
│   │   │       ├── dto/
│   │   │       │   ├── CategoryDTO.java
│   │   │       │   ├── ClientDTO.java
│   │   │       │   ├── OrderDTO.java
│   │   │       │   ├── OrderItemDTO.java
│   │   │       │   ├── PaymentDTO.java
│   │   │       │   ├── ProductDTO.java
│   │   │       │   ├── ProductMinDTO.java
│   │   │       │   ├── UserDTO.java
│   │   │       │   ├── CustomError.java
│   │   │       │   ├── FieldMessage.java
│   │   │       │   └── ValidationError.java
│   │   │       ├── entities/
│   │   │       │   ├── Category.java
│   │   │       │   ├── Order.java
│   │   │       │   ├── OrderItem.java
│   │   │       │   ├── OrderItemPK.java
│   │   │       │   ├── OrderStatus.java
│   │   │       │   ├── Payment.java
│   │   │       │   ├── Product.java
│   │   │       │   ├── Role.java
│   │   │       │   └── User.java
│   │   │       ├── projections/
│   │   │       │   └── UserDetailsProjection.java
│   │   │       ├── repositories/
│   │   │       │   ├── CategoryRepository.java
│   │   │       │   ├── OrderItemRepository.java
│   │   │       │   ├── OrderRepository.java
│   │   │       │   ├── ProductRepository.java
│   │   │       │   └── UserRepository.java
│   │   │       ├── services/
│   │   │       │   ├── exceptions/
│   │   │       │   │   ├── DatabaseException.java
│   │   │       │   │   ├── ForbiddenException.java
│   │   │       │   │   └── ResourceNotFoundException.java
│   │   │       │   ├── AuthService.java
│   │   │       │   ├── CategoryService.java
│   │   │       │   ├── OrderService.java
│   │   │       │   ├── ProductService.java
│   │   │       │   └── UserService.java
│   │   │       └── DscommerceApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── application-test.properties
│   │       └── import.sql
│   └── test/
│       └── java/
│           └── com/willaevangelista/dscommerce/
│               └── ... (unit and integration tests)
├── .gitignore
├── LICENSE
└── README.md
```

---

<div id='domainModel'/>

## Domain Model

| Entity | Description |
|---|---|
| `User` | A registered user, implementing `UserDetails`. Contains `name`, `email`, `phone`, `password` (BCrypt-hashed), `birthDate`, a many-to-many relationship to `Role`, and a one-to-many relationship to `Order`. |
| `Role` | An authority granted to a user (`ROLE_CLIENT`, `ROLE_ADMIN`), implementing `GrantedAuthority`. |
| `Product` | A catalog item with `name`, `description`, `price`, `imgUrl`, and a many-to-many relationship to `Category`. |
| `Category` | A product category (`name`), associated with one or more products. |
| `Order` | A client's order, with `moment`, `status` (`OrderStatus`), a `client` (`User`), an optional `payment`, and a list of `OrderItem`. |
| `OrderItem` | An association between an `Order` and a `Product`, with `price` and `quantity` captured at the time of purchase, identified by the composite key `OrderItemPK`. |
| `Payment` | The payment confirmation for an order, with a `moment`, sharing its id with the associated `Order`. |
| `OrderStatus` | An enum representing the order lifecycle: `WAITING_PAYMENT`, `PAID`, `SHIPPED`, `DELIVERED`, `CANCELED`. |

---

<div id='authAndAuthz'/>

## Authentication & Authorization

Authentication is implemented as a standalone **OAuth2 Authorization Server**, exposing a custom **Resource Owner Password Credentials grant** at `/oauth2/token`, backed by a **Resource Server** that validates self-contained JWT access tokens signed with an RSA key pair.

- **Obtaining a token**: clients authenticate with HTTP Basic (`client-id` / `client-secret`) and send `grant_type=password`, `username`, and `password` as `x-www-form-urlencoded` parameters to `/oauth2/token`.
- **Token contents**: each JWT carries the authenticated user's `username` and `authorities` (roles) as custom claims, added by an `OAuth2TokenCustomizer`.
- **Authorization**: protected endpoints use `@PreAuthorize` with `hasRole` / `hasAnyRole` expressions to restrict access by role.
- **Ownership checks**: some endpoints go beyond role checks — `AuthService.validateSelfOrAdmin` ensures a client can only access their own resources (e.g. their own orders), unless they are an admin.
- **Roles**:
  - `ROLE_CLIENT`: can browse products and categories, view and place their own orders, and view their own profile.
  - `ROLE_ADMIN`: has all client privileges, plus the ability to create, update, and delete products.

---

<div id='endpoints'/>

## Endpoints

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/oauth2/token` | Public (client credentials) | Obtain a JWT access token via the password grant |
| `GET` | `/products` | Public | Paginated search of products by name |
| `GET` | `/products/{id}` | Public | Find a product by id |
| `POST` | `/products` | `ROLE_ADMIN` | Insert a new product |
| `PUT` | `/products/{id}` | `ROLE_ADMIN` | Update an existing product |
| `DELETE` | `/products/{id}` | `ROLE_ADMIN` | Delete a product |
| `GET` | `/categories` | Public | List all categories |
| `GET` | `/users/me` | `ROLE_ADMIN`, `ROLE_CLIENT` | Get the authenticated user's profile |
| `GET` | `/orders/{id}` | `ROLE_ADMIN`, `ROLE_CLIENT` (owner only) | Find an order by id |
| `POST` | `/orders` | `ROLE_CLIENT` | Place a new order for the authenticated client |

---

<div id='exceptionHandling'/>

## Exception Handling

The application handles the following scenarios through a global exception handler (`ControllerExceptionHandler`):

| Scenario | HTTP Status | Exception |
|---|---|---|
| Id not found (`GET` by id, `PUT`, `DELETE`) | `404 Not Found` | `ResourceNotFoundException` |
| Database integrity violation | `400 Bad Request` | `DatabaseException` |
| Access denied (not the resource owner, insufficient role) | `403 Forbidden` | `ForbiddenException` |
| Bean Validation failure | `422 Unprocessable Content` | `MethodArgumentNotValidException` (mapped to `ValidationError`) |

Validation errors return a custom response body containing a list of field-specific error messages, making it clear which field failed validation and why.

---

<div id='validationRules'/>

## Validation Rules

The following validation rules are applied to `ProductDTO`:

- **name**: must not be blank and must be between 3 and 80 characters (`@NotBlank`, `@Size`).
- **description**: must not be blank and must have at least 10 characters (`@NotBlank`, `@Size`).
- **price**: is required and must be a positive number (`@NotNull`, `@Positive`).
- **categories**: must contain at least one category (`@NotEmpty`).

The following validation rule is applied to `OrderDTO`:

- **items**: must contain at least one order item (`@NotEmpty`).

When a validation rule is violated, the API responds with status `422` and a custom error message for each invalid field.

---

<div id='seedingData'/>

## Seeding Data

On startup, the application populates the H2 in-memory database via `import.sql` with **categories, products, users (with roles), and sample orders with items and payments**, allowing the API endpoints — including authentication — to be tested immediately without any manual data entry.

---

<div id='runningLocally'/>

## Running Locally

```bash
./mvnw spring-boot:run
```

Once the application starts, the H2 console will be available at:

```
http://localhost:8080/h2-console
```

Use the JDBC URL configured in `application.properties` to connect and explore the seeded data.

The REST API will be available at:

```
http://localhost:8080
```

To obtain an access token, send a `POST` request to `http://localhost:8080/oauth2/token` with HTTP Basic authentication (client-id / client-secret) and the following `x-www-form-urlencoded` body:

```
grant_type=password
username=<seeded user email>
password=<seeded user password>
```

---

<div id='runningTests'/>

## Running Tests

The project has a dedicated test environment configured in `application-test.properties`, using the H2 database. To run the test suite:

```bash
./mvnw test
```

---

<div id='license'/>

## License

This project is licensed under the MIT License - see the `LICENSE` file for details.

<div align='right'>

  [Back to top of page ⬆️](#top)

</div>
