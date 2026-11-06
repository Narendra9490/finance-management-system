# Finance Management System

Spring Boot 3.5.x + Java 21 + MySQL REST API based on the supplied Finance REST API PPT.

## Modules
- Customers, Accounts, Transactions, Loans, Investments
- Database-backed HTTP Basic authentication
- Customer document upload/download
- JPA auditing (`createdAt`, `updatedAt`, `createdBy`, `updatedBy`)
- Actuator, Swagger/OpenAPI, caching, AOP logging and scheduled jobs
- Pagination and sorting
- JPQL, native SQL, named queries and derived repository methods
- JUnit 5 and Mockito starter tests

## Prerequisites
- JDK 21
- Maven 3.9+
- MySQL 8+
- Eclipse or IntelliJ IDEA

## Configure MySQL
Create a MySQL user/database as appropriate. The application creates the schema if absent:
`finance_management_db`

Set your database password using environment variable `DB_PASSWORD`.
The dev profile currently defaults to `root` for local learning; change this before sharing or deploying.

## Run
```bash
mvn clean test
mvn spring-boot:run
```

Swagger: http://localhost:8080/swagger-ui.html  
Health: http://localhost:8080/actuator/health

## Customer registration
`POST /api/auth/register` is public for customer onboarding. The server always assigns the `CUSTOMER` role; a client cannot self-assign ADMIN.
```json
{
  "username": "customer1",
  "password": "ChangeMe123!",
  "role": "CUSTOMER"
}
```
Create the first ADMIN through a controlled database seed/manual provisioning process. Never expose public ADMIN registration.

HTTP Basic credentials are sent on each request. Passwords are stored using BCrypt.

## Notes before production
- This is a learning project scaffold, not a production banking platform.
- Public registration must not allow users to assign themselves ADMIN. Replace with admin-controlled user provisioning.
- Configure HTTPS, secrets management, strict actuator exposure and storage scanning.
- Add idempotency keys and robust concurrency handling for financial posting.
- Financial transactions should be immutable after posting; use reversal workflows instead of editing/deleting posted entries.
- Add dedicated loan repayment records and deeper audit history if required.
