# Order Module - Complete API Documentation

**Ceylon Wild Capture Backend - Order & License Management System**

This comprehensive documentation covers all API endpoints for the Order Module, including order management, order items, licenses, and analytics.

---

## 📋 Table of Contents

1. [Module Overview](#module-overview)
2. [Data Models](#data-models)
3. [Order Management API](#order-management-api)
4. [Order Query API](#order-query-api)
5. [Order Item API](#order-item-api)
6. [License Management API](#license-management-api)
7. [Testing Guide](#testing-guide)
8. [Best Practices](#best-practices)

---

## 🔎 Module Overview

The Order Module manages the complete order lifecycle, from creation to fulfillment, including license generation and download authorization for the Ceylon Wild Capture platform.

### Key Features

- **Order Creation**: Create orders with multiple photo items
- **Order Management**: Track order status through complete lifecycle
- **License Generation**: Automatic license key generation upon payment
- **Download Authorization**: Verify license ownership for photo downloads
- **Order Analytics**: Comprehensive statistics and reporting
- **Payment Integration**: Seamless integration with Payment Module
- **Multi-License Support**: Base, Commercial, Editorial, Extended licenses
- **Order Search**: Advanced filtering and search capabilities
- **Buyer Statistics**: Track customer purchase history and spending

### Controllers

1. **OrderController** (`/api/v1/orders`) - Core order CRUD operations
2. **OrderQueryController** (`/api/v1/orders/query`) - Analytics and reporting
3. **OrderItemController** (`/api/v1/order-items`) - Order item management
4. **LicenseController** (`/api/v1/licenses`) - License management and verification

### Order Status Flow

```
PENDING → PROCESSING → COMPLETED
    ↓           ↓
CANCELLED   REFUNDED (from COMPLETED)
```

### License Types

- **BASE**: Standard personal use license
- **COMMERCIAL**: Commercial use license
- **EDITORIAL**: Editorial/news use license
- **EXTENDED**: Extended commercial rights

---

## 🗂️ Data Models

### Order Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `orderNumber` | String | Unique, max 50 chars, required | Unique order number (e.g., ORD-2024-001234) |
| `buyer` | User | Required | Customer who placed the order |
| `totalAmount` | BigDecimal | Required, precision 12,2 | Total order amount |
| `subtotal` | BigDecimal | Required, precision 12,2 | Subtotal before tax/discount |
| `taxAmount` | BigDecimal | Precision 10,2, Default: 0 | Tax amount |
| `discountAmount` | BigDecimal | Precision 10,2, Default: 0 | Discount amount |
| `couponCode` | String | Max 50 chars | Applied coupon code |
| `status` | OrderStatus | Required, Default: PENDING | Order status |
| `paymentMethod` | String | Max 50 chars | Payment method used |
| `paymentId` | String | Max 100 chars | Payment gateway ID |
| `transactionId` | String | Max 100 chars | Transaction ID |
| `billingName` | String | Max 100 chars | Billing name |
| `billingEmail` | String | Max 100 chars | Billing email |
| `billingAddress` | String | TEXT | Billing street address |
| `billingCity` | String | Max 100 chars | Billing city |
| `billingState` | String | Max 100 chars | Billing state/province |
| `billingCountry` | String | Max 100 chars | Billing country |
| `billingZip` | String | Max 20 chars | Billing ZIP/postal code |
| `ipAddress` | String | Max 45 chars | Customer IP address |
| `notes` | String | TEXT | Order notes |
| `completedAt` | LocalDateTime | - | Order completion timestamp |
| `cancelledAt` | LocalDateTime | - | Cancellation timestamp |
| `refundedAt` | LocalDateTime | - | Refund timestamp |
| `createdAt` | LocalDateTime | Auto-generated | Order creation timestamp |
| `updatedAt` | LocalDateTime | Auto-updated | Last update timestamp |

**Relationships:**
- One-to-Many: `OrderItems` (photos in the order)
- One-to-One: `Payment` (payment details)

---

### OrderItem Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `order` | Order | Required | Parent order |
| `photo` | Photo | Required | Purchased photo |
| `licenseType` | LicenseType | Required | Type of license purchased |
| `price` | BigDecimal | Required, precision 10,2 | Item price |
| `discount` | BigDecimal | Precision 10,2, Default: 0 | Item discount |
| `finalPrice` | BigDecimal | Required, precision 10,2 | Final price after discount |
| `photographerEarnings` | BigDecimal | Precision 10,2 | Photographer's share |
| `platformCommission` | BigDecimal | Precision 10,2 | Platform commission |
| `createdAt` | LocalDateTime | Auto-generated | Creation timestamp |

**Relationships:**
- One-to-One: `License` (generated license)

---

### License Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `licenseKey` | String | Unique, max 100 chars, required | Unique license key |
| `orderItem` | OrderItem | Required, unique | Associated order item |
| `licenseType` | LicenseType | Required | License type |
| `issuedTo` | String | Max 200 chars | Licensee name |
| `issuedEmail` | String | Max 100 chars | Licensee email |
| `downloadLimit` | Integer | - | Maximum downloads allowed |
| `downloadCount` | Integer | Default: 0 | Current download count |
| `isActive` | Boolean | Default: true | License active status |
| `expiresAt` | LocalDateTime | - | License expiration date |
| `firstDownloadedAt` | LocalDateTime | - | First download timestamp |
| `lastDownloadedAt` | LocalDateTime | - | Last download timestamp |
| `terms` | String | TEXT | License terms and conditions |
| `createdAt` | LocalDateTime | Auto-generated | License creation timestamp |
| `updatedAt` | LocalDateTime | Auto-updated | Last update timestamp |

**Relationships:**
- One-to-Many: `DownloadAudits` (download history)

---

## 🛒 Order Management API

**Base Path:** `/api/v1/orders`

### Order Creation

#### 1. Create Order
```http
POST /api/v1/orders
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "items": [
    {
      "photoId": 123,
      "licenseType": "COMMERCIAL",
      "price": 150.00
    },
    {
      "photoId": 456,
      "licenseType": "BASE",
      "price": 50.00
    }
  ],
  "billingInfo": {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "address": "123 Main St",
    "city": "Colombo",
    "state": "Western Province",
    "country": "Sri Lanka",
    "zip": "00100"
  },
  "couponCode": "SUMMER2024"
}
```

**Response:** `201 Created`
```json
{
  "id": 1,
  "orderNumber": "ORD-2024-001234",
  "buyerId": 5,
  "totalAmount": 200.00,
  "subtotal": 200.00,
  "taxAmount": 0.00,
  "discountAmount": 0.00,
  "status": "PENDING",
  "itemCount": 2,
  "createdAt": "2024-12-06T13:44:41Z"
}
```

**Notes:**
- Requires CUSTOMER or PHOTOGRAPHER role
- Automatically generates unique order number
- Calculates totals including tax and discounts
- Creates order items and reserves photos

---

### Order Retrieval

#### 2. Get Order by ID
```http
GET /api/v1/orders/{orderId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `OrderResponseDto` or `404 Not Found`

**Notes:**
- Users can only view their own orders
- Admins can view any order

---

#### 3. Get Order by Order Number
```http
GET /api/v1/orders/number/{orderNumber}
Authorization: Bearer {token}
```

**Example:**
```http
GET /api/v1/orders/number/ORD-2024-001234
Authorization: Bearer {token}
```

**Response:** `200 OK` with `OrderResponseDto`

---

#### 4. Get My Orders
```http
GET /api/v1/orders/my-orders?page=0&size=20&sort=createdAt,desc
Authorization: Bearer {token}
```

**Response:** `Page<OrderSummaryDto>`

**Notes:**
- Returns current user's orders
- Supports pagination and sorting

---

#### 5. Get My Orders by Status
```http
GET /api/v1/orders/my-orders/status/{status}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `status`: PENDING, PROCESSING, COMPLETED, CANCELLED, REFUNDED

**Example:**
```http
GET /api/v1/orders/my-orders/status/COMPLETED?page=0&size=20
Authorization: Bearer {token}
```

---

#### 6. Get My Completed Orders
```http
GET /api/v1/orders/my-orders/completed?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<OrderResponseDto>`

---

#### 7. Get My Orders by Date Range
```http
GET /api/v1/orders/my-orders/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `startDate` (required): Start date in ISO format
- `endDate` (required): End date in ISO format

---

### Order Management

#### 8. Update Order Status (Admin)
```http
PUT /api/v1/orders/{orderId}/status
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "status": "COMPLETED",
  "notes": "Order fulfilled successfully"
}
```

**Response:** `200 OK` with updated `OrderResponseDto`

**Notes:**
- Admin-only operation
- Updates order status and timestamps
- May trigger license generation

---

#### 9. Cancel Order
```http
POST /api/v1/orders/{orderId}/cancel
Authorization: Bearer {token}
```

**Response:** `200 OK` with updated `OrderResponseDto`

**Notes:**
- Can only cancel PENDING or PROCESSING orders
- User can cancel their own orders
- Admin can cancel any order
- Releases reserved photos

---

### Payment Callbacks

#### 10. Process Payment Success
```http
POST /api/v1/orders/{orderId}/payment-success?paymentId=pi_3abc123xyz&transactionId=txn_abc123
Authorization: Bearer {token}
```

**Query Parameters:**
- `paymentId` (required): Payment gateway payment ID
- `transactionId` (required): Transaction ID

**Response:** `200 OK` with updated `OrderResponseDto`

**Notes:**
- Updates order status to COMPLETED
- Generates licenses for all order items
- Triggers fulfillment process

---

#### 11. Process Payment Failure
```http
POST /api/v1/orders/{orderId}/payment-failure?reason=Card%20declined
Authorization: Bearer {token}
```

**Query Parameters:**
- `reason` (required): Failure reason

**Response:** `200 OK` with updated `OrderResponseDto`

**Notes:**
- Updates order status to CANCELLED
- Records failure reason

---

### Search & Validation

#### 12. Search Orders (Admin)
```http
GET /api/v1/orders/search?orderNumber=ORD-2024&status=COMPLETED&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `orderNumber` (optional): Order number filter
- `status` (optional): Order status filter
- `buyerId` (optional): Buyer ID filter
- `minAmount` (optional): Minimum amount filter
- `maxAmount` (optional): Maximum amount filter

**Response:** `Page<OrderResponseDto>`

---

#### 13. Validate Order Before Payment
```http
GET /api/v1/orders/{orderId}/validate
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
true
```

**Notes:**
- Validates order is ready for payment
- Checks order status, items, and amounts

---

### Statistics

#### 14. Get My Order Count
```http
GET /api/v1/orders/my-orders/count
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
42
```

---

## 📊 Order Query API

**Base Path:** `/api/v1/orders/query`

**Note:** All endpoints require ADMIN role

### Order Retrieval

#### 1. Get All Orders
```http
GET /api/v1/orders/query/all?page=0&size=20&sort=createdAt,desc
Authorization: Bearer {token}
```

**Response:** `Page<OrderSummaryDto>`

---

#### 2. Get Orders by Status
```http
GET /api/v1/orders/query/status/{status}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `status`: PENDING, PROCESSING, COMPLETED, CANCELLED, REFUNDED

---

#### 3. Get Orders by Buyer
```http
GET /api/v1/orders/query/buyer/{buyerId}?page=0&size=20
Authorization: Bearer {token}
```

---

#### 4. Get Orders by Photographer
```http
GET /api/v1/orders/query/photographer/{photographerId}?page=0&size=20
Authorization: Bearer {token}
```

**Notes:**
- Returns orders containing photos by specific photographer
- Useful for photographer earnings reports

---

#### 5. Get Orders by Photo
```http
GET /api/v1/orders/query/photo/{photoId}?page=0&size=20
Authorization: Bearer {token}
```

**Notes:**
- Returns all orders containing a specific photo
- Useful for photo sales analytics

---

#### 6. Get Orders by Date Range
```http
GET /api/v1/orders/query/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20
Authorization: Bearer {token}
```

---

#### 7. Search Orders with Criteria
```http
GET /api/v1/orders/query/search?orderNumber=ORD-2024&status=COMPLETED&buyerId=5&page=0&size=20
Authorization: Bearer {token}
```

---

#### 8. Get Recent Orders
```http
GET /api/v1/orders/query/recent?page=0&size=20
Authorization: Bearer {token}
```

**Notes:**
- Returns most recent orders
- Ordered by creation date (descending)

---

### Statistics & Analytics

#### 9. Get Order Statistics
```http
GET /api/v1/orders/query/statistics
Authorization: Bearer {token}
```

**Response:**
```json
{
  "totalOrders": 1547,
  "pendingOrders": 23,
  "processingOrders": 5,
  "completedOrders": 1500,
  "cancelledOrders": 15,
  "refundedOrders": 4,
  "totalRevenue": 125000.50,
  "averageOrderValue": 80.85,
  "totalItems": 3094
}
```

---

#### 10. Get Total Sales
```http
GET /api/v1/orders/query/total-sales
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
125000.50
```

---

#### 11. Get Buyer Statistics
```http
GET /api/v1/orders/query/buyer/{buyerId}/statistics
Authorization: Bearer {token}
```

**Response:**
```json
{
  "buyerId": 5,
  "totalOrders": 42,
  "completedOrders": 40,
  "cancelledOrders": 2,
  "totalSpent": 3500.00,
  "averageOrderValue": 83.33,
  "firstOrderDate": "2024-01-15T10:30:00Z",
  "lastOrderDate": "2024-12-05T14:20:00Z"
}
```

---

#### 12. Get Top Buyers by Order Count
```http
GET /api/v1/orders/query/top-buyers/by-orders?page=0&size=10
Authorization: Bearer {token}
```

**Response:** `Page<Map<String, Object>>`

**Example Response:**
```json
{
  "content": [
    {
      "buyerId": 5,
      "buyerName": "John Doe",
      "buyerEmail": "john.doe@example.com",
      "orderCount": 42,
      "totalSpent": 3500.00
    }
  ]
}
```

---

#### 13. Get Top Buyers by Spending
```http
GET /api/v1/orders/query/top-buyers/by-spending?page=0&size=10
Authorization: Bearer {token}
```

---

### Order Counts

#### 14. Count Orders by Status
```http
GET /api/v1/orders/query/count/status/{status}
Authorization: Bearer {token}
```

**Example:**
```http
GET /api/v1/orders/query/count/status/COMPLETED
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
1500
```

---

#### 15. Count Total Orders
```http
GET /api/v1/orders/query/count/total
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
1547
```

---

## 📦 Order Item API

**Base Path:** `/api/v1/order-items`

### Item Management

#### 1. Add Item to Order
```http
POST /api/v1/order-items
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "orderId": 123,
  "photoId": 456,
  "licenseType": "COMMERCIAL",
  "price": 150.00
}
```

**Response:** `201 Created` with updated `OrderResponseDto`

**Notes:**
- Can only add items to PENDING orders
- Recalculates order totals
- User must own the order

---

#### 2. Remove Item from Order
```http
DELETE /api/v1/order-items/{orderItemId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with updated `OrderResponseDto`

**Notes:**
- Can only remove from PENDING orders
- Recalculates order totals
- Deletes associated license if exists

---

### Item Retrieval

#### 3. Get Order Item by ID
```http
GET /api/v1/order-items/{orderItemId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `OrderItemResponseDto`

---

#### 4. Get All Items for Order
```http
GET /api/v1/order-items/order/{orderId}
Authorization: Bearer {token}
```

**Response:** `List<OrderItemResponseDto>`

---

#### 5. Get Items by Photo (Admin)
```http
GET /api/v1/order-items/photo/{photoId}
Authorization: Bearer {token}
```

**Response:** `List<OrderItemResponseDto>`

**Notes:**
- Admin-only operation
- Returns all order items for a specific photo

---

#### 6. Get Order Item Count
```http
GET /api/v1/order-items/order/{orderId}/count
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
3
```

---

## 🔑 License Management API

**Base Path:** `/api/v1/licenses`

### License Retrieval

#### 1. Get License by ID
```http
GET /api/v1/licenses/{licenseId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `LicenseResponseDto`

**Notes:**
- Users can only view their own licenses
- Admins can view any license

---

#### 2. Get License by License Key
```http
GET /api/v1/licenses/key/{licenseKey}
Authorization: Bearer {token}
```

**Example:**
```http
GET /api/v1/licenses/key/LIC-2024-ABC123XYZ
Authorization: Bearer {token}
```

**Response:** `200 OK` with `LicenseResponseDto`

---

#### 3. Get My Licenses
```http
GET /api/v1/licenses/my-licenses?page=0&size=20&sort=createdAt,desc
Authorization: Bearer {token}
```

**Response:** `Page<LicenseResponseDto>`

---

#### 4. Get My Active Licenses
```http
GET /api/v1/licenses/my-licenses/active?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<LicenseResponseDto>`

**Notes:**
- Returns licenses where `isActive = true`
- Excludes expired licenses

---

#### 5. Get My Licenses for Photo
```http
GET /api/v1/licenses/my-licenses/photo/{photoId}
Authorization: Bearer {token}
```

**Response:** `List<LicenseResponseDto>`

**Notes:**
- Returns all licenses user owns for a specific photo
- Useful for checking if user can download photo

---

### License Verification

#### 6. Verify License Ownership
```http
POST /api/v1/licenses/verify
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "licenseKey": "LIC-2024-ABC123XYZ",
  "photoId": 456
}
```

**Response:** `200 OK`
```json
{
  "valid": true,
  "licenseKey": "LIC-2024-ABC123XYZ",
  "photoId": 456,
  "licenseType": "COMMERCIAL",
  "isActive": true,
  "downloadLimit": null,
  "downloadCount": 5,
  "expiresAt": null,
  "canDownload": true,
  "message": "License is valid and active"
}
```

**Notes:**
- Verifies license key matches photo
- Checks license is active and not expired
- Validates download limits

---

#### 7. Validate License for Download
```http
GET /api/v1/licenses/validate-download?licenseKey=LIC-2024-ABC123XYZ&photoId=456
Authorization: Bearer {token}
```

**Query Parameters:**
- `licenseKey` (required): License key
- `photoId` (required): Photo ID

**Response:** `200 OK` with `LicenseVerificationResultDto`

---

#### 8. Check Photo Purchase
```http
GET /api/v1/licenses/check-purchase/{photoId}
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
{
  "photoId": 456,
  "purchased": true,
  "licenseCount": 2,
  "licenses": [
    {
      "licenseKey": "LIC-2024-ABC123XYZ",
      "licenseType": "COMMERCIAL",
      "isActive": true
    },
    {
      "licenseKey": "LIC-2024-DEF456UVW",
      "licenseType": "BASE",
      "isActive": true
    }
  ]
}
```

**Notes:**
- Checks if user has purchased a specific photo
- Returns all licenses for that photo

---

### License Search

#### 9. Search Licenses (Admin)
```http
GET /api/v1/licenses/search?licenseKey=LIC-2024&licenseType=COMMERCIAL&isActive=true&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `licenseKey` (optional): License key filter
- `licenseType` (optional): License type filter
- `isActive` (optional): Active status filter
- `userId` (optional): User ID filter
- `photoId` (optional): Photo ID filter

**Response:** `Page<LicenseResponseDto>`

---

### License Statistics

#### 10. Get My License Count
```http
GET /api/v1/licenses/my-licenses/count
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
15
```

---

### License Management (Admin)

#### 11. Deactivate License
```http
POST /api/v1/licenses/{licenseId}/deactivate
Authorization: Bearer {token}
```

**Response:** `200 OK` with updated `LicenseResponseDto`

**Notes:**
- Admin-only operation
- Sets `isActive` to false
- Prevents further downloads

---

#### 12. Activate License
```http
POST /api/v1/licenses/{licenseId}/activate
Authorization: Bearer {token}
```

**Response:** `200 OK` with updated `LicenseResponseDto`

**Notes:**
- Admin-only operation
- Sets `isActive` to true
- Re-enables downloads

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

#### Scenario 1: Complete Order Flow

```bash
# 1. Login as customer
CUSTOMER_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "customer@example.com",
    "password": "Password123!"
  }' | jq -r '.accessToken')

# 2. Create order
ORDER_ID=$(curl -s -X POST http://localhost:8080/api/v1/orders \
  -H "Authorization: Bearer $CUSTOMER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "items": [
      {
        "photoId": 123,
        "licenseType": "COMMERCIAL",
        "price": 150.00
      }
    ],
    "billingInfo": {
      "name": "John Doe",
      "email": "john.doe@example.com",
      "address": "123 Main St",
      "city": "Colombo",
      "country": "Sri Lanka"
    }
  }' | jq -r '.id')

echo "Order ID: $ORDER_ID"

# 3. Validate order
curl -X GET "http://localhost:8080/api/v1/orders/$ORDER_ID/validate" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"

# 4. Process payment success
curl -X POST "http://localhost:8080/api/v1/orders/$ORDER_ID/payment-success?paymentId=pi_test123&transactionId=txn_test123" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"

# 5. Get order details
curl -X GET "http://localhost:8080/api/v1/orders/$ORDER_ID" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"
```

---

#### Scenario 2: License Verification & Download

```bash
# 1. Get my licenses
curl -X GET "http://localhost:8080/api/v1/licenses/my-licenses?page=0&size=10" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"

# 2. Check if photo is purchased
curl -X GET "http://localhost:8080/api/v1/licenses/check-purchase/123" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"

# 3. Verify license for download
curl -X POST http://localhost:8080/api/v1/licenses/verify \
  -H "Authorization: Bearer $CUSTOMER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "licenseKey": "LIC-2024-ABC123XYZ",
    "photoId": 123
  }'

# 4. Validate download authorization
curl -X GET "http://localhost:8080/api/v1/licenses/validate-download?licenseKey=LIC-2024-ABC123XYZ&photoId=123" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"
```

---

#### Scenario 3: Order Management

```bash
# 1. Get my orders
curl -X GET "http://localhost:8080/api/v1/orders/my-orders?page=0&size=10" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"

# 2. Get completed orders
curl -X GET "http://localhost:8080/api/v1/orders/my-orders/completed?page=0&size=10" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"

# 3. Get orders by date range
curl -X GET "http://localhost:8080/api/v1/orders/my-orders/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=10" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"

# 4. Get order count
curl -X GET "http://localhost:8080/api/v1/orders/my-orders/count" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"
```

---

#### Scenario 4: Admin Analytics

```bash
# 1. Login as admin
ADMIN_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin@example.com",
    "password": "AdminPass123!"
  }' | jq -r '.accessToken')

# 2. Get order statistics
curl -X GET "http://localhost:8080/api/v1/orders/query/statistics" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. Get total sales
curl -X GET "http://localhost:8080/api/v1/orders/query/total-sales" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 4. Get top buyers by spending
curl -X GET "http://localhost:8080/api/v1/orders/query/top-buyers/by-spending?page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 5. Get buyer statistics
curl -X GET "http://localhost:8080/api/v1/orders/query/buyer/5/statistics" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 5: Order Item Management

```bash
# 1. Create order
ORDER_ID=$(curl -s -X POST http://localhost:8080/api/v1/orders \
  -H "Authorization: Bearer $CUSTOMER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "items": [
      {"photoId": 123, "licenseType": "BASE", "price": 50.00}
    ],
    "billingInfo": {
      "name": "John Doe",
      "email": "john.doe@example.com"
    }
  }' | jq -r '.id')

# 2. Add item to order
curl -X POST http://localhost:8080/api/v1/order-items \
  -H "Authorization: Bearer $CUSTOMER_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"orderId\": $ORDER_ID,
    \"photoId\": 456,
    \"licenseType\": \"COMMERCIAL\",
    \"price\": 150.00
  }"

# 3. Get order items
curl -X GET "http://localhost:8080/api/v1/order-items/order/$ORDER_ID" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"

# 4. Get item count
curl -X GET "http://localhost:8080/api/v1/order-items/order/$ORDER_ID/count" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"
```

---

## 📝 Best Practices

### Order Management

1. **Validate before payment** - Always validate order before processing payment
2. **Generate unique order numbers** - Use consistent format (e.g., ORD-YYYY-NNNNNN)
3. **Track order lifecycle** - Update status at each stage
4. **Store billing information** - Maintain complete billing details
5. **Handle cancellations gracefully** - Release resources and notify users

### License Management

1. **Generate unique keys** - Use cryptographically secure random keys
2. **Set appropriate limits** - Configure download limits based on license type
3. **Track downloads** - Maintain complete download audit trail
4. **Verify before download** - Always validate license ownership
5. **Handle expiration** - Check expiration dates before allowing downloads

### Order Items

1. **Calculate earnings** - Split revenue between photographer and platform
2. **Validate photo availability** - Ensure photos are active and approved
3. **Handle pricing** - Apply correct pricing based on license type
4. **Prevent duplicates** - Check for existing items before adding
5. **Recalculate totals** - Update order totals when items change

### Analytics

1. **Cache statistics** - Cache frequently accessed statistics
2. **Use pagination** - Always paginate large result sets
3. **Index queries** - Ensure database indexes for common queries
4. **Track metrics** - Monitor key business metrics
5. **Generate reports** - Provide comprehensive reporting capabilities

### Security

1. **Verify ownership** - Ensure users can only access their own data
2. **Validate permissions** - Check role-based access for admin operations
3. **Audit actions** - Log all order and license operations
4. **Secure license keys** - Protect license keys from unauthorized access
5. **Prevent fraud** - Implement fraud detection mechanisms

---

## 🔒 Security Notes

### Access Control

- **Customers**: Can only view/manage their own orders and licenses
- **Photographers**: Same as customers (can purchase photos)
- **Admins**: Full access to all orders, licenses, and analytics

### Data Protection

- License keys: Unique, cryptographically secure
- Billing information: Encrypted at rest
- Order details: Access controlled by user ID
- Download tracking: Complete audit trail

### Validation Rules

- Orders can only be modified in PENDING status
- Licenses generated only for COMPLETED orders
- Download limits enforced at license level
- Expired licenses cannot be used for downloads

---

## 📊 API Endpoints Summary

| Controller | Endpoints | Description |
|------------|-----------|-------------|
| OrderController | 14 | Core order CRUD and management |
| OrderQueryController | 15 | Analytics and reporting |
| OrderItemController | 6 | Order item management |
| LicenseController | 12 | License management and verification |

**Total Endpoints:** 47

---

**Version:** 1.0.0  
**Last Updated:** December 2024  
**Module:** Order & License Management  
**Platform:** Ceylon Wild Capture Backend
