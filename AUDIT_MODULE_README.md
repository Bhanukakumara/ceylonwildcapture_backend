# Audit Module - Complete API Documentation

**Ceylon Wild Capture Backend - Comprehensive Audit & Activity Tracking System**

This comprehensive documentation covers all API endpoints and audit entities for the Audit Module, providing complete tracking of user activities, admin actions, downloads, payments, and system events.

---

## 📋 Table of Contents

1. [Module Overview](#module-overview)
2. [Data Models](#data-models)
3. [Audit Query API](#audit-query-api)
4. [Testing Guide](#testing-guide)
5. [Best Practices](#best-practices)

---

## 🔎 Module Overview

The Audit Module provides comprehensive tracking and logging of all significant activities within the Ceylon Wild Capture platform, ensuring accountability, security, and compliance.

### Key Features

- **Login Tracking**: Monitor all login attempts (successful and failed)
- **Download Auditing**: Track all photo downloads with license verification
- **Admin Action Logging**: Record all administrative actions and moderation
- **Payment Auditing**: Track payment transactions and payout events
- **User Activity Monitoring**: Log profile changes, password resets, and user actions
- **Photo Moderation**: Track photo approval/rejection decisions
- **Advanced Search**: Query audit logs with multiple filter criteria
- **Compliance Ready**: Maintain complete audit trails for regulatory compliance
- **Security Monitoring**: Detect suspicious activities and patterns

### Audit Types

1. **LOGIN_AUDIT**: User authentication events
2. **DOWNLOAD_AUDIT**: Photo download events
3. **ADMIN_ACTION_AUDIT**: Administrative actions
4. **PAYMENT_AUDIT**: Payment and payout events
5. **USER_ACTIVITY_AUDIT**: User profile and account changes
6. **MODERATION_RECORD**: Photo moderation decisions

### Controllers

1. **AuditController** (`/api/v1/audit`) - Unified audit query interface

---

## 🗂️ Data Models

### LoginAudit Entity

Tracks all login attempts, both successful and failed.

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `user` | User | - | User who attempted login (null for failed attempts) |
| `actionResult` | ActionResult | Required | SUCCESS or FAILURE |
| `ipAddress` | String | Max 45 chars | Client IP address |
| `userAgent` | String | TEXT | Browser user agent string |
| `device` | String | Max 100 chars | Device type (Mobile, Desktop, Tablet) |
| `browser` | String | Max 100 chars | Browser name and version |
| `operatingSystem` | String | Max 100 chars | Operating system |
| `country` | String | Max 100 chars | Country from IP geolocation |
| `city` | String | Max 100 chars | City from IP geolocation |
| `errorMessage` | String | TEXT | Error message for failed attempts |
| `createdAt` | LocalDateTime | Auto-generated | Login attempt timestamp |

**Indexes:** user_id, created_at, action_result

---

### DownloadAudit Entity

Tracks all photo downloads with license verification.

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `license` | License | Required | License used for download |
| `user` | User | Required | User who downloaded |
| `ipAddress` | String | Max 45 chars | Client IP address |
| `userAgent` | String | TEXT | Browser user agent string |
| `downloadUrl` | String | TEXT | Download URL generated |
| `fileSize` | Long | - | File size in bytes |
| `downloadSuccessful` | Boolean | Default: true | Download success status |
| `errorMessage` | String | TEXT | Error message if failed |
| `device` | String | Max 100 chars | Device type |
| `browser` | String | Max 100 chars | Browser name |
| `operatingSystem` | String | Max 100 chars | Operating system |
| `country` | String | Max 100 chars | Country from IP geolocation |
| `city` | String | Max 100 chars | City from IP geolocation |
| `createdAt` | LocalDateTime | Auto-generated | Download timestamp |

**Purpose:** Track license usage, detect abuse, analytics

---

### AdminActionAudit Entity

Tracks all administrative actions and moderation decisions.

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `admin` | User | Required | Admin who performed action |
| `entityType` | EntityType | Required | Type of entity (PHOTO, USER, ORDER, etc.) |
| `entityId` | Long | Required | ID of affected entity |
| `action` | String | Max 100 chars, required | Action performed |
| `actionResult` | ActionResult | Required | SUCCESS or FAILURE |
| `reason` | String | TEXT | Reason for action |
| `metadata` | String | JSON | Additional action metadata |
| `ipAddress` | String | Max 45 chars | Admin IP address |
| `userAgent` | String | TEXT | Browser user agent |
| `createdAt` | LocalDateTime | Auto-generated | Action timestamp |

**Indexes:** admin_id, entity_id, entity_type, created_at

**Common Actions:**
- APPROVE_PHOTO, REJECT_PHOTO
- DEACTIVATE_USER, ACTIVATE_USER
- REFUND_ORDER, CANCEL_ORDER
- UPDATE_PAYOUT_STATUS

---

### PaymentAudit Entity

Tracks payment transactions, refunds, and payout events.

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `user` | User | Required | User associated with payment |
| `paymentId` | Long | Required | Payment ID |
| `orderId` | Long | - | Associated order ID |
| `action` | String | Max 100 chars, required | Payment action |
| `actionResult` | ActionResult | Required | SUCCESS or FAILURE |
| `amount` | BigDecimal | - | Transaction amount |
| `currency` | String | Max 3 chars | Currency code (USD, LKR) |
| `paymentMethod` | String | Max 50 chars | Payment method used |
| `transactionId` | String | Max 100 chars | Gateway transaction ID |
| `metadata` | String | JSON | Additional payment metadata |
| `errorMessage` | String | TEXT | Error message if failed |
| `ipAddress` | String | Max 45 chars | Client IP address |
| `createdAt` | LocalDateTime | Auto-generated | Event timestamp |

**Indexes:** user_id, order_id, payment_id, created_at

**Common Actions:**
- PAYMENT_INITIATED, PAYMENT_COMPLETED, PAYMENT_FAILED
- REFUND_INITIATED, REFUND_COMPLETED
- PAYOUT_REQUESTED, PAYOUT_APPROVED, PAYOUT_COMPLETED

---

### UserActivityAudit Entity

Tracks user profile changes, password resets, and account modifications.

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `user` | User | Required | User who performed action |
| `action` | String | Max 100 chars, required | Action performed |
| `actionResult` | ActionResult | Required | SUCCESS or FAILURE |
| `description` | String | TEXT | Action description |
| `oldValue` | String | TEXT | Previous value (for updates) |
| `newValue` | String | TEXT | New value (for updates) |
| `metadata` | String | JSON | Additional metadata |
| `ipAddress` | String | Max 45 chars | Client IP address |
| `userAgent` | String | TEXT | Browser user agent |
| `createdAt` | LocalDateTime | Auto-generated | Action timestamp |

**Indexes:** user_id, action, created_at

**Common Actions:**
- PROFILE_UPDATED, EMAIL_CHANGED, PASSWORD_CHANGED
- PASSWORD_RESET_REQUESTED, PASSWORD_RESET_COMPLETED
- ACCOUNT_ACTIVATED, ACCOUNT_DEACTIVATED
- EMAIL_VERIFIED

---

### ModerationRecord Entity

Tracks photo moderation decisions (approval/rejection).

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `photo` | Photo | Required | Photo being moderated |
| `moderator` | User | Required | Admin who moderated |
| `action` | ModerationAction | Required | APPROVE or REJECT |
| `reason` | String | TEXT | Reason for decision |
| `notes` | String | TEXT | Additional notes |
| `previousStatus` | String | Max 20 chars | Previous photo status |
| `newStatus` | String | Max 20 chars | New photo status |
| `ipAddress` | String | Max 45 chars | Moderator IP address |
| `createdAt` | LocalDateTime | Auto-generated | Moderation timestamp |

**Purpose:** Track content moderation decisions for accountability

---

## 🔍 Audit Query API

**Base Path:** `/api/v1/audit`

**Note:** All endpoints require ADMIN role for access

### Unified Audit Search

#### 1. Search All Audit Events
```http
GET /api/v1/audit?auditType=LOGIN_AUDIT&userId=5&fromDate=2024-01-01T00:00:00&toDate=2024-12-31T23:59:59&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `auditType` (optional): LOGIN_AUDIT, DOWNLOAD_AUDIT, ADMIN_ACTION_AUDIT, PAYMENT_AUDIT, USER_ACTIVITY_AUDIT
- `userId` (optional): Filter by user ID
- `adminId` (optional): Filter by admin ID (for admin actions)
- `entityId` (optional): Filter by entity ID
- `fromDate` (optional): Start date in ISO format
- `toDate` (optional): End date in ISO format

**Response:** `Page<AuditEventResponse>`

**Example Response:**
```json
{
  "content": [
    {
      "id": 1,
      "auditType": "LOGIN_AUDIT",
      "userId": 5,
      "userName": "john.doe@example.com",
      "action": "LOGIN_ATTEMPT",
      "actionResult": "SUCCESS",
      "ipAddress": "192.168.1.100",
      "device": "Desktop",
      "browser": "Chrome 120.0",
      "country": "Sri Lanka",
      "city": "Colombo",
      "createdAt": "2024-12-06T13:48:35Z"
    }
  ],
  "totalElements": 1547,
  "totalPages": 78,
  "number": 0,
  "size": 20
}
```

**Notes:**
- Searches across all audit types
- Supports multiple filter criteria
- Returns unified audit event format
- Pagination supported

---

### Login Audit

#### 2. Get Login History
```http
GET /api/v1/audit/login?userId=5&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `userId` (optional): Filter by specific user (if not provided, returns all)

**Response:** `Page<LoginAuditDto>`

**Example Response:**
```json
{
  "content": [
    {
      "id": 1,
      "userId": 5,
      "userName": "john.doe@example.com",
      "actionResult": "SUCCESS",
      "ipAddress": "192.168.1.100",
      "userAgent": "Mozilla/5.0...",
      "device": "Desktop",
      "browser": "Chrome 120.0",
      "operatingSystem": "Windows 10",
      "country": "Sri Lanka",
      "city": "Colombo",
      "errorMessage": null,
      "createdAt": "2024-12-06T13:48:35Z"
    },
    {
      "id": 2,
      "userId": null,
      "userName": "unknown@example.com",
      "actionResult": "FAILURE",
      "ipAddress": "192.168.1.101",
      "errorMessage": "Invalid credentials",
      "createdAt": "2024-12-06T13:45:20Z"
    }
  ]
}
```

**Use Cases:**
- Monitor user login patterns
- Detect failed login attempts
- Identify suspicious login locations
- Track user session history

---

### Download Audit

#### 3. Get Download History
```http
GET /api/v1/audit/download?userId=5&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `userId` (optional): Filter by user ID
- `licenseId` (optional): Filter by license ID
- **Note:** Either userId or licenseId must be provided

**Response:** `Page<DownloadAuditDto>`

**Example Response:**
```json
{
  "content": [
    {
      "id": 1,
      "licenseId": 123,
      "licenseKey": "LIC-2024-ABC123XYZ",
      "userId": 5,
      "userName": "john.doe@example.com",
      "photoId": 456,
      "photoTitle": "Sri Lankan Leopard",
      "ipAddress": "192.168.1.100",
      "downloadUrl": "https://cdn.example.com/downloads/...",
      "fileSize": 15728640,
      "downloadSuccessful": true,
      "errorMessage": null,
      "device": "Desktop",
      "browser": "Chrome 120.0",
      "country": "Sri Lanka",
      "createdAt": "2024-12-06T14:30:00Z"
    }
  ]
}
```

**Use Cases:**
- Track license usage
- Monitor download patterns
- Detect license abuse
- Generate download reports
- Verify license compliance

---

### Admin Action Audit

#### 4. Get Admin Actions
```http
GET /api/v1/audit/admin?adminId=1&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `adminId` (required): Filter by admin user ID

**Response:** `Page<AdminActionAuditDto>`

**Example Response:**
```json
{
  "content": [
    {
      "id": 1,
      "adminId": 1,
      "adminName": "Admin User",
      "entityType": "PHOTO",
      "entityId": 456,
      "action": "APPROVE_PHOTO",
      "actionResult": "SUCCESS",
      "reason": "High quality wildlife photography",
      "metadata": "{\"previousStatus\":\"PENDING\",\"newStatus\":\"APPROVED\"}",
      "ipAddress": "192.168.1.50",
      "createdAt": "2024-12-06T10:15:00Z"
    },
    {
      "id": 2,
      "adminId": 1,
      "adminName": "Admin User",
      "entityType": "USER",
      "entityId": 789,
      "action": "DEACTIVATE_USER",
      "actionResult": "SUCCESS",
      "reason": "Terms of service violation",
      "createdAt": "2024-12-06T11:30:00Z"
    }
  ]
}
```

**Use Cases:**
- Track admin activities
- Monitor moderation decisions
- Audit administrative changes
- Compliance reporting
- Detect unauthorized actions

---

## 🧪 Testing Guide

### Prerequisites

- Java 25+
- Maven 3.6+
- MySQL 8.0+
- Postman or curl
- Admin credentials

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

#### Scenario 1: Login Audit Tracking

```bash
# 1. Attempt login (creates login audit)
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "user@example.com",
    "password": "WrongPassword"
  }'
# This creates a FAILURE login audit

# 2. Successful login
ADMIN_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin@example.com",
    "password": "AdminPass123!"
  }' | jq -r '.accessToken')
# This creates a SUCCESS login audit

# 3. View login history
curl -X GET "http://localhost:8080/api/v1/audit/login?page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 4. View specific user's login history
curl -X GET "http://localhost:8080/api/v1/audit/login?userId=5&page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 2: Download Audit Tracking

```bash
# 1. User downloads photo (creates download audit automatically)
# This happens in the download service

# 2. View download history for user
curl -X GET "http://localhost:8080/api/v1/audit/download?userId=5&page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. View downloads for specific license
curl -X GET "http://localhost:8080/api/v1/audit/download?licenseId=123&page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 3: Admin Action Tracking

```bash
# 1. Admin approves photo (creates admin action audit)
curl -X PATCH "http://localhost:8080/api/v1/photos/456/approve" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
# This automatically creates an admin action audit

# 2. View admin's actions
curl -X GET "http://localhost:8080/api/v1/audit/admin?adminId=1&page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 4: Comprehensive Audit Search

```bash
# 1. Search all login audits for specific user
curl -X GET "http://localhost:8080/api/v1/audit?auditType=LOGIN_AUDIT&userId=5&page=0&size=20" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 2. Search admin actions within date range
curl -X GET "http://localhost:8080/api/v1/audit?auditType=ADMIN_ACTION_AUDIT&adminId=1&fromDate=2024-01-01T00:00:00&toDate=2024-12-31T23:59:59&page=0&size=20" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. Search all audit events for specific entity
curl -X GET "http://localhost:8080/api/v1/audit?entityId=456&page=0&size=20" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 4. Search all audits within date range
curl -X GET "http://localhost:8080/api/v1/audit?fromDate=2024-12-01T00:00:00&toDate=2024-12-06T23:59:59&page=0&size=50" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 5: Security Monitoring

```bash
# 1. Find failed login attempts
curl -X GET "http://localhost:8080/api/v1/audit/login?page=0&size=50" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  | jq '.content[] | select(.actionResult == "FAILURE")'

# 2. Monitor downloads from specific IP
curl -X GET "http://localhost:8080/api/v1/audit/download?page=0&size=100" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  | jq '.content[] | select(.ipAddress == "192.168.1.100")'

# 3. Track admin actions on specific entity
curl -X GET "http://localhost:8080/api/v1/audit?auditType=ADMIN_ACTION_AUDIT&entityId=456&page=0&size=20" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

## 📝 Best Practices

### Audit Logging

1. **Log all significant events** - Don't skip important actions
2. **Include context** - IP address, user agent, location
3. **Timestamp everything** - Use consistent timezone (UTC)
4. **Store metadata** - Additional context in JSON format
5. **Never delete audits** - Audit logs are immutable

### Security Monitoring

1. **Monitor failed logins** - Alert on multiple failures
2. **Track suspicious patterns** - Unusual download volumes
3. **Audit admin actions** - All administrative changes
4. **Detect anomalies** - Unusual times, locations, or patterns
5. **Regular reviews** - Periodic audit log analysis

### Compliance

1. **Retain audit logs** - Follow regulatory requirements
2. **Protect audit data** - Encrypt sensitive information
3. **Access control** - Limit who can view audit logs
4. **Export capabilities** - Support compliance reporting
5. **Audit trail integrity** - Prevent tampering

### Performance

1. **Index strategically** - Index frequently queried fields
2. **Archive old data** - Move old audits to cold storage
3. **Pagination required** - Always paginate audit queries
4. **Async logging** - Don't block main operations
5. **Batch processing** - Process audits in batches

### Data Privacy

1. **Anonymize when needed** - Remove PII for analytics
2. **Secure storage** - Encrypt audit data at rest
3. **Access logging** - Log who accesses audit logs
4. **Data retention** - Define and enforce retention policies
5. **GDPR compliance** - Support data deletion requests

---

## 🔒 Security Notes

### Access Control

- **Admin-only access**: All audit endpoints require ADMIN role
- **User privacy**: Users cannot view other users' audits
- **Audit immutability**: Audit records cannot be modified or deleted
- **Secure transmission**: All audit data transmitted over HTTPS

### Data Protection

- **IP addresses**: Stored for security monitoring
- **User agents**: Parsed for device/browser information
- **Geolocation**: Derived from IP for location tracking
- **Sensitive data**: Passwords never logged, payment details encrypted

### Monitoring

- **Failed login tracking**: Alert after 5 failed attempts
- **Download abuse**: Monitor excessive downloads
- **Admin action alerts**: Notify on critical admin actions
- **Anomaly detection**: Flag unusual patterns

---

## 📊 Audit Event Types

### Login Events
- LOGIN_ATTEMPT (SUCCESS/FAILURE)
- LOGOUT
- SESSION_EXPIRED
- PASSWORD_RESET_REQUESTED

### Download Events
- PHOTO_DOWNLOADED (SUCCESS/FAILURE)
- LICENSE_VERIFIED
- DOWNLOAD_LIMIT_EXCEEDED

### Admin Actions
- PHOTO_APPROVED, PHOTO_REJECTED
- USER_ACTIVATED, USER_DEACTIVATED
- PAYOUT_APPROVED, PAYOUT_REJECTED
- ORDER_REFUNDED, ORDER_CANCELLED

### Payment Events
- PAYMENT_INITIATED, PAYMENT_COMPLETED, PAYMENT_FAILED
- REFUND_INITIATED, REFUND_COMPLETED
- PAYOUT_REQUESTED, PAYOUT_COMPLETED

### User Activities
- PROFILE_UPDATED, EMAIL_CHANGED
- PASSWORD_CHANGED, PASSWORD_RESET_COMPLETED
- EMAIL_VERIFIED, ACCOUNT_ACTIVATED

---

## 📈 Analytics Use Cases

### Security Analytics
- Failed login patterns by IP/user
- Unusual login times or locations
- Brute force attack detection
- Account takeover attempts

### Business Analytics
- Download trends and patterns
- Popular photos and licenses
- User engagement metrics
- Revenue attribution

### Compliance Reporting
- Admin action audit trails
- Payment transaction history
- User data access logs
- Data retention compliance

### Performance Monitoring
- Download success rates
- Payment processing times
- System error patterns
- User experience metrics

---

## 🛠️ Maintenance

### Database Maintenance

```sql
-- Archive old audit logs (older than 1 year)
CREATE TABLE login_audits_archive AS 
SELECT * FROM login_audits 
WHERE created_at < DATE_SUB(NOW(), INTERVAL 1 YEAR);

DELETE FROM login_audits 
WHERE created_at < DATE_SUB(NOW(), INTERVAL 1 YEAR);

-- Optimize audit tables
OPTIMIZE TABLE login_audits;
OPTIMIZE TABLE download_audits;
OPTIMIZE TABLE admin_action_audits;
```

### Index Optimization

```sql
-- Check index usage
SHOW INDEX FROM login_audits;

-- Add composite indexes for common queries
CREATE INDEX idx_user_date ON login_audits(user_id, created_at);
CREATE INDEX idx_result_date ON login_audits(action_result, created_at);
```

---

## 📊 API Endpoints Summary

| Controller | Endpoints | Description |
|------------|-----------|-------------|
| AuditController | 4 | Unified audit query interface |

**Total Endpoints:** 4

**Audit Entities:** 6 (LoginAudit, DownloadAudit, AdminActionAudit, PaymentAudit, UserActivityAudit, ModerationRecord)

---

## 🔍 Query Examples

### Find Recent Failed Logins
```http
GET /api/v1/audit/login?page=0&size=50
```
Filter response for `actionResult == "FAILURE"`

### Track User Activity
```http
GET /api/v1/audit?userId=5&fromDate=2024-12-01T00:00:00&toDate=2024-12-06T23:59:59
```

### Monitor Admin Actions
```http
GET /api/v1/audit/admin?adminId=1&page=0&size=100
```

### Audit Specific Photo
```http
GET /api/v1/audit?auditType=ADMIN_ACTION_AUDIT&entityId=456
```

### Download Compliance Report
```http
GET /api/v1/audit/download?licenseId=123&page=0&size=1000
```

---

**Version:** 1.0.0  
**Last Updated:** December 2024  
**Module:** Audit & Activity Tracking  
**Platform:** Ceylon Wild Capture Backend
