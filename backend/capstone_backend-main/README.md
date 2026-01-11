#🏦 FinalProject – Corporate Banking Credit Management System
📌 Project Overview

FinalProject is a full-stack corporate banking application designed to manage clients, users, and credit requests with role-based access control.
The system supports Admin, Relationship Manager (RM), and Analyst roles and follows enterprise-grade architecture, security, testing, and containerization standards.

###🧱 Architecture
Frontend (Angular)
        |
        |  REST API (JWT Secured)
        v
Backend (Spring Boot)
        |
        v
MongoDB

###🛠️ Tech Stack
🔹 Backend

1) Java 17

2) Spring Boot 3.5.x

3) Spring Security + JWT

4) Spring Data MongoDB

5) Validation (Jakarta Validation)

6) JUnit 5 + Mockito

7) JaCoCo (Code Coverage)

8) SonarQube (Static Analysis)

🔹 Frontend

1) Angular (Production Build)

2) Role-based Dashboards

3) Reactive Forms + Validation

4) Nginx (for production serving)

🔹 DevOps / Tools

1) Docker & Docker Compose

2) Postman

3) MongoDB Compass

4) SonarQube

5) JaCoCo

##👥 User Roles
🛡️ Admin

1) Create users

2) Assign roles (ADMIN / RM / ANALYST)

3) Activate / Deactivate users

4) View all users

##👔 Relationship Manager (RM)

1) Create and manage clients

2) Submit credit requests

3) View request status

4) Dashboard summary

##📊 Analyst

1) View all credit requests

2) Approve / Reject requests

3) Dashboard analytics

##🔐 Security

JWT-based authentication

Role-based authorization

Password encryption using BCrypt

Stateless session management

##📁 Backend Package Structure
com.example.demo
├── config
├── controller
├── dto
├── exception
├── model
├── repository
├── service
│   └── impl
└── FinalProjectApplication

##🧪 Testing Strategy
###✅ Implemented

Service layer unit tests (JUnit + Mockito)

Exception handling tests

Security-safe unit testing

Frontend unit tests (Angular)

###📊 Code Coverage

JaCoCo integrated

Coverage report generated in:

target/site/jacoco/index.html

🎯 Coverage Targets (As per PDF)

Service Layer: 95%



##📈 SonarQube Integration
Steps Followed

1) SonarQube server running on http://localhost:9000

2) Token generated from SonarQube UI

3) Analysis executed using:

mvn clean verify sonar:sonar -Dsonar.token=YOUR_TOKEN

Result

Code smells identified

Coverage reported

Maintainability & security analyzed
## 🔍 Code Quality & Test Coverage (SonarQube)

This project uses **SonarQube** for static code analysis and quality gate enforcement to ensure high code quality and maintainability.

### ✔ Key Highlights
- Static code analysis using SonarQube Community Edition
- Unit test coverage measured using **JaCoCo**
- Quality Gate configured with:
  - Code coverage thresholds
  - Reliability and maintainability checks
  - Security hotspot review
- Non-business layers (DTOs, configs, repositories, test files) excluded from coverage evaluation

### 📊 Quality Metrics
- Service layer test coverage: **80%+**
- Bugs: **0**
- Vulnerabilities: **0**
- Code Duplication: **0%**
- Quality Gate Status: **PASSED**

### 🔗 SonarQube Dashboard
👉 [View SonarQube Analysis](http://localhost:9000/dashboard?id=finalproject-backend)

> SonarQube was used to continuously monitor code quality and ensure production-ready standards.


##🐳 Dockerization
###Backend

Spring Boot container

Exposes port 8080

## 🐳 Dockerization & Containerization

The backend application is fully containerized using **Docker**, enabling consistent builds and easy deployment across environments.

### ✔ Docker Features
- Java 21-based lightweight Docker image
- Spring Boot application packaged as a JAR
- Application runs on port **8080**
- Image published to Docker Hub for easy access

### 📦 Docker Image
👉 **Docker Hub Repository:**  
https://hub.docker.com/r/pravallika761/finalproject-backend

### ▶️ Run Using Docker
```bash
docker pull pravallika761/finalproject-backend:1.0
docker run -p 8080:8080 pravallika761/finalproject-backend:1.0


###Frontend

Angular production build

Served via Nginx

Exposes port 80

###MongoDB

Official MongoDB image

Exposes port 27017

##▶️ Running with Docker Compose
docker compose up -d

Services
Service	Port
Backend	8080
Frontend	80
MongoDB	27017
SonarQube	9000
##🔗 API Sample
Login
POST /api/auth/login

{
  "username": "admin1",
  "password": "admin123"
}
🌐 Frontend Dockerization & Docker Hub

This frontend application is containerized using Docker and published to Docker Hub for easy deployment and portability.

🧰 Technologies Used

Angular

Docker

Nginx

Docker Hub

🐳 Dockerizing the Frontend

The Angular frontend is built using a multi-stage Docker build:

Build stage – Angular application is compiled using Node.js

Runtime stage – Compiled static files are served using Nginx

This approach results in a smaller, faster, and production-ready Docker image.

#🧾 Frontend Dockerfile
# Build stage
FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Runtime stage
FROM nginx:alpine
COPY --from=build /app/dist/ /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

📦 Docker Hub Image

The frontend Docker image is pushed to Docker Hub.

🔗 Docker Hub Repository:

https://hub.docker.com/r/pravallika761/finalproject-frontend


👉 Replace <your-dockerhub-username> with your Docker Hub username.

▶️ Run Frontend Using Docker

Pull the image from Docker Hub:

docker pull pravallika761/finalproject-frontend:latest


Run the container:

docker run -p 80:80 pravallika761/finalproject-frontend:latest


Access the application in the browser:

http://localhost:8080

🔗 Frontend & Backend Communication

The frontend communicates with the backend REST APIs using HTTP.

Example backend URL:

http://localhost:8080/api


Backend URLs can be configured using Angular environment files for Docker-based deployment.

#📌 Key Highlights

✔ Layered Architecture (Controller → Service → Repository)
✔ JWT Security
✔ Role-based dashboards
✔ Dockerized backend & frontend
✔ JaCoCo + SonarQube integrated
✔ Production-ready Angular build



👤 Author

Pravallika Chitturi
Java Full Stack Developer