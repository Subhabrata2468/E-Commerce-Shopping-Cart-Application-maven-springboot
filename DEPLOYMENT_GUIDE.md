# 🚀 E-Commerce Application - Deployment Guide

## Issues Fixed

### 1. **500 Internal Server Errors**

- Added comprehensive error handling to all admin endpoints
- Fixed null pointer exceptions in user and order management
- Added try-catch blocks with proper error messages
- Created global exception handler for unhandled errors

### 2. **Docker Configuration Issues**

- Fixed Dockerfile with proper health checks
- Added Spring Boot Actuator for monitoring
- Improved docker-compose.yml with default values
- Added proper volume mounts and environment variables

### 3. **Environment Configuration**

- Created docker.env file with all required variables
- Added default values in application.properties
- Fixed missing environment variable handling
- Added comprehensive logging configuration

## Quick Start

### Option 1: Docker Deployment (Recommended)

1. **Copy environment file**:

   ```bash
   cp docker.env .env
   ```

2. **Update .env file** with your actual values:

   ```bash
   # Edit .env file with your configuration
   nano .env
   ```

3. **Start the application**:

   ```bash
   docker-compose up -d
   ```

4. **Check logs**:

   ```bash
   docker-compose logs -f app
   ```

5. **Access the application**:
   - Main application: http://localhost:8080
   - Admin panel: http://localhost:8080/admin
   - Health check: http://localhost:8080/actuator/health

### Option 2: Local Development

1. **Set up MySQL database**:

   ```sql
   CREATE DATABASE ecommerce_db;
   CREATE USER 'ecom_user'@'localhost' IDENTIFIED BY 'ecom_password123';
   GRANT ALL PRIVILEGES ON ecommerce_db.* TO 'ecom_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

2. **Update application.properties** for local development:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
   spring.datasource.username=ecom_user
   spring.datasource.password=ecom_password123
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

## Environment Variables

### Required Variables

```env
# Database Configuration
SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/ecommerce_db
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_DATASOURCE_USERNAME=ecom_user
SPRING_DATASOURCE_PASSWORD=ecom_password123
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
MYSQL_ROOT_PASSWORD=root_password123
MYSQL_DATABASE=ecommerce_db
MYSQL_USER=ecom_user
MYSQL_PASSWORD=ecom_password123
```

## Troubleshooting

### Common Issues

1. **500 Internal Server Error**:

   - Check application logs: `docker-compose logs -f app`
   - Verify database connection
   - Check environment variables

2. **Database Connection Issues**:

   - Ensure MySQL container is running: `docker-compose ps`
   - Check database credentials in .env file
   - Verify database exists and user has permissions

3. **File Upload Issues**:

   - Check upload directory permissions
   - Verify file size limits in configuration
   - Ensure upload directories exist

4. **Memory Issues**:
   - Increase Docker memory allocation
   - Check JVM heap size settings

### Debug Commands

```bash
# Check container status
docker-compose ps

# View application logs
docker-compose logs -f app

# View database logs
docker-compose logs -f mysql

# Restart services
docker-compose restart

# Rebuild and restart
docker-compose up --build -d

# Stop all services
docker-compose down

# Remove volumes (⚠️ This will delete all data)
docker-compose down -v
```

## Health Checks

The application includes health checks for monitoring:

- **Health Endpoint**: http://localhost:8080/actuator/health
- **Info Endpoint**: http://localhost:8080/actuator/info

## Security Notes

1. **Change default passwords** in production
2. **Use strong database passwords**
3. **Configure proper mail settings** for password reset
4. **Enable HTTPS** in production
5. **Regular security updates**

## Performance Optimization

1. **Database Indexing**: Ensure proper indexes on frequently queried columns
2. **Connection Pooling**: Configure HikariCP settings
3. **Caching**: Consider adding Redis for session management
4. **CDN**: Use CDN for static assets in production

## Monitoring

The application includes:

- Spring Boot Actuator endpoints
- Health checks
- Detailed logging
- Error tracking

## Support

If you encounter issues:

1. Check the logs first
2. Verify environment configuration
3. Test database connectivity
4. Check Docker container status
5. Review error messages in the application

## Default Credentials

- **Admin Email**: admin@ecom.com
- **Admin Password**: admin123

**⚠️ Important**: Change these credentials before deploying to production!
