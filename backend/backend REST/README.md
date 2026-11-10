# Wealth-e Backend — V2 Core

Spring Boot REST API for the Wealth-e **Core backend**. The AI backend and frontend are separate team responsibilities.

## Stack
- Java 25
- Spring Boot 3.5.7
- Spring Web
- Spring Data JPA / Hibernate
- MySQL
- Bean Validation
- BCrypt

## Core API

### Health
- `GET /api/health`

### Auth
- `POST /api/auth/register`
- `POST /api/auth/login`

### Accounts
- `GET /api/accounts?userId=1`
- `GET /api/accounts/{id}`
- `POST /api/accounts`
- `PUT /api/accounts/{id}`
- `DELETE /api/accounts/{id}`

### Transactions
- `GET /api/transactions?userId=1`
- `GET /api/transactions/account/{accountId}`
- `POST /api/transactions`
- `PUT /api/transactions/{id}`
- `DELETE /api/transactions/{id}`

Creating/deleting/updating a transaction updates the related account balance atomically.

### Dashboard
- `GET /api/dashboard?userId=1`

Includes balances, income, expenses, asset/liability totals and module counts.

### Budgets
- `GET /api/budgets?userId=1`
- `GET /api/budgets/{id}`
- `POST /api/budgets`
- `PUT /api/budgets/{id}`
- `DELETE /api/budgets/{id}`

Budget responses include `spent` and `remaining` calculated from transactions in the budget date range.

### Goals
- `GET /api/goals?userId=1`
- `GET /api/goals/{id}`
- `POST /api/goals`
- `PUT /api/goals/{id}`
- `DELETE /api/goals/{id}`

### Investments
- `GET /api/investments?userId=1`
- `GET /api/investments/{id}`
- `POST /api/investments`
- `PUT /api/investments/{id}`
- `DELETE /api/investments/{id}`

Responses calculate gain/loss and gain/loss percentage.

### Assets
- `GET /api/assets?userId=1`
- `GET /api/assets/{id}`
- `POST /api/assets`
- `PUT /api/assets/{id}`
- `DELETE /api/assets/{id}`

### Liabilities
- `GET /api/liabilities?userId=1`
- `GET /api/liabilities/{id}`
- `POST /api/liabilities`
- `PUT /api/liabilities/{id}`
- `DELETE /api/liabilities/{id}`

### Net worth
- `GET /api/net-worth?userId=1`

Formula:
`netWorth = totalAssets - totalLiabilities`

Assets include account balances, investment current values and other assets. Liabilities use remaining balance.

### Analytics
- `GET /api/analytics?userId=1`
- Optional date range:
  `GET /api/analytics?userId=1&from=2026-01-01&to=2026-12-31`

Returns total income, expenses, net cash flow, expenses by category and monthly statistics.

## Structure

```text
src/main/java/com/wealthe/
├── config/
├── controller/rest/
├── dto/request/
├── dto/response/
├── entity/
├── enumeration/
├── exception/
├── mapper/
├── repository/
└── service/
```

## Run

```powershell
mvnw.cmd spring-boot:run
```

API: `http://localhost:8080/api/health`

For development MySQL:
```text
DB_URL=jdbc:mysql://localhost:3306/wealth_e?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=your_password
```

## Important scope

This repository contains the **Core backend only**. AI and frontend are intentionally outside this module.
