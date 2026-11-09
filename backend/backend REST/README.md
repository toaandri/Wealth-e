# Wealth-e Backend — V0.1

Spring Boot REST API for the normal/core backend. The AI backend remains a separate responsibility.

## Stack
- Java 25
- Spring Boot 3.5.7
- Spring Web
- Spring Data JPA / Hibernate
- MySQL
- Bean Validation
- BCrypt password hashing

## First 50% API

### Health
`GET /api/health`

### Auth
`POST /api/auth/register`
```json
{"email":"prosper@example.com","password":"secret123","fullName":"Prosper","currency":"MGA"}
```
`POST /api/auth/login`
```json
{"email":"prosper@example.com","password":"secret123"}
```

### Accounts
`GET /api/accounts?userId=1`
`GET /api/accounts/{id}`
`POST /api/accounts`
`PUT /api/accounts/{id}`
`DELETE /api/accounts/{id}`

Create:
```json
{"userId":1,"name":"BMOI","type":"BANK","currency":"MGA","balance":150000}
```
Types: `CASH`, `BANK`, `MOBILE_MONEY`, `SAVINGS`, `INVESTMENT`, `OTHER`

### Transactions
`GET /api/transactions?userId=1`
`GET /api/transactions/account/{accountId}`
`POST /api/transactions`
`DELETE /api/transactions/{id}`

Create:
```json
{"accountId":1,"amount":25000,"type":"EXPENSE","category":"FOOD","description":"Lunch"}
```

### Dashboard
`GET /api/dashboard?userId=1`

## Project structure

```text
src/main/java/com/wealthe/
├── WealthEApplication.java
├── config/        SecurityConfig
├── controller/
│   └── rest/      Auth / Account / Transaction / Dashboard / Health REST controllers
├── dto/
│   ├── request/   LoginRequest, RegisterRequest, CreateAccountRequest, UpdateAccountRequest, CreateTransactionRequest
│   └── response/  AuthResponse, UserResponse, AccountResponse, TransactionResponse, DashboardResponse
├── entity/        User, Account, Transaction
├── enumeration/   AccountType, TransactionType
├── exception/     BusinessException, ResourceNotFoundException, GlobalExceptionHandler
├── mapper/        UserMapper, AccountMapper, TransactionMapper
├── repository/    UserRepository, AccountRepository, TransactionRepository
└── service/       AuthService, UserService, AccountService, TransactionService, DashboardService
```

A Postman collection is available in `postman/WealthE.postman_collection.json`.

## Run on Windows

From `backend`:
```powershell
mvnw.cmd spring-boot:run
```

If the wrapper is not present:
```powershell
mvn spring-boot:run
```

API:
`http://localhost:8080/api/health`

## MySQL

Create the database:
```sql
CREATE DATABASE wealth_e;
```

Set credentials in `application.properties` or environment variables:
```text
DB_URL=jdbc:mysql://localhost:3306/wealth_e
DB_USERNAME=root
DB_PASSWORD=your_password
```

Hibernate uses `ddl-auto=update` for development.

## LAN demo

Backend PC:
```properties
server.address=0.0.0.0
server.port=8080
```

Then frontend PC uses:
`http://BACKEND_PC_IP:8080/api/...`

Find the backend PC IP:
```powershell
ipconfig
```

For production, replace `ddl-auto=update` with migrations and add real JWT/session security before exposing the API publicly.
