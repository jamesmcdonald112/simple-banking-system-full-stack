# 💳 Simple Banking System — Full Stack (Spring Boot + React)

A production-style full-stack demo banking system, expanded from a simple Java console project into a **Spring Boot backend** + **React/TypeScript frontend**.

This project demonstrates account management, authentication, deposits, transfers, validation, error handling, and integration between frontend and backend.

⚠️ **Note:** For learning/demo purposes only. Passwords are stored in plain text (no hashing).

---
## Live Demo
- [Click here for a live demo](https://simple-banking-system-fullstack.netlify.app/login)
---

## Features

### Account Creation
- Create accounts with **name, email, phone, password**  
- Automatically generates:  
  - **Card number (16 digits)**  
    - BIN: fixed `400000` (Visa-style)  
    - Account Identifier: 9 random digits  
    - Checksum: calculated using the **Luhn Algorithm**  
  - **PIN**: random 4-digit string  

**Example**  
`4000001234567893` → `400000` (BIN) + `123456789` (ID) + `3` (Luhn checksum)

### How Card Numbers Are Generated
Card numbers follow the standard 16-digit credit card format, generated in three steps:
1. BIN (Bank Identification Number)
  - Fixed as 400000 (Visa-style range).
  - Ensures all cards in the system share the same issuing institution prefix.
2. Account Identifier
  - random digits, generated using a secure random number generator.
  - This makes each card unique inside the system.
3. Checksum (Luhn Algorithm)
  - The final digit is calculated using the Luhn algorithm, an industry-standard checksum.
  - Validates that the card number is structurally correct and catches common errors (e.g. swapped digits).

#### Example:
- Generated Card: 4000001122065832
- Generated PIN: 4827
  
#### Breakdown:
- 400000 → BIN
- 112206583 → Random account identifier
- 2 → Luhn checksum

### Authentication
- Login with **card number + PIN + password**  
- Returns account details on success  
- Returns structured error (Problem Details) on failure  

### Deposits
- Deposit positive amounts only  
- Returns updated balance  

### Transfers
- Validates:  
  - Sender ≠ recipient  
  - Sufficient funds  
  - Luhn-valid recipient card  
- Returns updated balances  

### Recipient Search
- Search by name or email (case-insensitive)  
- Returns masked card number (**** last 4 digits)  

---

## 🛠️ Tech Stack

**Backend**
- Java 21, Spring Boot 3.5.x  
- Spring Data JPA (H2/PostgreSQL)  
- Jakarta Validation  
- JUnit 5, Mockito  

**Frontend**
- React 18 + TypeScript  
- Vite  
- react-router  
- react-hot-toast  
- Utility-first CSS  

---

## 🔑 API Overview

**Accounts**  
- `POST /api/accounts` → Create account  
- `GET /api/accounts/recipients?query=...` → Search recipients  
- `POST /api/accounts/{id}/deposit` → Deposit funds  

**Login**  
- `POST /api/login` → Authenticate  

**Transfers**  
- `POST /api/transfers` → Transfer funds  

---

## 🖥 Frontend UX

- **Create Account** — form validation, inline error banners, toast notifications  
- **Account Created** — shows generated card + PIN  
- **Login** — card, PIN, password with error banners  
- **Dashboard** — flip card (balance vs card details), add money modal, transfer modal, logout  
- **NotFound** — friendly 404 with navigation  

---

## 🧪 Testing

**Backend**
```bash
cd backend
./gradlew test
```

Covers:
	•	Card number & checksum generation (Luhn)
	•	Account creation & duplicate email handling
	•	Deposit & transfer (valid/invalid paths)
	•	Login (success & failure)
	•	Controller endpoints with MockMvc

**Frontend**
	•	Manual/integration testing (React Testing Library ready)
