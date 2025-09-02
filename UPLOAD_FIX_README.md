# File Upload Fix for Docker Deployment

## Problem

The application was failing when running in Docker with the error:

```
java.io.FileNotFoundException: class path resource [static/img] cannot be resolved to absolute file path because it does not reside in the file system: jar:nested:/app/app.jar/!BOOT-INF/classes/!/static/img
```

This occurred because the code was trying to access static resources as regular files using `ClassPathResource("static/img").getFile()`, which doesn't work when running from a JAR file.

## Solution

The fix involves:

1. **Creating a dedicated uploads directory** that is mounted as a volume in Docker
2. **Replacing file operations** that try to access static resources with a new utility class
3. **Configuring Spring** to serve uploaded files as static resources

## Changes Made

### 1. New Utility Classes

- `FileUploadUtil.java` - Handles file uploads to the external uploads directory
- `ImagePathUtil.java` - Determines correct image paths for both static and uploaded images
- `FileUploadConfig.java` - Configures Spring to serve uploaded files

### 2. Updated Controllers and Services

- `HomeController.java` - Updated user registration file handling
- `AdminController.java` - Updated category, product, and admin file handling
- `UserServiceImpl.java` - Updated profile update file handling
- `ProductServiceImpl.java` - Updated product update file handling

### 3. Docker Configuration

- `docker-compose.yml` - Added volume mount for uploads directory
- `Dockerfile` - Creates uploads directory structure in container

### 4. Configuration

- `application.properties` - Added upload directory configuration

## Usage

### Local Development

1. Run the appropriate script to create uploads directories:

   - **Linux/Mac**: `./create-uploads-dirs.sh`
   - **Windows**: `create-uploads-dirs.bat`

2. The application will use `./uploads` directory locally

### Docker Deployment

1. The Docker container will automatically create the uploads directory structure
2. Files are persisted through the volume mount `./uploads:/app/uploads`
3. The application will use `/app/uploads` directory in the container

## File Structure

```
uploads/
├── profile_img/     # User profile images
├── product_img/     # Product images
└── category_img/    # Category images
```

## Image Paths

- **Static images** (logos, defaults): `/img/...`
- **Uploaded images**: `/uploads/...`

The `ImagePathUtil` class automatically determines the correct path based on whether the file exists in the uploads directory.

## Benefits

1. **Docker compatibility** - Works when running from JAR files
2. **File persistence** - Uploaded files survive container restarts
3. **Scalability** - Can easily mount to external storage in production
4. **Backward compatibility** - Still works in local development

## Testing

After deployment, test file uploads for:

- User registration
- Category creation/updates
- Product creation/updates
- Profile updates

All file operations should now work correctly in both local and Docker environments.
