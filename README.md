# 🛒 E-Commerce Shopping Cart Application (maven, springboot)

A full-featured online shopping cart application built with Spring Boot, featuring user authentication, product management, order processing, and admin dashboard.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [API Endpoints](#api-endpoints)
- [Database Schema](#database-schema)
- [Docker Deployment](#docker-deployment)
- [Contributing](#contributing)
- [License](#license)

## ✨ Features

### 🛍️ Customer Features

- **User Registration & Authentication**: Secure user registration with email verification
- **Product Browsing**: Browse products by category with search functionality
- **Shopping Cart**: Add/remove items, update quantities, view cart total
- **Order Management**: Place orders, track order status, view order history
- **Profile Management**: Update profile information and change password
- **Password Recovery**: Forgot password functionality with email reset
- **Responsive Design**: Mobile-friendly interface using Bootstrap

### 👨‍💼 Admin Features

- **Product Management**: Add, edit, delete products with image upload
- **Category Management**: Create and manage product categories
- **Order Management**: View all orders, update order status
- **User Management**: View and manage user accounts
- **Admin Dashboard**: Comprehensive dashboard with statistics
- **Admin Creation**: Add new admin users

### 🔒 Security Features

- **Spring Security**: Role-based authentication and authorization
- **Password Encryption**: BCrypt password hashing
- **Account Locking**: Automatic account locking after failed login attempts
- **Session Management**: Secure session handling

## 🛠️ Technology Stack

### Backend

- **Java 17**: Latest LTS version
- **Spring Boot 3.2.4**: Main framework
- **Spring Security**: Authentication and authorization
- **Spring Data JPA**: Database operations
- **Hibernate**: ORM framework
- **MySQL 8.0**: Database
- **Thymeleaf**: Server-side templating
- **Lombok**: Reduces boilerplate code

### Frontend

- **Bootstrap 5.3.3**: CSS framework
- **Font Awesome 6.5.1**: Icons
- **JavaScript**: Client-side functionality

### DevOps

- **Docker**: Containerization
- **Docker Compose**: Multi-container orchestration
- **Maven**: Build tool
- **AWS EC2**: Cloud infrastructure for deployment
- **Ansible**: Automation tool for configuration management
- **Jenkins**: CI/CD pipeline setup

## 📁 Project Structure

```
shopping-cart-spring-boot-main/
├── src/
│   ├── main/
│   │   ├── java/com/ecom/
│   │   │   ├── config/          # Security and configuration
│   │   │   ├── controller/      # MVC controllers
│   │   │   ├── model/           # Entity classes
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── service/         # Business logic
│   │   │   └── util/            # Utility classes
│   │   └── resources/
│   │       ├── static/          # CSS, JS, images
│   │       └── templates/       # Thymeleaf templates
│   └── test/                    # Test files
├── docker-compose.yml           # Docker orchestration
├── Dockerfile                   # Application container
├── pom.xml                     # Maven dependencies
└── application.properties      # Configuration
```

## ⚙️ Prerequisites

Before running this application, ensure you have:

- **Java 17** or higher
- **MySQL 8.0** or higher
- **Maven 3.6+**
- **Docker** and **Docker Compose** (for containerized deployment)

## 🚀 Installation

### Option 1: Local Development

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd shopping-cart-spring-boot-main
   ```

2. **Set up MySQL database**

   ```sql
   CREATE DATABASE ecommerce_db;
   CREATE USER 'ecom_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON ecommerce_db.* TO 'ecom_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Configure application.properties**

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=ecom_user
   spring.datasource.password=your_password

   # Email configuration (for password reset)
   spring.mail.host=smtp.gmail.com
   spring.mail.username=your_email@gmail.com
   spring.mail.password=your_app_password
   spring.mail.port=587
   ```

4. **Build and run**

   ```bash
   mvn clean install
   mvn clean package
   mvn spring-boot:run
   ```

5. **Access the application**
   - Main application: http://localhost:8080
   - Admin panel: http://localhost:8080/admin (after admin login)

### Option 2: Docker Deployment

1. **Create environment file**

   ```bash
   cp .env.example .env
   # Edit .env with your configuration
   ```

2. **Run with Docker Compose**

   ```bash
   docker-compose up -d
   ```

3. **Access the application**
   - Main application: http://localhost:8080
   - Admin panel: http://localhost:8080/admin

## ⚙️ Configuration

### Database Configuration

The application uses MySQL with the following default settings:

- **Database**: `ecommerce_db`
- **Username**: `root`
- **Password**: `password`
- **Port**: `3306`

### Email Configuration

For password reset functionality, configure your email settings:

```properties
spring.mail.host=smtp.gmail.com
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
spring.mail.port=587
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

### File Upload Configuration

```properties
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
```

## 🎯 Usage

### Customer Workflow

1. **Register/Login**: Create an account or sign in
2. **Browse Products**: View products by category or search
3. **Add to Cart**: Add products to shopping cart
4. **Checkout**: Review cart and place order
5. **Track Orders**: View order history and status

### Admin Workflow

1. **Login**: Access admin panel with admin credentials
2. **Manage Categories**: Create and manage product categories
3. **Manage Products**: Add, edit, and delete products
4. **Process Orders**: Update order status and manage deliveries
5. **User Management**: Monitor and manage user accounts

## 🔌 API Endpoints

### Public Endpoints

- `GET /` - Home page
- `GET /signin` - Login page
- `GET /register` - Registration page
- `GET /products` - Product listing
- `GET /product/{id}` - Product details
- `GET /forgot-password` - Password recovery

### User Endpoints (ROLE_USER)

- `GET /user/` - User dashboard
- `GET /user/cart` - Shopping cart
- `GET /user/orders` - Order placement
- `GET /user/user-orders` - Order history
- `GET /user/profile` - User profile

### Admin Endpoints (ROLE_ADMIN)

- `GET /admin/` - Admin dashboard
- `GET /admin/category` - Category management
- `GET /admin/products` - Product management
- `GET /admin/orders` - Order management
- `GET /admin/users` - User management

## 🗄️ Database Schema

### Core Entities

- **UserDtls**: User information and authentication
- **Product**: Product details with pricing and inventory
- **Category**: Product categories
- **Cart**: Shopping cart items
- **ProductOrder**: Order information and status
- **OrderAddress**: Shipping address details

### Key Relationships

- User → Cart (One-to-Many)
- User → ProductOrder (One-to-Many)
- Product → Cart (One-to-Many)
- Product → ProductOrder (One-to-Many)
- Category → Product (One-to-Many)

## 🐳 Docker Deployment

### Using Docker Compose

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Remove volumes
docker-compose down -v
```

### Environment Variables

Create a `.env` file with:

```env
MYSQL_ROOT_PASSWORD=your_mysql_password
MYSQL_DATABASE=ecommerce_db
MYSQL_USER=ecom_user
MYSQL_PASSWORD=your_mysql_password
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/ecommerce_db
SPRING_DATASOURCE_USERNAME=ecom_user
SPRING_DATASOURCE_PASSWORD=your_mysql_password
SPRING_MAIL_USERNAME=your_email@gmail.com
SPRING_MAIL_PASSWORD=your_app_password
SPRING_MAIL_PORT=587
```

## 🔧 Development

### Running Tests

```bash
mvn test
```

### Code Style

The project uses Lombok to reduce boilerplate code and follows Spring Boot conventions.

### Hot Reload

The application includes Spring Boot DevTools for automatic restart during development.

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🆘 Support

If you encounter any issues or have questions:

1. Check the [Issues](../../issues) page
2. Review the configuration settings
3. Ensure all prerequisites are met
4. Check the application logs for error details

## 🔮 Future Enhancements

- [ ] Payment gateway integration
- [ ] Inventory management system
- [ ] Advanced search and filtering
- [ ] Product reviews and ratings
- [ ] Wishlist functionality
- [ ] Email notifications
- [ ] Analytics dashboard
- [ ] Multi-language support

---

**Built with ❤️ using Spring Boot**

## 👨‍💻 Author

Developed by **\[SUBHABRATA PANDA]**

📧 Contact: [panda.subhabrata2003@gmail.com](mailto:panda.subhabrata2003@gmail.com)
