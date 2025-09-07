# 🛒 E-Commerce Shopping Cart Application

A comprehensive full-stack e-commerce application built with Spring Boot 3.2.4, featuring user authentication, product management, shopping cart functionality, order processing, and a complete admin dashboard. This application is production-ready with Docker support and environment-based configuration.

## 📋 Table of Contents

- [Features](#-features)
- [Technology Stack](#-technology-stack)
- [Project Structure](#-project-structure)
- [Prerequisites](#-prerequisites)
- [Quick Start](#-quick-start)
- [Configuration](#-configuration)
- [Usage](#-usage)
- [API Endpoints](#-api-endpoints)
- [Database Schema](#-database-schema)
- [Docker Deployment](#-docker-deployment)
- [File Upload System](#-file-upload-system)
- [Security Features](#-security-features)
- [Development](#-development)
- [Troubleshooting](#-troubleshooting)
- [Contributing](#-contributing)
- [License](#-license)

## ✨ Features

### 🛍️ Customer Features

- **User Registration & Authentication**: Secure user registration with email verification
- **Product Browsing**: Browse products by category with search functionality
- **Shopping Cart**: Add/remove items, update quantities, view cart total
- **Order Management**: Place orders, track order status, view order history
- **Profile Management**: Update profile information and change password
- **Password Recovery**: Forgot password functionality with email reset
- **Responsive Design**: Mobile-friendly interface using Bootstrap 5.3.3

### 👨‍💼 Admin Features

- **Product Management**: Add, edit, delete products with image upload
- **Category Management**: Create and manage product categories
- **Order Management**: View all orders, update order status
- **User Management**: View and manage user accounts
- **Admin Dashboard**: Comprehensive dashboard with statistics
- **Admin Creation**: Add new admin users
- **File Upload Management**: Handle product, category, and profile images

### 🔒 Security Features

- **Spring Security**: Role-based authentication and authorization
- **Password Encryption**: BCrypt password hashing
- **Account Locking**: Automatic account locking after failed login attempts
- **Session Management**: Secure session handling
- **CSRF Protection**: Cross-site request forgery protection

## 🛠️ Technology Stack

### Backend

- **Java 17**: Latest LTS version
- **Spring Boot 3.2.4**: Main framework
- **Spring Security 6.x**: Authentication and authorization
- **Spring Data JPA**: Database operations with Hibernate
- **Spring Mail**: Email functionality for password reset
- **Hibernate**: ORM framework
- **MySQL 8.0**: Primary database
- **H2 Database**: In-memory database for testing
- **Thymeleaf**: Server-side templating engine
- **Lombok 1.18.38**: Reduces boilerplate code

### Frontend

- **Bootstrap 5.3.3**: CSS framework for responsive design
- **Font Awesome 6.5.1**: Icon library
- **JavaScript**: Client-side functionality
- **jQuery**: DOM manipulation and validation
- **jQuery Validation**: Form validation

### DevOps & Deployment

- **Docker**: Containerization
- **Docker Compose 3.9**: Multi-container orchestration
- **Maven**: Build tool and dependency management
- **Spring Boot DevTools**: Hot reloading for development

## 📁 Project Structure

```
E-Commerce-Shopping-Cart-Application-maven-springboot/
├── src/
│   ├── main/
│   │   ├── java/com/ecom/
│   │   │   ├── config/              # Configuration classes
│   │   │   │   ├── AdminInitializer.java      # Auto-creates admin user
│   │   │   │   ├── AuthFailureHandlerImpl.java
│   │   │   │   ├── AuthSucessHandlerImpl.java
│   │   │   │   ├── CustomUser.java
│   │   │   │   ├── FileUploadConfig.java
│   │   │   │   ├── SecurityConfig.java
│   │   │   │   └── UserDetailsServiceImpl.java
│   │   │   ├── controller/          # MVC controllers
│   │   │   │   ├── AdminController.java
│   │   │   │   ├── HomeController.java
│   │   │   │   └── UserController.java
│   │   │   ├── model/               # Entity classes
│   │   │   │   ├── Cart.java
│   │   │   │   ├── Category.java
│   │   │   │   ├── OrderAddress.java
│   │   │   │   ├── OrderRequest.java
│   │   │   │   ├── Product.java
│   │   │   │   ├── ProductOrder.java
│   │   │   │   └── UserDtls.java
│   │   │   ├── repository/          # Data access layer
│   │   │   │   ├── CartRepository.java
│   │   │   │   ├── CategoryRepository.java
│   │   │   │   ├── ProductOrderRepository.java
│   │   │   │   ├── ProductRepository.java
│   │   │   │   └── UserRepository.java
│   │   │   ├── service/             # Business logic
│   │   │   │   ├── impl/            # Service implementations
│   │   │   │   │   ├── CartServiceImpl.java
│   │   │   │   │   ├── CategoryServiceImpl.java
│   │   │   │   │   ├── OrderServiceImpl.java
│   │   │   │   │   ├── ProductServiceImpl.java
│   │   │   │   │   └── UserServiceImpl.java
│   │   │   │   ├── CartService.java
│   │   │   │   ├── CategoryService.java
│   │   │   │   ├── CommonService.java
│   │   │   │   ├── OrderService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   └── UserService.java
│   │   │   ├── util/                # Utility classes
│   │   │   │   ├── AppConstant.java
│   │   │   │   ├── CommonUtil.java
│   │   │   │   ├── FileUploadUtil.java
│   │   │   │   ├── ImagePathUtil.java
│   │   │   │   └── OrderStatus.java
│   │   │   └── ShoppingCartApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── static/              # Static resources
│   │       │   ├── css/
│   │       │   │   └── style.css
│   │       │   ├── js/
│   │       │   │   └── script.js
│   │       │   └── img/             # Images
│   │       │       ├── category_img/
│   │       │       ├── product_img/
│   │       │       └── profile_img/
│   │       └── templates/           # Thymeleaf templates
│   │           ├── admin/           # Admin templates
│   │           ├── user/            # User templates
│   │           └── *.html           # Common templates
│   └── test/                        # Test files
├── uploads/                         # File upload directory
│   ├── profile_img/
│   ├── product_img/
│   └── category_img/
├── docker-compose.yml               # Docker orchestration
├── Dockerfile                       # Application container
├── pom.xml                         # Maven dependencies
├── application.properties          # Configuration
├── create-uploads-dirs.sh          # Linux/Mac setup script
└── create-uploads-dirs.bat         # Windows setup script
```

## ⚙️ Prerequisites

Before running this application, ensure you have:

- **Java 17** or higher
- **Maven 3.6+**
- **MySQL 8.0** or higher (for production)
- **Docker** and **Docker Compose** (for containerized deployment)

## 🚀 Quick Start

### Option 1: Docker Deployment (Recommended)

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd E-Commerce-Shopping-Cart-Application-maven-springboot
   ```

2. **Create environment file**

   ```bash
   # Create .env file with your configuration
   cat > .env << EOF
   # Database Configuration
   SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/ecommerce_db
   SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
   SPRING_DATASOURCE_USERNAME=ecom_user
   SPRING_DATASOURCE_PASSWORD=your_secure_password
   SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT=org.hibernate.dialect.MySQLDialect
   SPRING_JPA_HIBERNATE_DDL_AUTO=update

   # Mail Configuration
   SPRING_MAIL_HOST=smtp.gmail.com
   SPRING_MAIL_USERNAME=your_email@gmail.com
   SPRING_MAIL_PASSWORD=your_app_password
   SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
   SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true

   # File Upload Configuration
   SPRING_SERVLET_MULTIPART_MAX_FILE_SIZE=50MB
   SPRING_SERVLET_MULTIPART_MAX_REQUEST_SIZE=50MB

   # Admin Configuration
   ADMIN_EMAIL=admin@ecom.com
   ADMIN_PASSWORD=admin123
   ADMIN_NAME=Admin User
   ADMIN_MOBILE=1234567890
   ADMIN_ADDRESS=Admin Address
   ADMIN_CITY=Admin City
   ADMIN_STATE=Admin State
   ADMIN_PINCODE=123456

   # MySQL Configuration
   MYSQL_ROOT_PASSWORD=your_mysql_root_password
   MYSQL_DATABASE=ecommerce_db
   MYSQL_USER=ecom_user
   MYSQL_PASSWORD=your_secure_password
   EOF
   ```

3. **Start the application**

   ```bash
   docker-compose up -d
   ```

4. **Access the application**
   - Main application: http://localhost:8080
   - Admin panel: http://localhost:8080/admin

### Option 2: Local Development

1. **Clone and setup**

   ```bash
   git clone <repository-url>
   cd E-Commerce-Shopping-Cart-Application-maven-springboot
   ```

2. **Create uploads directories**

   ```bash
   # Linux/Mac
   ./create-uploads-dirs.sh

   # Windows
   create-uploads-dirs.bat
   ```

3. **Configure application.properties**

   ```properties
   # Database Configuration
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
   spring.jpa.hibernate.ddl-auto=update

   # Mail Configuration
   spring.mail.host=smtp.gmail.com
   spring.mail.username=your_email@gmail.com
   spring.mail.password=your_app_password
   spring.mail.port=587
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true

   # File Upload Configuration
   spring.servlet.multipart.max-file-size=50MB
   spring.servlet.multipart.max-request-size=50MB

   # Admin Configuration
   admin.email=admin@ecom.com
   admin.password=admin123
   admin.name=Admin User
   admin.mobile=1234567890
   admin.address=Admin Address
   admin.city=Admin City
   admin.state=Admin State
   admin.pincode=123456
   ```

4. **Set up MySQL database**

   ```sql
   CREATE DATABASE ecommerce_db;
   CREATE USER 'ecom_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON ecommerce_db.* TO 'ecom_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

5. **Build and run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

## ⚙️ Configuration

### Environment Variables

The application uses environment-based configuration. All configuration values are externalized and can be set via:

1. **Environment variables** (recommended for production)
2. **`.env` file** (for Docker deployment)
3. **application.properties** (for local development)

### Key Configuration Areas

#### Database Configuration

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

#### Mail Configuration

```properties
spring.mail.host=smtp.gmail.com
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
```

#### File Upload Configuration

```properties
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
```

#### Admin User Configuration

```properties
admin.email=admin@ecom.com
admin.password=admin123
admin.name=Admin User
```

## 🎯 Usage

### Default Admin Credentials

The application automatically creates a default admin user on first startup:

- **Email**: `admin@ecom.com`
- **Password**: `admin123`

**⚠️ Important**: Change these credentials in your configuration before first startup for security purposes.

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

# Remove volumes (⚠️ This will delete all data)
docker-compose down -v
```

### Docker Services

- **MySQL 8.0**: Database service with persistent storage
- **Spring Boot App**: Application service with file upload support

### Volume Mounts

- `./uploads:/app/uploads` - File upload persistence
- `mysql_data:/var/lib/mysql` - Database persistence
- `./application.properties:/app/config/application.properties` - Configuration

### Health Checks

The MySQL service includes health checks to ensure the database is ready before the application starts.

## 📁 File Upload System

The application includes a robust file upload system that works in both local and Docker environments:

### Upload Directories

```
uploads/
├── profile_img/     # User profile images
├── product_img/     # Product images
└── category_img/    # Category images
```

### Supported File Types

- **Images**: JPG, PNG, JFIF
- **Max File Size**: 50MB
- **Max Request Size**: 50MB

### File Storage

- **Local Development**: Files stored in `./uploads/` directory
- **Docker Deployment**: Files stored in `/app/uploads/` with volume persistence

## 🔒 Security Features

### Authentication & Authorization

- **Role-based Access Control**: USER and ADMIN roles
- **Password Encryption**: BCrypt hashing
- **Account Locking**: Automatic lockout after failed attempts
- **Session Management**: Secure session handling

### Security Configuration

- **CSRF Protection**: Enabled by default
- **CORS Configuration**: Configurable cross-origin settings
- **Password Reset**: Secure token-based password reset

## 🔧 Development

### Running Tests

```bash
mvn test
```

### Code Style

The project uses Lombok to reduce boilerplate code and follows Spring Boot conventions.

### Hot Reload

The application includes Spring Boot DevTools for automatic restart during development.

### Database Console (H2)

When using H2 database, access the console at:

- **URL**: http://localhost:8080/h2-console
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (leave empty)

## 🆘 Troubleshooting

### Common Issues

1. **Database Connection Issues**

   - Ensure MySQL is running and accessible
   - Check database credentials in configuration
   - Verify database exists and user has proper permissions

2. **File Upload Issues**

   - Ensure upload directories exist and have proper permissions
   - Check file size limits in configuration
   - Verify file types are supported

3. **Email Configuration Issues**

   - Verify SMTP settings are correct
   - Check if 2-factor authentication is enabled for Gmail
   - Use app-specific passwords for Gmail

4. **Docker Issues**
   - Ensure Docker and Docker Compose are installed
   - Check if ports 8080 and 3306 are available
   - Verify .env file exists and has correct values

### Logs

```bash
# View application logs
docker-compose logs -f app

# View database logs
docker-compose logs -f mysql
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🔮 Future Enhancements

- [ ] Payment gateway integration (Stripe, PayPal)
- [ ] Advanced inventory management system
- [ ] Product reviews and ratings
- [ ] Advanced search and filtering
- [ ] Wishlist functionality
- [ ] Email notifications for orders
- [ ] Analytics dashboard
- [ ] Multi-language support
- [ ] Mobile app (React Native/Flutter)
- [ ] API documentation (Swagger/OpenAPI)
- [ ] Redis caching for better performance
- [ ] Elasticsearch for advanced search
- [ ] Microservices architecture

## 📊 Project Statistics

- **Total Files**: 50+ Java files, 20+ HTML templates
- **Dependencies**: 10+ Spring Boot starters
- **Database Tables**: 7 main entities
- **API Endpoints**: 20+ REST endpoints
- **Frontend Components**: Bootstrap-based responsive design
- **Spring Boot Version**: 3.2.4
- **Java Version**: 17
- **Lombok Version**: 1.18.38

---

**Built with ❤️ using Spring Boot 3.2.4**

## 👨‍💻 Author

Developed by **[SUBHABRATA PANDA]**

📧 Contact: [panda.subhabrata2003@gmail.com](mailto:panda.subhabrata2003@gmail.com)

---

_This README provides comprehensive documentation for the E-Commerce Shopping Cart Application. For specific implementation details, refer to the source code and inline comments._
