# User Module - Complete API Documentation

**Ceylon Wild Capture Backend - User Management & Authentication System**

This comprehensive documentation covers all API endpoints for the User Module, including authentication, user management, and photographer profiles.

---

## 📋 Table of Contents

1. [Module Overview](#module-overview)
2. [Data Models](#data-models)
3. [Authentication API](#authentication-api)
4. [User Management API](#user-management-api)
5. [Testing Guide](#testing-guide)
6. [Best Practices](#best-practices)

---

## 🔎 Module Overview

The User Module manages user accounts, authentication, authorization, and photographer profiles for the Ceylon Wild Capture platform.

### Key Features

- **JWT Authentication**: Secure token-based authentication with refresh tokens
- **Role-Based Access Control**: ADMIN, PHOTOGRAPHER, and BUYER roles
- **User Management**: Complete CRUD operations for user accounts
- **Photographer Profiles**: Extended profiles for photographers with earnings tracking
- **Email Verification**: Email verification workflow
- **Password Management**: Secure password change and reset functionality
- **Soft Delete**: User deactivation with optional permanent deletion
- **Activity Tracking**: Last login tracking and user statistics

### Controllers

1. **AuthController** (`/api/auth`) - Authentication operations (login, token refresh, validation)
2. **UserController** (`/api/v1/users`) - User management and profile operations

### User Roles

- **ADMIN**: Full system access, user management, content moderation
- **PHOTOGRAPHER**: Upload photos, manage portfolio, track earnings
- **BUYER**: Purchase and download photos

---

## 🗂️ Data Models

### User Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `username` | String | 3-50 chars, unique, required | Username for login |
| `email` | String | Valid email, unique, required | User email address |
| `password` | String | Min 8 chars, required | Hashed password |
| `firstName` | String | Max 50 chars, required | User's first name |
| `lastName` | String | Max 50 chars, required | User's last name |
| `phoneNumber` | String | Max 20 chars | Contact phone number |
| `role` | UserRole | Required | User role (ADMIN/PHOTOGRAPHER/BUYER) |
| `isActive` | Boolean | Default: true | Account active status |
| `emailVerified` | Boolean | Default: false | Email verification status |
| `profileImageUrl` | String | - | Profile picture URL |
| `createdAt` | LocalDateTime | Auto-generated | Account creation timestamp |
| `updatedAt` | LocalDateTime | Auto-updated | Last update timestamp |
| `lastLogin` | LocalDateTime | - | Last login timestamp |

**Relationships:**
- One-to-One: `PhotographerProfile` (for photographers)
- One-to-Many: `Photos` (uploaded photos)
- One-to-Many: `Orders` (purchase history)
- One-to-Many: `DownloadAudits` (download history)
- One-to-Many: `ModerationRecords` (moderation actions)

---

### PhotographerProfile Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `user` | User | Required, unique | Associated user account |
| `bio` | String | Max 2000 chars | Photographer biography |
| `portfolioUrl` | String | - | External portfolio URL |
| `socialMediaLinks` | String | TEXT | JSON/comma-separated social links |
| `bankAccountNumber` | String | Max 50 chars | Bank account for payouts |
| `bankName` | String | Max 100 chars | Bank name |
| `paypalEmail` | String | Max 100 chars | PayPal email for payouts |
| `totalEarnings` | BigDecimal | Precision 12,2, Default: 0 | Total lifetime earnings |
| `pendingEarnings` | BigDecimal | Precision 12,2, Default: 0 | Pending payout amount |
| `commissionRate` | BigDecimal | Precision 5,2, Default: 15.00 | Platform commission rate (%) |
| `verifiedPhotographer` | Boolean | Default: false | Verification badge status |
| `totalSales` | Integer | Default: 0 | Total number of sales |
| `rating` | BigDecimal | Precision 3,2 | Average rating (0-5) |
| `totalReviews` | Integer | Default: 0 | Total number of reviews |
| `createdAt` | LocalDateTime | Auto-generated | Profile creation timestamp |
| `updatedAt` | LocalDateTime | Auto-updated | Last update timestamp |

**Relationships:**
- One-to-Many: `Payouts` (payout history)

---

## 🔐 Authentication API

**Base Path:** `/api/auth`

### 1. User Login
```http
POST /api/auth/login
Content-Type: application/json
```

**Request Body:**
```json
{
  "usernameOrEmail": "john.doe@example.com",
  "password": "SecurePassword123!",
  "rememberMe": false
}
```

**Response:** `200 OK`
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600,
  "user": {
    "id": 1,
    "username": "johndoe",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "PHOTOGRAPHER",
    "isActive": true,
    "emailVerified": true,
    "profileImageUrl": "https://example.com/profiles/johndoe.jpg"
  }
}
```

**Error Responses:**
- `401 Unauthorized`: Invalid credentials
- `400 Bad Request`: Validation errors

**Notes:**
- Accepts either username or email for login
- `rememberMe` extends token expiration time
- Returns both access token (short-lived) and refresh token (long-lived)

---

### 2. Refresh Access Token
```http
POST /api/auth/refresh
Content-Type: application/json
```

**Request Body:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

**Response:** `200 OK`
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

**Error Responses:**
- `401 Unauthorized`: Invalid or expired refresh token
- `400 Bad Request`: Missing refresh token

**Notes:**
- Use when access token expires
- Refresh token remains valid for longer period
- Does not return a new refresh token

---

### 3. Validate Credentials
```http
POST /api/auth/validate
Content-Type: application/json
```

**Request Body:**
```json
{
  "usernameOrEmail": "john.doe@example.com",
  "password": "SecurePassword123!"
}
```

**Response:** `200 OK` (Valid credentials)
```json
{
  "valid": true
}
```

**Response:** `401 Unauthorized` (Invalid credentials)
```json
{
  "valid": false
}
```

**Notes:**
- Validates credentials without generating tokens
- Useful for re-authentication flows
- Does not update last login timestamp

---

## 👥 User Management API

**Base Path:** `/api/v1/users`

### CRUD Operations

#### 1. Create User
```http
POST /api/v1/users
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "username": "johndoe",
  "email": "john.doe@example.com",
  "password": "SecurePassword123!",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true,
  "emailVerified": false
}
```

**Response:** `200 OK` with `UserResponseDto`

**Notes:**
- Admin-only operation
- Password is automatically hashed
- Email verification email may be sent

---

#### 2. Update User
```http
PUT /api/v1/users/{userId}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "username": "johndoe_updated",
  "email": "john.updated@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "role": "PHOTOGRAPHER",
  "isActive": true
}
```

**Response:** `200 OK` with `UserResponseDto`

**Notes:**
- All fields are optional
- Cannot update password through this endpoint
- Email change may require re-verification

---

#### 3. Get User by ID
```http
GET /api/v1/users/{userId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `UserResponseDto` or `404 Not Found`

---

#### 4. Get User by Email
```http
GET /api/v1/users/email/{email}
Authorization: Bearer {token}
```

**Example:**
```http
GET /api/v1/users/email/john.doe@example.com
Authorization: Bearer {token}
```

**Response:** `200 OK` with `UserResponseDto` or `404 Not Found`

---

#### 5. Get User by Username
```http
GET /api/v1/users/username/{username}
Authorization: Bearer {token}
```

**Example:**
```http
GET /api/v1/users/username/johndoe
Authorization: Bearer {token}
```

**Response:** `200 OK` with `UserResponseDto` or `404 Not Found`

---

### Retrieval & Filtering

#### 6. Get All Users (Paginated)
```http
GET /api/v1/users?page=0&size=20&sort=createdAt,desc
Authorization: Bearer {token}
```

**Response:** `Page<UserResponseDto>`

---

#### 7. Get Users by Role
```http
GET /api/v1/users/role/{role}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `role`: ADMIN, PHOTOGRAPHER, or BUYER

**Example:**
```http
GET /api/v1/users/role/PHOTOGRAPHER?page=0&size=20
Authorization: Bearer {token}
```

---

#### 8. Search Users
```http
GET /api/v1/users/search?term=john&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `term` (required): Search term (searches username, email, first name, last name)

**Notes:**
- Case-insensitive search
- Searches across multiple fields

---

#### 9. Get Active Users
```http
GET /api/v1/users/active?isActive=true&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `isActive` (optional, default: true): Filter by active status

---

#### 10. Get Users Created Between Dates
```http
GET /api/v1/users/created-between?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59
Authorization: Bearer {token}
```

**Query Parameters:**
- `startDate` (required): Start date in ISO format
- `endDate` (required): End date in ISO format

**Response:** `List<UserResponseDto>`

---

#### 11. Get Verified Photographers
```http
GET /api/v1/users/photographers/verified?page=0&size=20
Authorization: Bearer {token}
```

**Response:** Paginated list of photographers with verified status

---

#### 12. Get Top Photographers
```http
GET /api/v1/users/photographers/top?page=0&size=10
Authorization: Bearer {token}
```

**Response:** Photographers ordered by total sales or rating

**Notes:**
- Useful for featured photographer sections
- Typically includes verified photographers only

---

### Account Management

#### 13. Soft Delete User
```http
DELETE /api/v1/users/{userId}
Authorization: Bearer {token}
```

**Response:** `204 No Content`

**Notes:**
- Sets `isActive` to false
- User data is retained
- Can be reactivated later
- Admin-only operation

---

#### 14. Permanently Delete User
```http
DELETE /api/v1/users/{userId}/hard
Authorization: Bearer {token}
```

**Response:** `204 No Content`

**Notes:**
- Permanently removes user from database
- Cannot be undone
- Admin-only operation
- May cascade delete related data

---

#### 15. Activate User
```http
PUT /api/v1/users/{userId}/activate
Authorization: Bearer {token}
```

**Response:** `200 OK` with `UserResponseDto`

**Notes:**
- Sets `isActive` to true
- Restores user access

---

#### 16. Deactivate User
```http
PUT /api/v1/users/{userId}/deactivate
Authorization: Bearer {token}
```

**Response:** `200 OK` with `UserResponseDto`

**Notes:**
- Sets `isActive` to false
- Suspends user access
- Soft delete alternative

---

#### 17. Verify Email
```http
PUT /api/v1/users/{userId}/verify-email
Authorization: Bearer {token}
```

**Response:** `200 OK` with `UserResponseDto`

**Notes:**
- Sets `emailVerified` to true
- May be called after email verification link is clicked

---

### Password Management

#### 18. Update Password (Authenticated)
```http
PUT /api/v1/users/{userId}/password
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "currentPassword": "OldPassword123!",
  "newPassword": "NewSecurePassword456!",
  "confirmPassword": "NewSecurePassword456!"
}
```

**Response:** `200 OK` with `UserResponseDto`

**Notes:**
- Requires current password for verification
- New password must meet security requirements
- User must be authenticated

---

#### 19. Reset Password (Forgot Password)
```http
PUT /api/v1/users/reset-password
Content-Type: application/json
```

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "resetToken": "abc123xyz789",
  "newPassword": "NewSecurePassword456!",
  "confirmPassword": "NewSecurePassword456!"
}
```

**Response:** `200 OK` with `UserResponseDto`

**Notes:**
- Requires valid reset token (sent via email)
- No authentication required
- Token expires after use or timeout

---

### Profile Management

#### 20. Update Profile Info
```http
PUT /api/v1/users/{userId}/profile
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+94771234567",
  "bio": "Wildlife photographer specializing in Sri Lankan fauna",
  "portfolioUrl": "https://johndoe.photography",
  "socialMediaLinks": "{\"instagram\":\"@johndoe\",\"twitter\":\"@johndoe_photo\"}"
}
```

**Response:** `200 OK` with `UserResponseDto`

**Notes:**
- Updates both User and PhotographerProfile (if applicable)
- All fields are optional

---

#### 21. Update Profile Image
```http
PUT /api/v1/users/{userId}/profile-image?imageUrl=https://example.com/new-profile.jpg
Authorization: Bearer {token}
```

**Query Parameters:**
- `imageUrl` (required): New profile image URL

**Response:** `200 OK` with `UserResponseDto`

---

### Role Management

#### 22. Assign Role
```http
PUT /api/v1/users/{userId}/role?role=PHOTOGRAPHER
Authorization: Bearer {token}
```

**Query Parameters:**
- `role` (required): ADMIN, PHOTOGRAPHER, or BUYER

**Response:** `200 OK` with `UserResponseDto`

**Notes:**
- Admin-only operation
- Changing to PHOTOGRAPHER may create PhotographerProfile
- Role changes affect permissions immediately

---

### Utility Operations

#### 23. Check Email Exists
```http
GET /api/v1/users/exists/email?email=john.doe@example.com
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
true
```

**Notes:**
- Useful for registration form validation
- Returns boolean

---

#### 24. Check Username Exists
```http
GET /api/v1/users/exists/username?username=johndoe
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
false
```

**Notes:**
- Useful for registration form validation
- Returns boolean

---

#### 25. Update Last Login
```http
PUT /api/v1/users/{userId}/last-login
Authorization: Bearer {token}
```

**Response:** `200 OK` with `UserResponseDto`

**Notes:**
- Updates `lastLogin` timestamp
- Typically called automatically after successful login

---

### Statistics

#### 26. Count Users by Role
```http
GET /api/v1/users/count/role/{role}
Authorization: Bearer {token}
```

**Path Parameters:**
- `role`: ADMIN, PHOTOGRAPHER, or BUYER

**Example:**
```http
GET /api/v1/users/count/role/PHOTOGRAPHER
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
142
```

---

#### 27. Count Active Users
```http
GET /api/v1/users/count/active
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
1547
```

---

### Special Operations

#### 28. Register First Admin (No Auth Required)
```http
POST /api/v1/users/register-first-admin
Content-Type: application/json
```

**Request Body:**
```json
{
  "username": "admin",
  "email": "admin@ceylonwildcapture.com",
  "password": "AdminPassword123!",
  "firstName": "System",
  "lastName": "Administrator",
  "phoneNumber": "+94771234567"
}
```

**Response:** `200 OK` with `UserResponseDto`

**Notes:**
- **No authentication required**
- Only works if no admin users exist
- Automatically sets role to ADMIN
- Sets `emailVerified` and `isActive` to true
- Should be disabled in production after first admin is created

---

## 🧪 Testing Guide

### Prerequisites

- Java 25+
- Maven 3.6+
- MySQL 8.0+
- Postman or curl

### Setup

1. **Configure Database:**
   ```yaml
   spring:
     datasource:
       url: jdbc:mysql://localhost:3306/ceylonwildcapture
       username: your_username
       password: your_password
   ```

2. **Set Environment Variables:**
   ```bash
   export JWT_SECRET="your-secret-key-here"
   ```

3. **Run Application:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### Example Test Scenarios

#### Scenario 1: Complete User Registration & Login Flow

```bash
# 1. Register first admin (no auth required)
curl -X POST http://localhost:8080/api/v1/users/register-first-admin \
  -H "Content-Type: application/json" \
  -d '{
    "username": "admin",
    "email": "admin@ceylonwildcapture.com",
    "password": "AdminPassword123!",
    "firstName": "System",
    "lastName": "Administrator"
  }'

# 2. Login as admin
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin",
    "password": "AdminPassword123!"
  }' | jq -r '.accessToken')

echo "Admin Token: $TOKEN"

# 3. Create photographer user
PHOTOGRAPHER_ID=$(curl -s -X POST http://localhost:8080/api/v1/users \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "username": "johndoe",
    "email": "john.doe@example.com",
    "password": "PhotographerPass123!",
    "firstName": "John",
    "lastName": "Doe",
    "role": "PHOTOGRAPHER"
  }' | jq -r '.id')

echo "Created Photographer ID: $PHOTOGRAPHER_ID"

# 4. Verify photographer email
curl -X PUT "http://localhost:8080/api/v1/users/$PHOTOGRAPHER_ID/verify-email" \
  -H "Authorization: Bearer $TOKEN"

# 5. Login as photographer
PHOTO_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "johndoe",
    "password": "PhotographerPass123!"
  }' | jq -r '.accessToken')

echo "Photographer Token: $PHOTO_TOKEN"
```

---

#### Scenario 2: Password Management

```bash
# 1. Change password (authenticated)
curl -X PUT "http://localhost:8080/api/v1/users/$USER_ID/password" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "currentPassword": "OldPassword123!",
    "newPassword": "NewSecurePassword456!",
    "confirmPassword": "NewSecurePassword456!"
  }'

# 2. Validate new credentials
curl -X POST http://localhost:8080/api/auth/validate \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "johndoe",
    "password": "NewSecurePassword456!"
  }'
```

---

#### Scenario 3: Token Refresh Flow

```bash
# 1. Login and save tokens
RESPONSE=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "johndoe",
    "password": "SecurePassword123!",
    "rememberMe": true
  }')

ACCESS_TOKEN=$(echo $RESPONSE | jq -r '.accessToken')
REFRESH_TOKEN=$(echo $RESPONSE | jq -r '.refreshToken')

echo "Access Token: $ACCESS_TOKEN"
echo "Refresh Token: $REFRESH_TOKEN"

# 2. Wait for access token to expire (or simulate expiration)
# ...

# 3. Refresh access token
NEW_ACCESS_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d "{\"refreshToken\":\"$REFRESH_TOKEN\"}" \
  | jq -r '.accessToken')

echo "New Access Token: $NEW_ACCESS_TOKEN"

# 4. Use new access token
curl -X GET http://localhost:8080/api/v1/users/1 \
  -H "Authorization: Bearer $NEW_ACCESS_TOKEN"
```

---

#### Scenario 4: User Profile Management

```bash
# 1. Update profile info
curl -X PUT "http://localhost:8080/api/v1/users/$USER_ID/profile" \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+94771234567",
    "bio": "Wildlife photographer specializing in Sri Lankan fauna",
    "portfolioUrl": "https://johndoe.photography"
  }'

# 2. Update profile image
curl -X PUT "http://localhost:8080/api/v1/users/$USER_ID/profile-image?imageUrl=https://example.com/profile.jpg" \
  -H "Authorization: Bearer $TOKEN"

# 3. Get updated profile
curl -X GET "http://localhost:8080/api/v1/users/$USER_ID" \
  -H "Authorization: Bearer $TOKEN"
```

---

#### Scenario 5: User Search & Filtering

```bash
# 1. Search users by term
curl -X GET "http://localhost:8080/api/v1/users/search?term=john&page=0&size=10" \
  -H "Authorization: Bearer $TOKEN"

# 2. Get photographers only
curl -X GET "http://localhost:8080/api/v1/users/role/PHOTOGRAPHER?page=0&size=20" \
  -H "Authorization: Bearer $TOKEN"

# 3. Get verified photographers
curl -X GET "http://localhost:8080/api/v1/users/photographers/verified?page=0&size=20" \
  -H "Authorization: Bearer $TOKEN"

# 4. Get top photographers
curl -X GET "http://localhost:8080/api/v1/users/photographers/top?page=0&size=10" \
  -H "Authorization: Bearer $TOKEN"

# 5. Get active users
curl -X GET "http://localhost:8080/api/v1/users/active?isActive=true&page=0&size=20" \
  -H "Authorization: Bearer $TOKEN"
```

---

#### Scenario 6: User Statistics

```bash
# 1. Count photographers
PHOTOGRAPHER_COUNT=$(curl -s -X GET "http://localhost:8080/api/v1/users/count/role/PHOTOGRAPHER" \
  -H "Authorization: Bearer $TOKEN")
echo "Total Photographers: $PHOTOGRAPHER_COUNT"

# 2. Count active users
ACTIVE_COUNT=$(curl -s -X GET "http://localhost:8080/api/v1/users/count/active" \
  -H "Authorization: Bearer $TOKEN")
echo "Active Users: $ACTIVE_COUNT"

# 3. Get users created this month
START_DATE=$(date -u +"%Y-%m-01T00:00:00")
END_DATE=$(date -u +"%Y-%m-%dT23:59:59")

curl -X GET "http://localhost:8080/api/v1/users/created-between?startDate=$START_DATE&endDate=$END_DATE" \
  -H "Authorization: Bearer $TOKEN"
```

---

#### Scenario 7: Account Deactivation & Reactivation

```bash
# 1. Deactivate user
curl -X PUT "http://localhost:8080/api/v1/users/$USER_ID/deactivate" \
  -H "Authorization: Bearer $TOKEN"

# 2. Verify user cannot login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "johndoe",
    "password": "SecurePassword123!"
  }'
# Should return 401 Unauthorized

# 3. Reactivate user
curl -X PUT "http://localhost:8080/api/v1/users/$USER_ID/activate" \
  -H "Authorization: Bearer $TOKEN"

# 4. Verify user can login again
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "johndoe",
    "password": "SecurePassword123!"
  }'
```

---

## 📝 Best Practices

### Authentication

1. **Use HTTPS in production** - Never transmit credentials over HTTP
2. **Store tokens securely** - Use secure storage (HttpOnly cookies, secure storage APIs)
3. **Implement token refresh** - Refresh access tokens before expiration
4. **Handle token expiration** - Implement proper error handling for 401 responses
5. **Use strong passwords** - Enforce password complexity requirements
6. **Implement rate limiting** - Prevent brute force attacks on login endpoint

### User Management

1. **Validate email addresses** - Verify email ownership before activation
2. **Use soft delete** - Prefer deactivation over permanent deletion
3. **Audit user changes** - Log all user modifications for security
4. **Implement role-based access** - Restrict operations based on user roles
5. **Sanitize user inputs** - Prevent XSS and injection attacks
6. **Use pagination** - Always paginate user lists for performance

### Password Security

1. **Hash passwords** - Never store plain text passwords
2. **Use strong hashing** - BCrypt with appropriate work factor
3. **Implement password policies** - Minimum length, complexity requirements
4. **Expire reset tokens** - Time-limit password reset tokens
5. **Require current password** - For password changes by authenticated users
6. **Prevent password reuse** - Optional: Track password history

### Profile Management

1. **Validate profile images** - Check file type, size, and dimensions
2. **Sanitize bio/description** - Prevent XSS in user-generated content
3. **Verify external URLs** - Validate portfolio and social media links
4. **Update timestamps** - Track profile modification times
5. **Handle photographer profiles** - Create automatically when role changes

### Security Considerations

1. **Implement CSRF protection** - For state-changing operations
2. **Use secure session management** - Proper token lifecycle
3. **Log security events** - Track login attempts, password changes
4. **Implement account lockout** - After multiple failed login attempts
5. **Verify email changes** - Require confirmation for email updates
6. **Restrict admin operations** - Limit who can create/modify users

---

## 🔒 Security Notes

### JWT Token Security

- **Access Token**: Short-lived (15-60 minutes)
- **Refresh Token**: Long-lived (7-30 days)
- **Token Storage**: Client-side secure storage
- **Token Transmission**: Authorization header only
- **Token Validation**: Signature, expiration, claims

### Password Requirements

- Minimum 8 characters
- At least one uppercase letter
- At least one lowercase letter
- At least one number
- At least one special character
- Cannot be common passwords

### Account Security

- Email verification required for full access
- Failed login attempts tracked
- Account lockout after 5 failed attempts
- Password reset tokens expire in 1 hour
- Last login timestamp tracked

---

## 📊 API Documentation

**Swagger UI:** `http://localhost:8080/swagger-ui.html`  
**OpenAPI Spec:** `http://localhost:8080/v3/api-docs`

---

## 🆘 Support

For issues or questions:
1. Check application logs
2. Verify database connectivity
3. Validate JWT token format and expiration
4. Review user role permissions
5. Consult API documentation

---

## 📈 User Statistics Endpoints Summary

| Endpoint | Description | Response Type |
|----------|-------------|---------------|
| `GET /count/role/{role}` | Count users by role | Long |
| `GET /count/active` | Count active users | Long |
| `GET /photographers/verified` | Get verified photographers | Page<UserResponseDto> |
| `GET /photographers/top` | Get top photographers | Page<UserResponseDto> |
| `GET /created-between` | Get users by date range | List<UserResponseDto> |

---

**Version:** 1.0.0  
**Last Updated:** December 2024  
**Module:** User Management & Authentication  
**Platform:** Ceylon Wild Capture Backend
