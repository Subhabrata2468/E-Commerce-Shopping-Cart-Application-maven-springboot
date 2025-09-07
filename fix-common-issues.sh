#!/bin/bash

# E-Commerce Application - Common Issues Fix Script

echo "🔧 E-Commerce Application - Fixing Common Issues..."

# Create uploads directories
echo "📁 Creating uploads directories..."
mkdir -p uploads/profile_img uploads/product_img uploads/category_img
chmod -R 755 uploads/

# Create .env file if it doesn't exist
if [ ! -f .env ]; then
    echo "📝 Creating .env file from template..."
    cp docker.env .env
    echo "✅ .env file created. Please update with your actual values."
else
    echo "✅ .env file already exists."
fi

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

# Check if docker-compose is available
if ! command -v docker-compose &> /dev/null; then
    echo "❌ docker-compose is not installed. Please install docker-compose first."
    exit 1
fi

# Stop existing containers
echo "🛑 Stopping existing containers..."
docker-compose down

# Remove old images (optional)
read -p "🗑️  Do you want to remove old Docker images? (y/N): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    echo "🗑️  Removing old images..."
    docker-compose down --rmi all
fi

# Build and start containers
echo "🏗️  Building and starting containers..."
docker-compose up --build -d

# Wait for services to start
echo "⏳ Waiting for services to start..."
sleep 30

# Check container status
echo "📊 Checking container status..."
docker-compose ps

# Check application logs
echo "📋 Recent application logs:"
docker-compose logs --tail=20 app

# Check if application is responding
echo "🔍 Checking application health..."
if curl -f http://localhost:8080/actuator/health > /dev/null 2>&1; then
    echo "✅ Application is healthy!"
    echo "🌐 Access the application at: http://localhost:8080"
    echo "👨‍💼 Admin panel: http://localhost:8080/admin"
    echo "🔑 Default admin credentials: admin@ecom.com / admin123"
else
    echo "❌ Application is not responding. Check logs:"
    echo "docker-compose logs -f app"
fi

echo "🎉 Fix script completed!"
echo ""
echo "📚 For more help, see DEPLOYMENT_GUIDE.md"
echo "🐛 For troubleshooting, run: docker-compose logs -f app"
