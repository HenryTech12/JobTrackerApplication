# 🧑‍💼 Job Tracker Microservices Application

This project is a **Job Tracker System** built with **Java Spring Boot** using a clean **Microservice Architecture**. It supports real-time interviewer status updates using **Apache Kafka**, and internal communication across services using **Feign Clients**.

---

## 📦 Microservices Overview

| Microservice         | Description                                                                 |
|----------------------|-----------------------------------------------------------------------------|
| **User Service**     | Manages user registration, authentication, and user profile data            |
| **Job Service**      | Handles job applications, interview scheduling, and interviewer updates     |
| **Notification Service** | Listens to Kafka events and sends real-time notifications to users     |

---

---

## 🛡️ Security

- 🔐 **JWT Authentication** implemented in the User Service.
- 🛡️ Tokens are issued at login and required for accessing protected endpoints.
- ✅ Tokens are **validated** across services via **Feign**.
---

## 🧰 Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Cloud OpenFeign
- Spring Data JPA + Hibernate
- Apache Kafka
- MySQL
- Eureka Discovery Server
- Spring Cloud Gateway
---

## 🔗 Communication

- **Synchronous (REST):** Uses Feign Clients between `Job Service` and `User Service`.
- **Asynchronous (Event-Driven):** Kafka is used to send interviewer updates from `Job Service` to `Notification Service`.

---

---

## 🚀 Features

- Microservice-based design
- Real-time notification via Kafka
- Clean REST APIs using Spring MVC
- Internal communication via Feign Client
- Scalable and easily deployable
- Extensible for authentication/authorization and more services

---

## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/job-tracker-microservices.git
cd job-tracker-microservices
