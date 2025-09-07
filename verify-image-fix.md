# Image Display Fix - Verification Guide

## Problem Fixed

The issue was caused by **inconsistent image path handling** in Thymeleaf templates. Some templates were using hardcoded paths like `/img/product_img/` instead of the proper `ImagePathUtil` service.

## Changes Made

Fixed the following templates to use the correct `ImagePathUtil` service:

1. **admin/products.html** - Product list view
2. **admin/edit_product.html** - Product edit form
3. **admin/edit_category.html** - Category edit form
4. **admin/category.html** - Category list view

## How to Test the Fix

### 1. Restart Your Application

```bash
# Stop containers
docker-compose down

# Rebuild and start
docker-compose up --build
```

### 2. Verify Image Display

- Login to admin panel
- Check product list: `/admin/products`
- Check category list: `/admin/category`
- Edit any product or category to see if images display correctly

### 3. Test Upload Functionality

- Upload a new product image
- Upload a new category image
- Verify images display immediately after upload

## Why This Fixes the Issue

### Before (Broken)

```html
<!-- Hardcoded path - looks in classpath static resources -->
<img th:src="@{'/img/product_img/'+${p.image}}" />
```

### After (Fixed)

```html
<!-- Uses ImagePathUtil service - correctly routes to uploaded files -->
<img
  th:with="imgUrl=${@imagePathUtil.getProductImagePath(p.image)}"
  th:src="@{${imgUrl}}"
/>
```

## How ImagePathUtil Works

The `ImagePathUtil` service:

1. Checks if image name is "default.jpg" → uses classpath static resources
2. For all other images → uses `/uploads/` directory (mounted volume)
3. This ensures uploaded images persist across container restarts

## Docker Volume Mount

Your `docker-compose.yml` correctly mounts:

```yaml
volumes:
  - ./uploads:/app/uploads # This persists uploaded images
```

## Additional Recommendations

### 1. Backup Your Uploads

```bash
# Create backup of uploaded images
tar -czf uploads-backup-$(date +%Y%m%d).tar.gz uploads/
```

### 2. Monitor Upload Directory

```bash
# Check if uploads directory exists and has proper permissions
ls -la uploads/
ls -la uploads/product_img/
ls -la uploads/category_img/
ls -la uploads/profile_img/
```

### 3. Environment Variables

Ensure your `.env` file has:

```env
UPLOAD_DIR=/app/uploads
```

## Troubleshooting

If images still don't display:

1. **Check container logs**:

   ```bash
   docker-compose logs app
   ```

2. **Verify file permissions**:

   ```bash
   docker exec -it shopping-cart-app ls -la /app/uploads/
   ```

3. **Test direct image access**:

   - Visit: `http://localhost:8080/uploads/product_img/[filename]`
   - Should return the image directly

4. **Check database**:
   - Verify image filenames are stored correctly in database
   - Image names should be timestamps (e.g., `1757226285592.jpg`)

## Prevention

- Always use `ImagePathUtil` service for dynamic images
- Only use hardcoded `/img/` paths for static assets in `src/main/resources/static/img/`
- Test image upload/display after any template changes
