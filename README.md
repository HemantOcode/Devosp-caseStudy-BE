# Spring Boot Microservices with React Frontend

A complete microservices architecture built with Spring Boot, featuring an API Gateway, User Service, Order Service, and a modern React frontend for managing users and orders.

## 🏗️ Architecture

```
┌─────────────────┐
│  React Frontend │
│   (Port 5173)   │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   API Gateway   │
│   (Port 8080)   │
└────────┬────────┘
         │
    ┌────┴────┐
    ▼         ▼
┌──────────┐ ┌──────────┐
│  User    │ │  Order   │
│ Service  │ │ Service  │
│ (8081)   │ │ (8082)   │
└──────────┘ └──────────┘
```

## 📦 Services

### Common Module

Shared library containing:

- Generic API response wrapper
- Custom exceptions
- Global exception handler

### User Service

Manages user data with CRUD operations:

- Create, Read, Update, Delete users
- Email validation
- H2 in-memory database

### Order Service

Manages orders with CRUD operations:

- Create, Read, Update, Delete orders
- Filter by user and status
- Order status tracking (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)
- H2 in-memory database

### API Gateway

Spring Cloud Gateway for routing:

- Routes requests to microservices
- CORS configuration for frontend
- Centralized entry point

### Frontend

Modern React application with:

- User management interface
- Order management interface
- Dark mode UI with gradients
- Responsive design
- Real-time CRUD operations

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker & Docker Compose
- Node.js 18+ (for frontend development)

### Running with Docker Compose

1. **Clone the repository**

   ```bash
   cd springboot-microservices
   ```

2. **Build and start all services**

   ```bash
   docker-compose up --build
   ```

3. **Access the services**
   - Frontend: http://localhost:5173
   - API Gateway: http://localhost:8080
   - User Service: http://localhost:8081
   - Order Service: http://localhost:8082

### Running Locally (Development)

#### Backend Services

1. **Build the common module**

   ```bash
   cd common
   mvn clean install
   cd ..
   ```

2. **Start User Service**

   ```bash
   cd user-service
   mvn spring-boot:run
   ```

3. **Start Order Service** (in a new terminal)

   ```bash
   cd order-service
   mvn spring-boot:run
   ```

4. **Start API Gateway** (in a new terminal)
   ```bash
   cd api-gateway
   mvn spring-boot:run
   ```

#### Frontend

```bash
cd frontend
npm install
npm run dev
```

## 📡 API Endpoints

### User Service (via Gateway: http://localhost:8080)

| Method | Endpoint      | Description     |
| ------ | ------------- | --------------- |
| GET    | `/users`      | Get all users   |
| GET    | `/users/{id}` | Get user by ID  |
| POST   | `/users`      | Create new user |
| PUT    | `/users/{id}` | Update user     |
| DELETE | `/users/{id}` | Delete user     |

**User Object:**

```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "phone": "1234567890"
}
```

### Order Service (via Gateway: http://localhost:8080)

| Method | Endpoint                  | Description          |
| ------ | ------------------------- | -------------------- |
| GET    | `/orders`                 | Get all orders       |
| GET    | `/orders/{id}`            | Get order by ID      |
| GET    | `/orders/user/{userId}`   | Get orders by user   |
| GET    | `/orders/status/{status}` | Get orders by status |
| POST   | `/orders`                 | Create new order     |
| PUT    | `/orders/{id}`            | Update order         |
| DELETE | `/orders/{id}`            | Delete order         |

**Order Object:**

```json
{
  "userId": 1,
  "productName": "Laptop",
  "quantity": 1,
  "price": 999.99,
  "status": "PENDING"
}
```

**Valid Statuses:** `PENDING`, `CONFIRMED`, `SHIPPED`, `DELIVERED`, `CANCELLED`

## 🧪 Testing the API

### Create a User

```bash
curl -X POST http://localhost:8080/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "1234567890"
  }'
```

### Get All Users

```bash
curl http://localhost:8080/users
```

### Create an Order

```bash
curl -X POST http://localhost:8080/orders \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "productName": "Laptop",
    "quantity": 1,
    "price": 999.99,
    "status": "PENDING"
  }'
```

### Get All Orders

```bash
curl http://localhost:8080/orders
```

## 🎨 Frontend Features

- **Modern Dark UI**: Sleek dark mode with vibrant gradients
- **Responsive Design**: Works on desktop and mobile
- **Real-time Updates**: Instant feedback on CRUD operations
- **Form Validation**: Client-side and server-side validation
- **Error Handling**: User-friendly error messages
- **Loading States**: Visual feedback during API calls
- **Modal Forms**: Clean interface for creating/editing records

## 🛠️ Technology Stack

### Backend

- **Spring Boot 3.2.0**
- **Spring Cloud Gateway**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Maven**

### Frontend

- **React 18**
- **Vite**
- **React Router**
- **Axios**
- **Modern CSS with CSS Variables**

### DevOps

- **Docker**
- **Docker Compose**

## 📁 Project Structure

```
springboot-microservices/
├── common/                    # Shared library
│   ├── src/main/java/com/example/common/
│   │   ├── exception/        # Custom exceptions
│   │   └── response/         # API response wrappers
│   └── pom.xml
├── user-service/             # User microservice
│   ├── src/main/java/com/example/user/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   └── entity/
│   ├── Dockerfile
│   └── pom.xml
├── order-service/            # Order microservice
│   ├── src/main/java/com/example/order/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   └── entity/
│   ├── Dockerfile
│   └── pom.xml
├── api-gateway/              # API Gateway
│   ├── src/main/java/com/example/gateway/
│   ├── Dockerfile
│   └── pom.xml
├── frontend/                 # React frontend
│   ├── src/
│   │   ├── components/
│   │   ├── services/
│   │   ├── App.jsx
│   │   └── main.jsx
│   └── package.json
├── docker-compose.yml
└── README.md
```

## 🔧 Configuration

### Database

Each service uses H2 in-memory database. To switch to PostgreSQL or MySQL:

1. Update `pom.xml` with the appropriate driver
2. Modify `application.yml` with database connection details
3. Update Docker Compose with database service

### Ports

- API Gateway: 8080
- User Service: 8081
- Order Service: 8082
- Frontend: 5173

To change ports, update the respective `application.yml` files and Docker Compose configuration.

## 📝 License

This project is open source and available under the MIT License.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📧 Support

For issues and questions, please open an issue in the repository.
