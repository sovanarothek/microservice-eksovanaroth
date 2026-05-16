## 🔄 System Workflow

# Microservice System (Spring Boot)

This project is a microservice-based system built using Spring Boot.  
It demonstrates how services communicate using **Eureka Discovery Server**, **API Gateway**, and centralized configuration.

---

## 🏗️ Architecture Overview

The system contains the following services:

- **Discovery Server (Eureka Server)**
- **Config Server**
- **API Gateway**
- **User Service**
- **Order Service**

---

## 🔗 How Everything Works

### 1. Discovery Server (Eureka)

- Acts as a **service registry**
- All microservices register themselves here
- Services find each other through Eureka instead of hardcoded URLs

Example:
- User Service registers as: `USER-SERVICE`
- Order Service registers as: `ORDER-SERVICE`

👉 So instead of calling:

http://localhost:8082/user

We call using service name:

http://USER-SERVICE/user

Eureka handles routing automatically.

---

### 2. API Gateway

- Single entry point for all requests
- Routes requests to correct microservice
- Handles authentication, logging, filtering

Example routing:

/api/users/**   → User Service /api/orders/**  → Order Service

So frontend never calls services directly.

---

### 3. Config Server

- Centralized configuration management
- Stores `.yml` configs for all services

Example:

user-service.yml order-service.yml

Benefits:
- Change config without rebuilding services
- Centralized control

---

### 4. User Service

Handles:
- User registration
- User data management
- Kafka event publishing (User created events)

---

### 5. Order Service

Handles:
- Order creation
- Order processing
- Communicates with User Service if needed

---

## 🔄 How Request Flow Works

Example: Get user data

Client → API Gateway → User Service → Database

Example: Create order

Client → API Gateway → Order Service → (calls User Service if needed)

---

## 🧠 Key Concepts Used

- Spring Boot
- Spring Cloud Netflix Eureka
- Spring Cloud Gateway
- Spring Config Server
- Kafka (event-driven communication)
- REST APIs

---

## 🚀 How to Run

1. Start Config Server
2. Start Discovery Server (Eureka)
3. Start API Gateway
4. Start User Service
5. Start Order Service

Order is IMPORTANT.

---

## 📌 Eureka Dashboard

Once running:

http://localhost:8761

You can see all services registered there.

---

## 📡 Future Improvements

- JWT Authentication
- Circuit Breaker (Resilience4j)
- Docker Compose deployment
- Kubernetes deployment
- Central logging (ELK stack)

---

## 👨‍💻 Author

Built for learning microservices architecture with Spring Boot.


