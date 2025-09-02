#!/bin/bash

# Create uploads directories for local development
mkdir -p uploads/profile_img
mkdir -p uploads/product_img
mkdir -p uploads/category_img

# Set permissions
chmod -R 755 uploads

echo "Uploads directories created successfully!"
echo "Directories created:"
echo "  - uploads/profile_img"
echo "  - uploads/product_img"
echo "  - uploads/category_img"
