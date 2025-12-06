# Admin Module - Complete API Documentation

**Ceylon Wild Capture Backend - Administrative Control & Management System**

This comprehensive documentation covers all API endpoints for the Admin Module, providing complete administrative control over users, photos, orders, payouts, analytics, and system configuration.

---

## 📋 Table of Contents

1. [Module Overview](#module-overview)
2. [User Management API](#user-management-api)
3. [Photo Moderation API](#photo-moderation-api)
4. [Order Management API](#order-management-api)
5. [Payout Review API](#payout-review-api)
6. [Category & Tag Management API](#category--tag-management-api)
7. [Dashboard Analytics API](#dashboard-analytics-api)
8. [Financial Reports API](#financial-reports-api)
9. [System Configuration API](#system-configuration-api)
10. [Audit Log API](#audit-log-api)
11. [Testing Guide](#testing-guide)
12. [Best Practices](#best-practices)

---

## 🔎 Module Overview

The Admin Module provides comprehensive administrative tools for managing all aspects of the Ceylon Wild Capture platform, from user management to financial reporting.

### Key Features

- **User Management**: Ban, activate, verify photographers, assign roles
- **Photo Moderation**: Approve, reject, flag, feature photos
- **Order Management**: Cancel, refund, resolve disputes
- **Payout Review**: Approve, reject, hold photographer payouts
- **Content Management**: Manage categories and tags
- **Analytics Dashboard**: Real-time platform statistics
- **Financial Reports**: Generate and export financial reports
- **System Configuration**: Manage platform settings
- **Audit Logging**: Track all administrative actions
- **Bulk Operations**: Perform batch updates efficiently

### Controllers

1. **UserManagementController** (`/api/v1/admin/users`) - User administration
2. **PhotoModerationController** (`/api/v1/admin/moderation`) - Photo content moderation
3. **OrderManagementController** (`/api/v1/admin/orders`) - Order administration
4. **PayoutReviewController** (`/api/v1/admin/payouts`) - Payout approval workflow
5. **CategoryManagementController** (`/api/v1/admin/categories-tags`) - Taxonomy management
6. **DashboardAnalyticsController** (`/api/v1/admin/analytics`) - Platform analytics
7. **FinancialReportController** (`/api/v1/admin/reports`) - Financial reporting
8. **SystemConfigController** (`/api/v1/admin/config`) - System configuration
9. **AuditLogController** (`/api/v1/admin/audit-logs`) - Audit trail management

**Note:** All endpoints require ADMIN role (`@PreAuthorize("hasRole('ADMIN')")`).

---

## 👥 User Management API

**Base Path:** `/api/v1/admin/users`

### User Administration

#### 1. Manage User
```http
POST /api/v1/admin/users/manage
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "userId": 5,
  "action": "UPDATE_ROLE",
  "newRole": "PHOTOGRAPHER",
  "reason": "User requested photographer access"
}
```

**Response:** `200 OK` with `User` entity

---

#### 2. Ban User
```http
POST /api/v1/admin/users/{userId}/ban?reason=Terms%20of%20service%20violation
Authorization: Bearer {token}
```

**Query Parameters:**
- `reason` (required): Reason for banning

**Response:** `200 OK` with updated `User`

---

#### 3. Unban User
```http
POST /api/v1/admin/users/{userId}/unban
Authorization: Bearer {token}
```

**Response:** `200 OK` with updated `User`

---

#### 4. Activate User
```http
POST /api/v1/admin/users/{userId}/activate
Authorization: Bearer {token}
```

**Response:** `200 OK` with updated `User`

---

#### 5. Deactivate User
```http
POST /api/v1/admin/users/{userId}/deactivate
Authorization: Bearer {token}
```

**Response:** `200 OK` with updated `User`

---

### Photographer Verification

#### 6. Verify Photographer
```http
POST /api/v1/admin/users/{userId}/verify-photographer
Authorization: Bearer {token}
```

**Response:** `200 OK` with updated `User`

**Notes:**
- Marks photographer as verified
- Enables additional features for verified photographers

---

#### 7. Unverify Photographer
```http
POST /api/v1/admin/users/{userId}/unverify-photographer
Authorization: Bearer {token}
```

**Response:** `200 OK` with updated `User`

---

### Role Management

#### 8. Change User Role
```http
PUT /api/v1/admin/users/{userId}/role?newRole=PHOTOGRAPHER
Authorization: Bearer {token}
```

**Query Parameters:**
- `newRole` (required): CUSTOMER, PHOTOGRAPHER, ADMIN

**Response:** `200 OK` with updated `User`

---

### User Retrieval

#### 9. Get All Users
```http
GET /api/v1/admin/users?role=PHOTOGRAPHER&isActive=true&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `role` (optional): Filter by user role
- `isActive` (optional): Filter by active status

**Response:** `Page<User>`

---

#### 10. Get Banned Users
```http
GET /api/v1/admin/users/banned?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<User>`

---

#### 11. Get Verified Photographers
```http
GET /api/v1/admin/users/verified-photographers?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<User>`

---

#### 12. Get Unverified Photographers
```http
GET /api/v1/admin/users/unverified-photographers?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<User>`

---

#### 13. Get User Details
```http
GET /api/v1/admin/users/{userId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `User` entity

---

#### 14. Get Inactive Users
```http
GET /api/v1/admin/users/inactive?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<User>`

---

## 📸 Photo Moderation API

**Base Path:** `/api/v1/admin/moderation`

### Moderation Actions

#### 1. Moderate Photo
```http
POST /api/v1/admin/moderation
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "photoId": 123,
  "action": "APPROVE",
  "reason": "High quality wildlife photography",
  "notes": "Featured on homepage"
}
```

**Response:** `200 OK` with `Photo` entity

---

#### 2. Approve Photo
```http
POST /api/v1/admin/moderation/{photoId}/approve
Authorization: Bearer {token}
```

**Response:** `200 OK` with approved `Photo`

---

#### 3. Reject Photo
```http
POST /api/v1/admin/moderation/{photoId}/reject?reason=Low%20quality
Authorization: Bearer {token}
```

**Query Parameters:**
- `reason` (required): Rejection reason

**Response:** `200 OK` with rejected `Photo`

---

#### 4. Flag Photo
```http
POST /api/v1/admin/moderation/{photoId}/flag?reason=Inappropriate%20content
Authorization: Bearer {token}
```

**Query Parameters:**
- `reason` (required): Flag reason

**Response:** `200 OK` with flagged `Photo`

---

#### 5. Unflag Photo
```http
POST /api/v1/admin/moderation/{photoId}/unflag
Authorization: Bearer {token}
```

**Response:** `200 OK` with unflagged `Photo`

---

### Featured Photos

#### 6. Feature Photo
```http
POST /api/v1/admin/moderation/{photoId}/feature
Authorization: Bearer {token}
```

**Response:** `200 OK` with featured `Photo`

---

#### 7. Unfeature Photo
```http
POST /api/v1/admin/moderation/{photoId}/unfeature
Authorization: Bearer {token}
```

**Response:** `200 OK` with unfeatured `Photo`

---

### Watermark Management

#### 8. Apply Watermark
```http
POST /api/v1/admin/moderation/{photoId}/watermark
Authorization: Bearer {token}
```

**Response:** `200 OK` with watermarked `Photo`

---

#### 9. Remove Watermark
```http
POST /api/v1/admin/moderation/{photoId}/remove-watermark
Authorization: Bearer {token}
```

**Response:** `200 OK` with `Photo`

---

### Photo Retrieval

#### 10. Get Pending Photos
```http
GET /api/v1/admin/moderation/pending?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Photo>`

---

#### 11. Get Flagged Photos
```http
GET /api/v1/admin/moderation/flagged?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Photo>`

---

#### 12. Get Rejected Photos
```http
GET /api/v1/admin/moderation/rejected?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Photo>`

---

#### 13. Get Featured Photos
```http
GET /api/v1/admin/moderation/featured?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Photo>`

---

#### 14. Get Photographer Photos
```http
GET /api/v1/admin/moderation/photographer/{photographerId}?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Photo>`

---

## 🛒 Order Management API

**Base Path:** `/api/v1/admin/orders`

### Order Administration

#### 1. Manage Order
```http
POST /api/v1/admin/orders/manage
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "orderId": 123,
  "action": "UPDATE_STATUS",
  "newStatus": "COMPLETED",
  "notes": "Order fulfilled"
}
```

**Response:** `200 OK` with `Order` entity

---

#### 2. Cancel Order
```http
POST /api/v1/admin/orders/{orderId}/cancel
Authorization: Bearer {token}
```

**Response:** `200 OK` with cancelled `Order`

---

### Refund Operations

#### 3. Process Full Refund
```http
POST /api/v1/admin/orders/{orderId}/refund/full?reason=Customer%20request
Authorization: Bearer {token}
```

**Query Parameters:**
- `reason` (required): Refund reason

**Response:** `200 OK` with refunded `Order`

---

#### 4. Process Partial Refund
```http
POST /api/v1/admin/orders/{orderId}/refund/partial?amount=50.00&reason=Partial%20refund
Authorization: Bearer {token}
```

**Query Parameters:**
- `amount` (required): Refund amount
- `reason` (required): Refund reason

**Response:** `200 OK` with refunded `Order`

---

### Order Status Management

#### 5. Mark as Completed
```http
POST /api/v1/admin/orders/{orderId}/complete
Authorization: Bearer {token}
```

**Response:** `200 OK` with completed `Order`

---

### Dispute Management

#### 6. Mark as Disputed
```http
POST /api/v1/admin/orders/{orderId}/dispute
Authorization: Bearer {token}
```

**Response:** `200 OK` with disputed `Order`

---

#### 7. Resolve Dispute
```http
POST /api/v1/admin/orders/{orderId}/resolve-dispute?resolution=Refund%20issued
Authorization: Bearer {token}
```

**Query Parameters:**
- `resolution` (required): Dispute resolution details

**Response:** `200 OK` with resolved `Order`

---

### Order Retrieval

#### 8. Get All Orders
```http
GET /api/v1/admin/orders?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Order>`

---

#### 9. Get Orders by Status
```http
GET /api/v1/admin/orders/status/{status}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `status`: PENDING, PROCESSING, COMPLETED, CANCELLED, REFUNDED

---

#### 10. Get Orders by Date Range
```http
GET /api/v1/admin/orders/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20
Authorization: Bearer {token}
```

---

#### 11. Get Disputed Orders
```http
GET /api/v1/admin/orders/disputed?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Order>`

---

#### 12. Get Refunded Orders
```http
GET /api/v1/admin/orders/refunded?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Order>`

---

#### 13. Get Customer Orders
```http
GET /api/v1/admin/orders/customer/{customerId}?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Order>`

---

#### 14. Get Order Details
```http
GET /api/v1/admin/orders/{orderId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `Order` entity

---

#### 15. Search Orders
```http
GET /api/v1/admin/orders/search?searchTerm=ORD-2024&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `searchTerm` (required): Search term (order number, customer name, email)

**Response:** `Page<Order>`

---

## 💰 Payout Review API

**Base Path:** `/api/v1/admin/payouts`

### Payout Review

#### 1. Review Payout
```http
POST /api/v1/admin/payouts/review
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "payoutId": 123,
  "action": "APPROVE",
  "notes": "All documentation verified"
}
```

**Response:** `200 OK` with `Payout` entity

---

#### 2. Approve Payout
```http
POST /api/v1/admin/payouts/{payoutId}/approve
Authorization: Bearer {token}
```

**Response:** `200 OK` with approved `Payout`

---

#### 3. Reject Payout
```http
POST /api/v1/admin/payouts/{payoutId}/reject?reason=Missing%20documentation
Authorization: Bearer {token}
```

**Query Parameters:**
- `reason` (required): Rejection reason

**Response:** `200 OK` with rejected `Payout`

---

#### 4. Hold Payout
```http
POST /api/v1/admin/payouts/{payoutId}/hold?reason=Under%20review
Authorization: Bearer {token}
```

**Query Parameters:**
- `reason` (required): Hold reason

**Response:** `200 OK` with held `Payout`

---

#### 5. Release Payout
```http
POST /api/v1/admin/payouts/{payoutId}/release
Authorization: Bearer {token}
```

**Response:** `200 OK` with released `Payout`

---

### Payout Retrieval

#### 6. Get Pending Payouts
```http
GET /api/v1/admin/payouts/pending?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Payout>`

---

#### 7. Get Approved Payouts
```http
GET /api/v1/admin/payouts/approved?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Payout>`

---

#### 8. Get Rejected Payouts
```http
GET /api/v1/admin/payouts/rejected?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Payout>`

---

#### 9. Get Held Payouts
```http
GET /api/v1/admin/payouts/on-hold?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Payout>`

---

#### 10. Get Photographer Payouts
```http
GET /api/v1/admin/payouts/photographer/{photographerId}?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Payout>`

---

#### 11. Get Payouts Above Threshold
```http
GET /api/v1/admin/payouts/threshold?threshold=1000.00&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `threshold` (required): Minimum payout amount

**Response:** `Page<Payout>`

---

#### 12. Get Payout Details
```http
GET /api/v1/admin/payouts/{payoutId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `Payout` entity

---

## 🏷️ Category & Tag Management API

**Base Path:** `/api/v1/admin/categories-tags`

### Category Management

#### 1. Create Category
```http
POST /api/v1/admin/categories-tags/categories
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "Wildlife",
  "slug": "wildlife",
  "description": "Wildlife photography",
  "imageUrl": "https://example.com/wildlife.jpg",
  "displayOrder": 1,
  "isActive": true
}
```

**Response:** `200 OK` with created `Category`

---

#### 2. Update Category
```http
PUT /api/v1/admin/categories-tags/categories/{categoryId}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:** `Category` entity

**Response:** `200 OK` with updated `Category`

---

#### 3. Delete Category
```http
DELETE /api/v1/admin/categories-tags/categories/{categoryId}
Authorization: Bearer {token}
```

**Response:** `204 No Content`

---

#### 4. Get All Categories
```http
GET /api/v1/admin/categories-tags/categories?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Category>`

---

#### 5. Merge Categories
```http
POST /api/v1/admin/categories-tags/categories/merge?sourceCategoryId=1&targetCategoryId=2
Authorization: Bearer {token}
```

**Query Parameters:**
- `sourceCategoryId` (required): Source category to merge from
- `targetCategoryId` (required): Target category to merge into

**Response:** `200 OK` with merged `Category`

---

#### 6. Get Unused Categories
```http
GET /api/v1/admin/categories-tags/categories/unused
Authorization: Bearer {token}
```

**Response:** `List<Category>`

---

#### 7. Delete Unused Categories
```http
DELETE /api/v1/admin/categories-tags/categories/unused
Authorization: Bearer {token}
```

**Response:** `200 OK` with count of deleted categories

---

### Tag Management

#### 8. Create Tag
```http
POST /api/v1/admin/categories-tags/tags
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "leopard",
  "description": "Sri Lankan Leopard"
}
```

**Response:** `200 OK` with created `Tag`

---

#### 9. Update Tag
```http
PUT /api/v1/admin/categories-tags/tags/{tagId}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:** `Tag` entity

**Response:** `200 OK` with updated `Tag`

---

#### 10. Delete Tag
```http
DELETE /api/v1/admin/categories-tags/tags/{tagId}
Authorization: Bearer {token}
```

**Response:** `204 No Content`

---

#### 11. Get All Tags
```http
GET /api/v1/admin/categories-tags/tags?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<Tag>`

---

#### 12. Merge Tags
```http
POST /api/v1/admin/categories-tags/tags/merge?sourceTagId=1&targetTagId=2
Authorization: Bearer {token}
```

**Query Parameters:**
- `sourceTagId` (required): Source tag to merge from
- `targetTagId` (required): Target tag to merge into

**Response:** `200 OK` with merged `Tag`

---

#### 13. Get Unused Tags
```http
GET /api/v1/admin/categories-tags/tags/unused
Authorization: Bearer {token}
```

**Response:** `List<Tag>`

---

#### 14. Delete Unused Tags
```http
DELETE /api/v1/admin/categories-tags/tags/unused
Authorization: Bearer {token}
```

**Response:** `200 OK` with count of deleted tags

---

## 📊 Dashboard Analytics API

**Base Path:** `/api/v1/admin/analytics`

### Analytics Endpoints

#### 1. Get Dashboard Analytics
```http
GET /api/v1/admin/analytics/dashboard?startDate=2024-01-01&endDate=2024-12-31
Authorization: Bearer {token}
```

**Query Parameters:**
- `startDate` (required): Start date (ISO date format)
- `endDate` (required): End date (ISO date format)

**Response:** `DashboardAnalyticsDto`

**Example Response:**
```json
{
  "totalUsers": 1547,
  "totalPhotographers": 342,
  "totalPhotos": 15234,
  "totalOrders": 8765,
  "totalRevenue": 125000.50,
  "totalPayouts": 87500.35,
  "platformCommission": 37500.15,
  "period": {
    "startDate": "2024-01-01",
    "endDate": "2024-12-31"
  }
}
```

---

#### 2. Get User Statistics
```http
GET /api/v1/admin/analytics/user-statistics
Authorization: Bearer {token}
```

**Response:** `Map<String, Object>`

---

#### 3. Get Sales Statistics
```http
GET /api/v1/admin/analytics/sales-statistics?startDate=2024-01-01&endDate=2024-12-31
Authorization: Bearer {token}
```

**Response:** `Map<String, Object>`

---

#### 4. Get Photo Statistics
```http
GET /api/v1/admin/analytics/photo-statistics
Authorization: Bearer {token}
```

**Response:** `Map<String, Object>`

---

#### 5. Get Revenue Statistics
```http
GET /api/v1/admin/analytics/revenue-statistics?startDate=2024-01-01&endDate=2024-12-31
Authorization: Bearer {token}
```

**Response:** `Map<String, Object>`

---

#### 6. Get Payout Statistics
```http
GET /api/v1/admin/analytics/payout-statistics?startDate=2024-01-01&endDate=2024-12-31
Authorization: Bearer {token}
```

**Response:** `Map<String, Object>`

---

#### 7. Get Growth Metrics
```http
GET /api/v1/admin/analytics/growth-metrics?startDate=2024-01-01&endDate=2024-12-31
Authorization: Bearer {token}
```

**Response:** `Map<String, Object>`

---

#### 8. Get Daily Trends
```http
GET /api/v1/admin/analytics/daily-trends?startDate=2024-01-01&endDate=2024-12-31
Authorization: Bearer {token}
```

**Response:** `Map<String, Object>`

---

#### 9. Get Category Performance
```http
GET /api/v1/admin/analytics/category-performance?startDate=2024-01-01&endDate=2024-12-31
Authorization: Bearer {token}
```

**Response:** `Map<String, Object>`

---

## 📈 Financial Reports API

**Base Path:** `/api/v1/admin/reports`

### Report Generation

#### 1. Generate Financial Report
```http
GET /api/v1/admin/reports/financial?startDate=2024-01-01&endDate=2024-12-31
Authorization: Bearer {token}
```

**Query Parameters:**
- `startDate` (required): Start date (ISO date format)
- `endDate` (required): End date (ISO date format)

**Response:** `FinancialReportDto`

---

#### 2. Generate Monthly Report
```http
GET /api/v1/admin/reports/financial/monthly?year=2024&month=12
Authorization: Bearer {token}
```

**Query Parameters:**
- `year` (required): Year
- `month` (required): Month (1-12)

**Response:** `FinancialReportDto`

---

#### 3. Generate Yearly Report
```http
GET /api/v1/admin/reports/financial/yearly?year=2024
Authorization: Bearer {token}
```

**Query Parameters:**
- `year` (required): Year

**Response:** `FinancialReportDto`

---

### Report Export

#### 4. Export to PDF
```http
POST /api/v1/admin/reports/financial/export/pdf
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:** `FinancialReportDto`

**Response:** PDF file download

**Headers:**
```
Content-Type: application/pdf
Content-Disposition: attachment; filename="financial-report.pdf"
```

---

#### 5. Export to Excel
```http
POST /api/v1/admin/reports/financial/export/excel
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:** `FinancialReportDto`

**Response:** Excel file download

**Headers:**
```
Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
Content-Disposition: attachment; filename="financial-report.xlsx"
```

---

#### 6. Export to CSV
```http
POST /api/v1/admin/reports/financial/export/csv
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:** `FinancialReportDto`

**Response:** CSV file download

**Headers:**
```
Content-Type: text/csv
Content-Disposition: attachment; filename="financial-report.csv"
```

---

## ⚙️ System Configuration API

**Base Path:** `/api/v1/admin/config`

### Configuration Management

#### 1. Save Configuration
```http
POST /api/v1/admin/config
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "key": "platform.commission.rate",
  "value": "15",
  "category": "FINANCIAL",
  "description": "Platform commission percentage",
  "isPublic": false
}
```

**Response:** `200 OK` with `SystemConfigDto`

---

#### 2. Get Configuration by Key
```http
GET /api/v1/admin/config/{key}
Authorization: Bearer {token}
```

**Example:**
```http
GET /api/v1/admin/config/platform.commission.rate
Authorization: Bearer {token}
```

**Response:** `200 OK` with `SystemConfigDto` or `404 Not Found`

---

#### 3. Get Configuration Value
```http
GET /api/v1/admin/config/{key}/value
Authorization: Bearer {token}
```

**Response:** `200 OK` with string value

---

#### 4. Get All Configurations
```http
GET /api/v1/admin/config
Authorization: Bearer {token}
```

**Response:** `List<SystemConfigDto>`

---

#### 5. Get Public Configurations
```http
GET /api/v1/admin/config/public
Authorization: Bearer {token}
```

**Response:** `List<SystemConfigDto>`

---

#### 6. Get Configurations by Category
```http
GET /api/v1/admin/config/category/{category}
Authorization: Bearer {token}
```

**Path Parameters:**
- `category`: FINANCIAL, SECURITY, FEATURES, etc.

**Response:** `List<SystemConfigDto>`

---

#### 7. Get All Categories
```http
GET /api/v1/admin/config/categories
Authorization: Bearer {token}
```

**Response:** `List<String>`

---

#### 8. Delete Configuration
```http
DELETE /api/v1/admin/config/{key}
Authorization: Bearer {token}
```

**Response:** `204 No Content`

---

#### 9. Bulk Update Configurations
```http
PUT /api/v1/admin/config/bulk
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "platform.commission.rate": "15",
  "platform.min.payout": "50",
  "platform.max.payout": "10000"
}
```

**Response:** `200 OK`

---

#### 10. Reset to Default
```http
POST /api/v1/admin/config/{key}/reset
Authorization: Bearer {token}
```

**Response:** `200 OK` with reset `SystemConfigDto`

---

## 📝 Audit Log API

**Base Path:** `/api/v1/admin/audit-logs`

### Audit Log Retrieval

#### 1. Get All Audit Logs
```http
GET /api/v1/admin/audit-logs?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<AuditLogDto>`

---

#### 2. Get Audit Logs by Event Type
```http
GET /api/v1/admin/audit-logs/event-type/{eventType}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `eventType`: USER_CREATED, PHOTO_APPROVED, ORDER_REFUNDED, etc.

---

#### 3. Get Audit Logs by Entity Type
```http
GET /api/v1/admin/audit-logs/entity-type/{entityType}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `entityType`: USER, PHOTO, ORDER, PAYOUT, etc.

---

#### 4. Get Audit Logs by User
```http
GET /api/v1/admin/audit-logs/user/{userId}?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<AuditLogDto>`

---

#### 5. Get Audit Logs by Date Range
```http
GET /api/v1/admin/audit-logs/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<AuditLogDto>`

---

#### 6. Get Audit Logs for Entity
```http
GET /api/v1/admin/audit-logs/entity/{entityType}/{entityId}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `entityType`: Entity type
- `entityId`: Entity ID

**Response:** `Page<AuditLogDto>`

---

#### 7. Get Download Audit Logs
```http
GET /api/v1/admin/audit-logs/downloads?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<AuditLogDto>`

---

#### 8. Get Login Audit Logs
```http
GET /api/v1/admin/audit-logs/logins?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<AuditLogDto>`

---

#### 9. Get Failed Login Attempts
```http
GET /api/v1/admin/audit-logs/failed-logins?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<AuditLogDto>`

---

#### 10. Get Audit Logs by Action
```http
GET /api/v1/admin/audit-logs/action/{action}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `action`: APPROVE, REJECT, BAN, REFUND, etc.

---

#### 11. Search Audit Logs
```http
GET /api/v1/admin/audit-logs/search?searchTerm=user@example.com&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `searchTerm` (required): Search term

**Response:** `Page<AuditLogDto>`

---

#### 12. Delete Old Audit Logs
```http
DELETE /api/v1/admin/audit-logs/cleanup?olderThan=2023-01-01T00:00:00
Authorization: Bearer {token}
```

**Query Parameters:**
- `olderThan` (required): Delete logs older than this date

**Response:** `200 OK` with count of deleted logs

---

## 🧪 Testing Guide

### Prerequisites

- Java 25+
- Maven 3.6+
- MySQL 8.0+
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

#### Scenario 1: User Management

```bash
# 1. Login as admin
ADMIN_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin@example.com",
    "password": "AdminPass123!"
  }' | jq -r '.accessToken')

# 2. Get all users
curl -X GET "http://localhost:8080/api/v1/admin/users?page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. Verify photographer
curl -X POST "http://localhost:8080/api/v1/admin/users/5/verify-photographer" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 4. Ban user
curl -X POST "http://localhost:8080/api/v1/admin/users/10/ban?reason=Spam" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 5. Get banned users
curl -X GET "http://localhost:8080/api/v1/admin/users/banned?page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 2: Photo Moderation

```bash
# 1. Get pending photos
curl -X GET "http://localhost:8080/api/v1/admin/moderation/pending?page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 2. Approve photo
curl -X POST "http://localhost:8080/api/v1/admin/moderation/123/approve" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. Reject photo
curl -X POST "http://localhost:8080/api/v1/admin/moderation/456/reject?reason=Low%20quality" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 4. Feature photo
curl -X POST "http://localhost:8080/api/v1/admin/moderation/123/feature" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 5. Get featured photos
curl -X GET "http://localhost:8080/api/v1/admin/moderation/featured?page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 3: Order Management

```bash
# 1. Get all orders
curl -X GET "http://localhost:8080/api/v1/admin/orders?page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 2. Get disputed orders
curl -X GET "http://localhost:8080/api/v1/admin/orders/disputed?page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. Resolve dispute
curl -X POST "http://localhost:8080/api/v1/admin/orders/123/resolve-dispute?resolution=Refund%20issued" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 4. Process full refund
curl -X POST "http://localhost:8080/api/v1/admin/orders/456/refund/full?reason=Customer%20request" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 4: Payout Review

```bash
# 1. Get pending payouts
curl -X GET "http://localhost:8080/api/v1/admin/payouts/pending?page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 2. Approve payout
curl -X POST "http://localhost:8080/api/v1/admin/payouts/123/approve" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. Reject payout
curl -X POST "http://localhost:8080/api/v1/admin/payouts/456/reject?reason=Missing%20docs" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 4. Get payouts above threshold
curl -X GET "http://localhost:8080/api/v1/admin/payouts/threshold?threshold=1000.00&page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 5: Analytics & Reports

```bash
# 1. Get dashboard analytics
curl -X GET "http://localhost:8080/api/v1/admin/analytics/dashboard?startDate=2024-01-01&endDate=2024-12-31" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 2. Get sales statistics
curl -X GET "http://localhost:8080/api/v1/admin/analytics/sales-statistics?startDate=2024-01-01&endDate=2024-12-31" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. Generate monthly report
curl -X GET "http://localhost:8080/api/v1/admin/reports/financial/monthly?year=2024&month=12" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 4. Export to PDF (save report first, then export)
curl -X POST "http://localhost:8080/api/v1/admin/reports/financial/export/pdf" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d @report.json \
  --output financial-report.pdf
```

---

## 📝 Best Practices

### User Management

1. **Document actions** - Always provide clear reasons for bans/deactivations
2. **Verify photographers carefully** - Check portfolio and credentials
3. **Monitor banned users** - Regularly review ban list
4. **Role changes** - Log all role modifications
5. **Inactive users** - Periodically review and clean up

### Photo Moderation

1. **Timely review** - Process pending photos within 24-48 hours
2. **Clear feedback** - Provide specific rejection reasons
3. **Quality standards** - Maintain consistent moderation criteria
4. **Featured content** - Regularly update featured photos
5. **Flag review** - Investigate flagged content promptly

### Order Management

1. **Dispute resolution** - Handle disputes within 48 hours
2. **Refund processing** - Process refunds promptly
3. **Documentation** - Keep detailed notes on all actions
4. **Customer communication** - Notify customers of status changes
5. **Fraud detection** - Monitor for suspicious patterns

### Payout Review

1. **Verification** - Verify bank details before approval
2. **Threshold monitoring** - Review large payouts carefully
3. **Hold justification** - Document reasons for holds
4. **Timely processing** - Approve eligible payouts within 3-5 days
5. **Audit trail** - Maintain complete payout history

### Analytics & Reporting

1. **Regular monitoring** - Review dashboard daily
2. **Trend analysis** - Identify patterns and anomalies
3. **Export reports** - Archive monthly/yearly reports
4. **Performance metrics** - Track KPIs consistently
5. **Data accuracy** - Verify report data periodically

### System Configuration

1. **Change management** - Document all config changes
2. **Testing** - Test config changes in staging first
3. **Backup** - Keep backups of critical configurations
4. **Access control** - Limit config access to senior admins
5. **Audit logging** - Log all configuration changes

---

## 🔒 Security Notes

### Access Control

- **Admin-only access**: All endpoints require ADMIN role
- **Action logging**: All admin actions are logged in audit trail
- **IP tracking**: Admin IP addresses recorded for security
- **Session management**: Admin sessions have shorter timeouts

### Data Protection

- **Sensitive data**: Encrypt sensitive configuration values
- **Audit retention**: Maintain audit logs for compliance
- **Export security**: Secure report exports with encryption
- **Access logs**: Monitor admin access patterns

### Best Security Practices

1. **Strong authentication** - Enforce MFA for admin accounts
2. **Regular audits** - Review admin actions weekly
3. **Principle of least privilege** - Grant minimal necessary permissions
4. **Secure communications** - Use HTTPS for all admin operations
5. **Incident response** - Have procedures for security incidents

---

## 📊 API Endpoints Summary

| Controller | Endpoints | Description |
|------------|-----------|-------------|
| UserManagementController | 14 | User administration and verification |
| PhotoModerationController | 14 | Photo content moderation |
| OrderManagementController | 15 | Order administration and refunds |
| PayoutReviewController | 12 | Payout approval workflow |
| CategoryManagementController | 14 | Category and tag management |
| DashboardAnalyticsController | 9 | Platform analytics |
| FinancialReportController | 6 | Financial reporting and export |
| SystemConfigController | 10 | System configuration |
| AuditLogController | 12 | Audit trail management |

**Total Endpoints:** 106

---

**Version:** 1.0.0  
**Last Updated:** December 2024  
**Module:** Administrative Control & Management  
**Platform:** Ceylon Wild Capture Backend
