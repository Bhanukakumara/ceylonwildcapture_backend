# User Module Testing Guide

This guide provides comprehensive testing instructions for the User Module with JWT authentication in the Ceylon Wild Capture backend.

## 📋 Table of Contents

1. [Prerequisites](#prerequisites)
2. [Authentication Endpoints](#authentication-endpoints)
3. [User Management Endpoints](#user-management-endpoints)
4. [Testing with Postman](#testing-with-postman)
5. [Testing with curl](#testing-with-curl)
6. [JWT Token Usage](#jwt-token-usage)
7. [Common Test Scenarios](#common-test-scenarios)
8. [Troubleshooting](#troubleshooting)

## 🚀 Prerequisites

### Required Software
- **Java 25+**
- **Maven 3.6+**
- **MySQL 8.0+**
- **Postman** or **curl** for API testing

### Environment Setup
1. Set up MySQL database
2. Configure `application.yml` with database credentials
3. Set JWT secret environment variable:
   ```bash
   export JWT_SECRET="your-secret-key-here"
   ```

### Running the Application
```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔐 Authentication Endpoints

### 1. User Login
**Endpoint:** `POST /api/auth/login`

**Request Body:**
```json
{
  "usernameOrEmail": "john_doe",
  "password": "password123",
  "rememberMe": false
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "PHOTOGRAPHER",
    "isActive": true,
    "emailVerified": true
  }
}
```

### 2. Refresh Token
**Endpoint:** `POST /api/auth/refresh`

**Request Body:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiJ9..."
}
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

### 3. Validate Credentials
**Endpoint:** `POST /api/auth/validate`

**Request Body:**
```json
{
  "usernameOrEmail": "john_doe",
  "password": "password123"
}
```

**Response:**
```json
{
  "valid": true
}
```

## 👥 User Management Endpoints

### 1. Register First Admin (No Authentication Required)
**Endpoint:** `POST /api/v1/users/register-first-admin` *(Only works when no admin users exist)*

**Request Body:**
```json
{
  "username": "admin",
  "email": "admin@example.com",
  "password": "Bhanuka@123",
  "firstName": "Admin",
  "lastName": "User",
  "phoneNumber": "+94771234567",
  "role": "ADMIN",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/admin-profile.jpg"
}
```

**Response:**
```json
{
  "id": 1,
  "username": "admin",
  "email": "admin@example.com",
  "firstName": "Admin",
  "lastName": "User",
  "phoneNumber": "+94771234567",
  "role": "ADMIN",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/admin-profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00",
  "lastLogin": null
}
```

**Important Notes:**
- This endpoint can only be used when no admin users exist in the database
- After creating the first admin, this endpoint will return an error
- The user will be automatically marked as active and email verified
- Use this endpoint for initial system setup only

### 2. Create User (Registration)
**Endpoint:** `POST /api/v1/users` *(Requires ADMIN role)*

**Request Body:**
```json
{
  "username": "new_user",
  "email": "newuser@example.com",
  "password": "password123",
  "firstName": "New",
  "lastName": "User",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": false,
  "profileImageUrl": "https://example.com/profile.jpg"
}
```

**Response:**
```json
{
  "id": 2,
  "username": "new_user",
  "email": "newuser@example.com",
  "firstName": "New",
  "lastName": "User",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": false,
  "profileImageUrl": "https://example.com/profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00",
  "lastLogin": null
}
```

### 2. Get User by ID
**Endpoint:** `GET /api/v1/users/{id}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00",
  "lastLogin": "2024-01-01T10:00:00"
}
```

### 3. Update User Profile
**Endpoint:** `PUT /api/v1/users/{id}`

**Request Body:**
```json
{
  "firstName": "John Updated",
  "lastName": "Doe Updated",
  "phoneNumber": "+94779876543",
  "email": "john.updated@example.com",
  "profileImageUrl": "https://example.com/new-profile.jpg",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true
}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john.updated@example.com",
  "firstName": "John Updated",
  "lastName": "Doe Updated",
  "phoneNumber": "+94779876543",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/new-profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:00:00",
  "lastLogin": "2024-01-01T10:00:00"
}
```

### 4. Update User Profile (Partial)
**Endpoint:** `PUT /api/v1/users/{id}/{
  "firstName": "John Updated",
  "lastName": "Doe Updated",
  "phoneNumber": "+94779876543",
  "email": "john.updated@example.com",
  "profileImageUrl": "https://example.com/new-profile.jpg",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true
}`

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "email": "john@example.com",
  "profileImageUrl": "https://example.com/profile.jpg"
}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:00:00",
  "lastLogin": "2024-01-01T10:00:00"
}
```

### 5. Update Password
**Endpoint:** `PUT /api/v1/users/{id}/password`

**Request Body:**
```json
{
  "currentPassword": "oldpassword123",
  "newPassword": "newpassword123",
  "confirmPassword": "newpassword123"
}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:00:00",
  "lastLogin": "2024-01-01T10:00:00"
}
```

### 6. Reset Password
**Endpoint:** `PUT /api/v1/users/reset-password`

**Request Body:**
```json
{
  "email": "john@example.com",
  "newPassword": "newpassword123",
  "confirmPassword": "newpassword123"
}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:00:00",
  "lastLogin": "2024-01-01T10:00:00"
}
```

### 7. Get All Users (Paginated)
**Endpoint:** `GET /api/v1/users?page=0&size=10&sort=createdAt,desc`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "phoneNumber": "+94771234567",
      "role": "PHOTOGRAPHER",
      "isActive": true,
      "emailVerified": true,
      "profileImageUrl": "https://example.com/profile.jpg",
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00",
      "lastLogin": "2024-01-01T10:00:00"
    }
  ],
  "pageable": {
    "page": 0,
    "size": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### 8. Get User by Email
**Endpoint:** `GET /api/v1/users/email/{email}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00",
  "lastLogin": "2024-01-01T10:00:00"
}
```

### 9. Get User by Username
**Endpoint:** `GET /api/v1/users/username/{username}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00",
  "lastLogin": "2024-01-01T10:00:00"
}
```

### 10. Search Users
**Endpoint:** `GET /api/v1/users/search?term=john&page=0&size=10`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "phoneNumber": "+94771234567",
      "role": "PHOTOGRAPHER",
      "isActive": true,
      "emailVerified": true,
      "profileImageUrl": "https://example.com/profile.jpg",
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00",
      "lastLogin": "2024-01-01T10:00:00"
    }
  ],
  "pageable": {
    "page": 0,
    "size": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### 11. Get Users by Role
**Endpoint:** `GET /api/v1/users/role/{role}?page=0&size=10`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "phoneNumber": "+94771234567",
      "role": "PHOTOGRAPHER",
      "isActive": true,
      "emailVerified": true,
      "profileImageUrl": "https://example.com/profile.jpg",
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00",
      "lastLogin": "2024-01-01T10:00:00"
    }
  ],
  "pageable": {
    "page": 0,
    "size": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### 12. Get Active Users
**Endpoint:** `GET /api/v1/users/active?isActive=true&page=0&size=10`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "phoneNumber": "+94771234567",
      "role": "PHOTOGRAPHER",
      "isActive": true,
      "emailVerified": true,
      "profileImageUrl": "https://example.com/profile.jpg",
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00",
      "lastLogin": "2024-01-01T10:00:00"
    }
  ],
  "pageable": {
    "page": 0,
    "size": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

### 13. Activate/Deactivate User
**Endpoint:** `PUT /api/v1/users/{id}/activate` or `PUT /api/v1/users/{id}/deactivate`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:00:00",
  "lastLogin": "2024-01-01T10:00:00"
}
```

### 14. Verify Email
**Endpoint:** `PUT /api/v1/users/{id}/verify-email`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:00:00",
  "lastLogin": "2024-01-01T10:00:00"
}
```

### 15. Update Profile Image
**Endpoint:** `PUT /api/v1/users/{id}/profile-image?imageUrl=https://example.com/new-profile.jpg`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/new-profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:00:00",
  "lastLogin": "2024-01-01T10:00:00"
}
```

### 16. Update Last Login
**Endpoint:** `PUT /api/v1/users/{id}/last-login`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:00:00",
  "lastLogin": "2024-01-01T11:00:00"
}
```

### 17. Assign Role
**Endpoint:** `PUT /api/v1/users/{id}/role?role=ADMIN`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "ADMIN",
  "isActive": true,
  "emailVerified": true,
  "profileImageUrl": "https://example.com/profile.jpg",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:00:00",
  "lastLogin": "2024-01-01T10:00:00"
}
```

### 18. Delete User (Soft Delete)
**Endpoint:** `DELETE /api/v1/users/{id}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** `204 No Content`

### 19. Permanently Delete User
**Endpoint:** `DELETE /api/v1/users/{id}/hard`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** `204 No Content`

### 20. Utility Endpoints

#### Check Email Exists
**Endpoint:** `GET /api/v1/users/exists/email?email=test@example.com`

**Response:**
```json
true
```

#### Check Username Exists
**Endpoint:** `GET /api/v1/users/exists/username?username=test_user`

**Response:**
```json
false
```

#### Count Users by Role
**Endpoint:** `GET /api/v1/users/count/role/{role}`

**Response:**
```json
5
```

#### Count Active Users
**Endpoint:** `GET /api/v1/users/count/active`

**Response:**
```json
12
```

#### Get Users Created Between Dates
**Endpoint:** `GET /api/v1/users/created-between?startDate=2024-01-01T00:00:00&endDate=2024-01-31T23:59:59`

**Response:**
```json
[
  {
    "id": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+94771234567",
    "role": "PHOTOGRAPHER",
    "isActive": true,
    "emailVerified": true,
    "profileImageUrl": "https://example.com/profile.jpg",
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00",
    "lastLogin": "2024-01-01T10:00:00"
  }
]
```

#### Get Verified Photographers
**Endpoint:** `GET /api/v1/users/photographers/verified?page=0&size=10`

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "phoneNumber": "+94771234567",
      "role": "PHOTOGRAPHER",
      "isActive": true,
      "emailVerified": true,
      "profileImageUrl": "https://example.com/profile.jpg",
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00",
      "lastLogin": "2024-01-01T10:00:00"
    }
  ],
  "pageable": {
    "page": 0,
    "size": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

#### Get Top Photographers
**Endpoint:** `GET /api/v1/users/photographers/top?page=0&size=10`

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "firstName": "John",
      "lastName": "Doe",
      "phoneNumber": "+94771234567",
      "role": "PHOTOGRAPHER",
      "isActive": true,
      "emailVerified": true,
      "profileImageUrl": "https://example.com/profile.jpg",
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00",
      "lastLogin": "2024-01-01T10:00:00"
    }
  ],
  "pageable": {
    "page": 0,
    "size": 10
  },
  "totalElements": 1,
  "totalPages": 1
}
```

## 📮 Testing with Postman

### Step 1: Import Collection
1. Download the Postman collection [here](#)
2. Import the collection into Postman
3. Set environment variables:
   - `base_url`: `http://localhost:8080`
   - `jwt_token`: (will be set automatically after login)

### Step 2: Login Test
1. **Login Request:**
   - Method: POST
   - URL: `{{base_url}}/api/auth/login`
   - Body: JSON with login credentials
   - Tests script:
   ```javascript
   if (pm.response.code === 200) {
       const response = pm.response.json();
       pm.environment.set("jwt_token", response.accessToken);
       pm.environment.set("refresh_token", response.refreshToken);
   }
   ```

### Step 3: Authenticated Requests
1. **Authorization Header:**
   - Key: `Authorization`
   - Value: `Bearer {{jwt_token}}`

2. **Test User Profile:**
   - Method: GET
   - URL: `{{base_url}}/api/v1/users/1`
   - Authorization: Bearer Token

## 📋 Testing with curl

### Login and Get Token
```bash
# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "john_doe",
    "password": "password123"
  }' | jq '.accessToken' > token.txt

# Extract token
TOKEN=$(cat token.txt | tr -d '"')
```

### Authenticated Requests
```bash
# Register first admin (no authentication required)
curl -X POST http://localhost:8080/api/v1/users/register-first-admin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@example.com",
    "password": "Bhanuka@123",
    "firstName": "Admin",
    "lastName": "User",
    "phoneNumber": "+94771234567"
  }'

# Get user profile
curl -X GET http://localhost:8080/api/v1/users/1 \
  -H "Authorization: Bearer $TOKEN"

# Create user (requires admin role)
curl -X POST http://localhost:8080/api/v1/users \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "new_user",
    "email": "newuser@example.com",
    "password": "password123",
    "firstName": "New",
    "lastName": "User",
    "phoneNumber": "+94771234567",
    "role": "PHOTOGRAPHER"
  }'

# Update user profile
curl -X PUT http://localhost:8080/api/v1/users/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John Updated",
    "lastName": "Doe Updated",
    "phoneNumber": "+94779876543"
  }'

# Update password
curl -X PUT http://localhost:8080/api/v1/users/1/password \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "currentPassword": "oldpassword123",
    "newPassword": "newpassword123",
    "confirmPassword": "newpassword123"
  }'

# Get all users (paginated)
curl -X GET "http://localhost:8080/api/v1/users?page=0&size=10" \
  -H "Authorization: Bearer $TOKEN"

# Search users
curl -X GET "http://localhost:8080/api/v1/users/search?term=john&page=0&size=10" \
  -H "Authorization: Bearer $TOKEN"
```

## 🔑 JWT Token Usage

### Access Token
- **Purpose:** Authenticate API requests
- **Lifetime:** 1 hour (configurable)
- **Usage:** Include in Authorization header as `Bearer {token}`

### Refresh Token
- **Purpose:** Generate new access tokens
- **Lifetime:** 7 days (configurable)
- **Usage:** Send to `/api/auth/refresh` endpoint

### Token Structure
```json
{
  "sub": "john_doe",
  "role": "PHOTOGRAPHER",
  "iat": 1640995200,
  "exp": 1640998800
}
```

## 🧪 Common Test Scenarios

### Scenario 1: Successful Login Flow
```bash
# 1. Login with valid credentials
# 2. Receive access and refresh tokens
# 3. Use access token for authenticated requests
# 4. Refresh token when expired
```

### Scenario 2: Invalid Credentials
```bash
# Expected: 401 Unauthorized
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "invalid_user",
    "password": "wrong_password"
  }'
```

### Scenario 3: Expired Token
```bash
# Use expired token (wait 1 hour after login)
# Expected: 401 Unauthorized
```

### Scenario 4: Invalid Token
```bash
# Use malformed token
curl -X GET http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer invalid_token"
# Expected: 401 Unauthorized
```

### Scenario 5: Role-Based Access
```bash
# Test admin endpoints with photographer role
# Expected: 403 Forbidden
curl -X GET http://localhost:8080/api/admin/users \
  -H "Authorization: Bearer $PHOTOGRAPHER_TOKEN"
```

## 🔧 Troubleshooting

### Common Issues

#### 1. Authentication Fails
**Problem:** 401 Unauthorized despite valid credentials
**Solution:**
- Check database connection
- Verify user exists and is active
- Check password encoding

#### 2. Token Validation Fails
**Problem:** JWT token not accepted
**Solution:**
- Verify JWT secret matches between requests
- Check token expiration
- Ensure proper Bearer token format

#### 3. CORS Issues
**Problem:** Browser blocks cross-origin requests
**Solution:** Check CORS configuration in SecurityConfig

#### 4. Database Connection
**Problem:** Cannot find users in database
**Solution:**
- Verify database is running
- Check connection string in application.yml
- Ensure user table exists

### Debug Tips

1. **Enable Debug Logging:**
   ```yaml
   logging:
     level:
       lk.ceylonwildcapture_backend: DEBUG
       org.springframework.security: DEBUG
   ```

2. **Check Token Contents:**
   ```bash
   # Decode JWT token
   echo $TOKEN | cut -d '.' -f 2 | base64 -d | jq
   ```

3. **Verify User in Database:**
   ```sql
   SELECT * FROM users WHERE username = 'john_doe';
   ```

## 📊 Test Data

### Sample Users (for testing)
```sql
-- Admin User
INSERT INTO users (username, email, password, first_name, last_name, role, is_active, email_verified)
VALUES ('admin', 'admin@example.com', '$2a$10$...', 'Admin', 'User', 'ADMIN', true, true);

-- Photographer User
INSERT INTO users (username, email, password, first_name, last_name, role, is_active, email_verified)
VALUES ('photographer', 'photo@example.com', '$2a$10$...', 'Photo', 'Grapher', 'PHOTOGRAPHER', true, true);

-- Buyer User
INSERT INTO users (username, email, password, first_name, last_name, role, is_active, email_verified)
VALUES ('buyer', 'buyer@example.com', '$2a$10$...', 'Buyer', 'User', 'BUYER', true, true);
```

### Default Passwords
- **All test users:** `password123`
- **Password encoding:** BCrypt

## 📝 API Documentation

Once the application is running, you can access:
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI Spec:** `http://localhost:8080/v3/api-docs`

## 🚨 Security Notes

1. **Never commit JWT secrets** to version control
2. **Use HTTPS** in production environments
3. **Implement rate limiting** for authentication endpoints
4. **Regularly rotate JWT secrets**
5. **Monitor failed login attempts**

## 📞 Support

For issues or questions:
1. Check application logs
2. Verify database connectivity
3. Validate JWT configuration
4. Review security configuration

---

**Happy Testing! 🎉**
