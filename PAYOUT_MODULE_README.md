# Payout Module - Complete API Documentation

**Ceylon Wild Capture Backend - Photographer Earnings & Payout Management System**

This comprehensive documentation covers all API endpoints for the Payout Module, including payout requests, approvals, earnings tracking, and audit logging.

---

## 📋 Table of Contents

1. [Module Overview](#module-overview)
2. [Data Models](#data-models)
3. [Payout Management API](#payout-management-api)
4. [Payout Request API](#payout-request-api)
5. [Payout Approval API](#payout-approval-api)
6. [Earnings Management API](#earnings-management-api)
7. [Payout Audit API](#payout-audit-api)
8. [Testing Guide](#testing-guide)
9. [Best Practices](#best-practices)

---

## 🔎 Module Overview

The Payout Module manages photographer earnings, payout requests, administrative approvals, and comprehensive audit trails for the Ceylon Wild Capture platform.

### Key Features

- **Payout Request Management**: Photographers can request payouts of their earnings
- **Admin Approval Workflow**: Multi-step approval process for payout requests
- **Earnings Tracking**: Real-time calculation of pending, paid, and total earnings
- **Audit Trail**: Complete history of all payout-related actions
- **Multiple Payment Methods**: Support for bank transfers and PayPal
- **Commission Management**: Automatic platform commission calculation
- **Earnings Snapshots**: Historical earnings data preservation
- **Validation & Eligibility**: Automated checks for payout eligibility
- **Statistics & Analytics**: Comprehensive reporting on payouts and earnings

### Controllers

1. **PayoutController** (`/api/v1/payouts`) - Payout history and status queries
2. **PayoutRequestController** (`/api/v1/payouts/requests`) - Payout request submission
3. **PayoutApprovalController** (`/api/v1/payouts/approvals`) - Admin approval operations
4. **EarningsController** (`/api/v1/earnings`) - Earnings calculations and summaries
5. **PayoutAuditController** (`/api/v1/payouts/audits`) - Audit log retrieval

### Payout Status Flow

```
PENDING → PROCESSING → COMPLETED
    ↓
REJECTED / CANCELLED / FAILED
```

---

## 🗂️ Data Models

### Payout Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `payoutReference` | String | Unique, max 50 chars, required | Unique payout reference number |
| `photographer` | PhotographerProfile | Required | Associated photographer profile |
| `amount` | BigDecimal | Required, precision 12,2 | Gross payout amount |
| `fee` | BigDecimal | Precision 10,2, Default: 0 | Platform/processing fee |
| `netAmount` | BigDecimal | Required, precision 12,2 | Net amount after fees |
| `currency` | String | Max 10 chars, Default: "USD" | Currency code |
| `status` | PayoutStatus | Required, Default: PENDING | Current payout status |
| `paymentMethod` | String | Max 50 chars | Payment method (BANK_TRANSFER, PAYPAL) |
| `paymentProvider` | String | Max 50 chars | Payment provider name |
| `transactionId` | String | Max 100 chars | External transaction ID |
| `bankAccountNumber` | String | Max 50 chars | Bank account for transfer |
| `bankName` | String | Max 100 chars | Bank name |
| `paypalEmail` | String | Max 100 chars | PayPal email address |
| `periodStart` | LocalDateTime | - | Earnings period start date |
| `periodEnd` | LocalDateTime | - | Earnings period end date |
| `notes` | String | TEXT | Additional notes |
| `errorMessage` | String | TEXT | Error details if failed |
| `processedAt` | LocalDateTime | - | Processing timestamp |
| `completedAt` | LocalDateTime | - | Completion timestamp |
| `cancelledAt` | LocalDateTime | - | Cancellation timestamp |
| `createdAt` | LocalDateTime | Auto-generated | Request creation timestamp |
| `updatedAt` | LocalDateTime | Auto-updated | Last update timestamp |

---

### EarningsSnapshot Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `photographer` | PhotographerProfile | Required | Associated photographer |
| `totalEarnings` | BigDecimal | Precision 12,2 | Total lifetime earnings |
| `pendingEarnings` | BigDecimal | Precision 12,2 | Earnings awaiting payout |
| `paidEarnings` | BigDecimal | Precision 12,2 | Already paid earnings |
| `snapshotDate` | LocalDateTime | Auto-generated | Snapshot creation date |

---

### PayoutAudit Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `payout` | Payout | Required | Associated payout |
| `admin` | User | Required | Admin who performed action |
| `action` | String | Max 50 chars, required | Action performed |
| `oldStatus` | PayoutStatus | - | Previous status |
| `newStatus` | PayoutStatus | - | New status |
| `notes` | String | TEXT | Action notes/reason |
| `createdAt` | LocalDateTime | Auto-generated | Action timestamp |

---

## 💰 Payout Management API

**Base Path:** `/api/v1/payouts`

### Retrieval Operations

#### 1. Get Payout by ID
```http
GET /api/v1/payouts/{payoutId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `PayoutResponseDto` or `404 Not Found`

---

#### 2. Get Payout by Reference
```http
GET /api/v1/payouts/reference/{payoutReference}
Authorization: Bearer {token}
```

**Example:**
```http
GET /api/v1/payouts/reference/PAY-2024-001234
Authorization: Bearer {token}
```

**Response:** `200 OK` with `PayoutResponseDto` or `404 Not Found`

---

#### 3. Get All Payouts (Paginated)
```http
GET /api/v1/payouts?page=0&size=20&sort=createdAt,desc
Authorization: Bearer {token}
```

**Response:** `Page<PayoutResponseDto>`

**Notes:**
- Admin-only operation
- Returns all payouts across all photographers

---

#### 4. Get Payouts by Photographer
```http
GET /api/v1/payouts/photographer/{photographerId}?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<PayoutResponseDto>`

**Notes:**
- Photographers can only view their own payouts
- Admins can view any photographer's payouts

---

#### 5. Get Payouts by Status
```http
GET /api/v1/payouts/status/{status}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `status`: PENDING, PROCESSING, COMPLETED, REJECTED, CANCELLED, FAILED

**Example:**
```http
GET /api/v1/payouts/status/PENDING?page=0&size=20
Authorization: Bearer {token}
```

---

#### 6. Get Payouts by Date Range
```http
GET /api/v1/payouts/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `startDate` (required): Start date in ISO format
- `endDate` (required): End date in ISO format

---

### Search & Filtering

#### 7. Search Payouts with Criteria
```http
POST /api/v1/payouts/search?page=0&size=20
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "photographerId": 1,
  "status": "COMPLETED",
  "paymentMethod": "BANK_TRANSFER",
  "minAmount": 100.00,
  "maxAmount": 5000.00,
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-12-31T23:59:59"
}
```

**Response:** `Page<PayoutResponseDto>`

---

### Photographer-Specific Queries

#### 8. Get Payout History
```http
GET /api/v1/payouts/photographer/{photographerId}/history?page=0&size=20
Authorization: Bearer {token}
```

**Response:** Complete payout history ordered by creation date (descending)

---

#### 9. Get Pending Payouts
```http
GET /api/v1/payouts/photographer/{photographerId}/pending?page=0&size=20
Authorization: Bearer {token}
```

**Response:** Payouts with status PENDING or PROCESSING

---

#### 10. Get Completed Payouts
```http
GET /api/v1/payouts/photographer/{photographerId}/completed?page=0&size=20
Authorization: Bearer {token}
```

**Response:** Payouts with status COMPLETED

---

#### 11. Get Most Recent Payout
```http
GET /api/v1/payouts/photographer/{photographerId}/recent
Authorization: Bearer {token}
```

**Response:** `200 OK` with most recent `PayoutResponseDto` or `404 Not Found`

---

### Statistics

#### 12. Get Payout Statistics
```http
GET /api/v1/payouts/statistics
Authorization: Bearer {token}
```

**Response:**
```json
{
  "totalPayouts": 1547,
  "totalAmount": 125000.50,
  "pendingCount": 23,
  "processingCount": 5,
  "completedCount": 1500,
  "rejectedCount": 15,
  "averagePayoutAmount": 80.85,
  "totalFees": 18750.07
}
```

**Notes:**
- Admin-only operation
- Provides platform-wide statistics

---

#### 13. Count Payouts by Status
```http
GET /api/v1/payouts/status/{status}/count
Authorization: Bearer {token}
```

**Example:**
```http
GET /api/v1/payouts/status/PENDING/count
Authorization: Bearer {token}
```

**Response:**
```json
{
  "status": "PENDING",
  "count": 23
}
```

---

#### 14. Count Payouts for Photographer
```http
GET /api/v1/payouts/photographer/{photographerId}/count
Authorization: Bearer {token}
```

**Response:**
```json
{
  "photographerId": 1,
  "count": 42
}
```

---

## 📝 Payout Request API

**Base Path:** `/api/v1/payouts/requests`

### Request Submission

#### 1. Submit Payout Request
```http
POST /api/v1/payouts/requests
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "photographerId": 1,
  "amount": 500.00,
  "paymentMethod": "BANK_TRANSFER",
  "bankAccountNumber": "1234567890",
  "bankName": "Bank of Ceylon",
  "notes": "Monthly payout request"
}
```

**Alternative (PayPal):**
```json
{
  "photographerId": 1,
  "amount": 500.00,
  "paymentMethod": "PAYPAL",
  "paypalEmail": "photographer@example.com",
  "notes": "Monthly payout request"
}
```

**Response:** `201 Created` with `PayoutResponseDto`

**Notes:**
- Validates photographer has sufficient pending earnings
- Checks for existing pending requests
- Validates amount is within allowed range
- Automatically generates unique payout reference

---

### Validation Operations

#### 2. Validate Payout Eligibility
```http
POST /api/v1/payouts/requests/validate?photographerId=1&requestedAmount=500.00
Authorization: Bearer {token}
```

**Query Parameters:**
- `photographerId` (required): Photographer ID
- `requestedAmount` (required): Requested payout amount

**Response:**
```json
{
  "eligible": true,
  "photographerId": 1,
  "requestedAmount": 500.00
}
```

**Notes:**
- Checks if photographer has sufficient pending earnings
- Validates amount is within min/max limits
- Checks for existing pending requests

---

#### 3. Check Pending Requests
```http
GET /api/v1/payouts/requests/photographer/{photographerId}/pending
Authorization: Bearer {token}
```

**Response:**
```json
{
  "photographerId": 1,
  "hasPendingRequests": false
}
```

**Notes:**
- Returns true if photographer has any pending or processing payouts
- Used to prevent duplicate requests

---

#### 4. Validate Request DTO
```http
POST /api/v1/payouts/requests/validate-request
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:** `PayoutRequestDto`

**Response:**
```json
{
  "valid": true,
  "errors": []
}
```

**Or (if invalid):**
```json
{
  "valid": false,
  "errors": [
    "Amount must be at least 50.00",
    "Bank account number is required for bank transfers"
  ]
}
```

---

### Configuration Queries

#### 5. Get Minimum Payout Amount
```http
GET /api/v1/payouts/requests/minimum-amount
Authorization: Bearer {token}
```

**Response:**
```json
{
  "minimumAmount": 50.00
}
```

---

#### 6. Get Maximum Payout Amount
```http
GET /api/v1/payouts/requests/maximum-amount
Authorization: Bearer {token}
```

**Response:**
```json
{
  "maximumAmount": 10000.00
}
```

---

#### 7. Check Amount Validity
```http
GET /api/v1/payouts/requests/amount-valid?amount=500.00
Authorization: Bearer {token}
```

**Response:**
```json
{
  "amount": 500.00,
  "valid": true
}
```

---

### Request Management

#### 8. Cancel Payout Request
```http
DELETE /api/v1/payouts/requests/{payoutId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with updated `PayoutResponseDto`

**Notes:**
- Only pending payouts can be cancelled
- Photographer can cancel their own requests
- Admins can cancel any request
- Sets status to CANCELLED and records timestamp

---

## ✅ Payout Approval API

**Base Path:** `/api/v1/payouts/approvals`

### Approval Operations

#### 1. Approve Payout
```http
POST /api/v1/payouts/approvals/approve
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "payoutId": 1,
  "adminId": 5,
  "notes": "Approved - all documentation verified",
  "transactionId": "TXN-2024-001234"
}
```

**Response:** `200 OK` with `PayoutResponseDto`

**Notes:**
- Admin-only operation
- Changes status from PENDING to PROCESSING or COMPLETED
- Creates audit log entry
- May trigger payment processing

---

#### 2. Reject Payout
```http
POST /api/v1/payouts/approvals/reject
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "payoutId": 1,
  "adminId": 5,
  "notes": "Rejected - insufficient documentation",
  "reason": "Missing bank verification documents"
}
```

**Response:** `200 OK` with `PayoutResponseDto`

**Notes:**
- Admin-only operation
- Changes status to REJECTED
- Returns funds to pending earnings
- Creates audit log entry
- Sends notification to photographer

---

### Review Queue

#### 3. Get Payouts Pending Review
```http
GET /api/v1/payouts/approvals/pending?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<PayoutResponseDto>`

**Notes:**
- Admin-only operation
- Returns all payouts with PENDING status
- Ordered by creation date (oldest first)

---

#### 4. Count Payouts Pending Review
```http
GET /api/v1/payouts/approvals/pending/count
Authorization: Bearer {token}
```

**Response:**
```json
{
  "pendingCount": 23
}
```

**Notes:**
- Admin-only operation
- Useful for dashboard badges

---

### Validation

#### 5. Validate Payout for Approval
```http
GET /api/v1/payouts/approvals/{payoutId}/validate
Authorization: Bearer {token}
```

**Response:**
```json
{
  "payoutId": 1,
  "valid": true,
  "errors": []
}
```

**Or (if invalid):**
```json
{
  "payoutId": 1,
  "valid": false,
  "errors": [
    "Photographer profile incomplete",
    "Bank account not verified"
  ]
}
```

---

### Review History

#### 6. Get Review History
```http
GET /api/v1/payouts/approvals/{payoutId}/history?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<PayoutAuditDto>`

**Notes:**
- Returns all audit entries for the payout
- Includes approval, rejection, and status change records

---

### Statistics

#### 7. Get Review Statistics
```http
GET /api/v1/payouts/approvals/statistics
Authorization: Bearer {token}
```

**Response:**
```json
{
  "totalReviewed": 1500,
  "approved": 1450,
  "rejected": 50,
  "averageReviewTimeHours": 4.5,
  "approvalRate": 96.67,
  "rejectionRate": 3.33
}
```

**Notes:**
- Admin-only operation
- Provides performance metrics

---

#### 8. Get Average Review Time
```http
GET /api/v1/payouts/approvals/statistics/average-review-time
Authorization: Bearer {token}
```

**Response:**
```json
{
  "averageReviewTimeHours": 4.5
}
```

---

#### 9. Get Approval Rate
```http
GET /api/v1/payouts/approvals/statistics/approval-rate
Authorization: Bearer {token}
```

**Response:**
```json
{
  "approvalRatePercentage": 96.67
}
```

---

#### 10. Get Rejection Rate
```http
GET /api/v1/payouts/approvals/statistics/rejection-rate
Authorization: Bearer {token}
```

**Response:**
```json
{
  "rejectionRatePercentage": 3.33
}
```

---

## 💵 Earnings Management API

**Base Path:** `/api/v1/earnings`

### Earnings Summaries

#### 1. Get Earnings Summary
```http
GET /api/v1/earnings/photographer/{photographerId}/summary
Authorization: Bearer {token}
```

**Response:**
```json
{
  "photographerId": 1,
  "totalEarnings": 5000.00,
  "pendingEarnings": 1200.00,
  "paidEarnings": 3800.00,
  "availableForPayout": 1200.00,
  "lastPayoutDate": "2024-11-15T10:30:00",
  "nextPayoutEligibleDate": "2024-12-01T00:00:00"
}
```

---

#### 2. Get Earnings History
```http
GET /api/v1/earnings/photographer/{photographerId}/history?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<EarningsResponseDto>`

**Notes:**
- Returns historical earnings snapshots
- Useful for tracking earnings over time

---

### Earnings Calculations

#### 3. Get Pending Earnings
```http
GET /api/v1/earnings/photographer/{photographerId}/pending
Authorization: Bearer {token}
```

**Response:**
```json
{
  "photographerId": 1,
  "pendingEarnings": 1200.00
}
```

**Notes:**
- Earnings not yet paid out
- Available for payout requests

---

#### 4. Get Total Earnings
```http
GET /api/v1/earnings/photographer/{photographerId}/total
Authorization: Bearer {token}
```

**Response:**
```json
{
  "photographerId": 1,
  "totalEarnings": 5000.00
}
```

**Notes:**
- Lifetime earnings (pending + paid)

---

#### 5. Get Paid Earnings
```http
GET /api/v1/earnings/photographer/{photographerId}/paid
Authorization: Bearer {token}
```

**Response:**
```json
{
  "photographerId": 1,
  "paidEarnings": 3800.00
}
```

**Notes:**
- Total amount already paid out

---

#### 6. Get Earnings for Date Range
```http
GET /api/v1/earnings/photographer/{photographerId}/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59
Authorization: Bearer {token}
```

**Response:**
```json
{
  "photographerId": 1,
  "earnings": 2500.00,
  "startDate": "2024-01-01T00:00:00",
  "endDate": "2024-12-31T23:59:59"
}
```

---

### Earnings Breakdown

#### 7. Get Earnings Breakdown
```http
GET /api/v1/earnings/photographer/{photographerId}/breakdown
Authorization: Bearer {token}
```

**Response:**
```json
{
  "photographerId": 1,
  "breakdown": {
    "photoSales": 4500.00,
    "commissionDeductions": 675.00,
    "netEarnings": 3825.00,
    "pendingPayouts": 1200.00,
    "completedPayouts": 2625.00
  }
}
```

---

### Snapshots

#### 8. Get Most Recent Earnings Snapshot
```http
GET /api/v1/earnings/photographer/{photographerId}/recent-snapshot
Authorization: Bearer {token}
```

**Response:** `200 OK` with `EarningsResponseDto` or `404 Not Found`

---

#### 9. Create Earnings Snapshot
```http
POST /api/v1/earnings/photographer/{photographerId}/snapshot
Authorization: Bearer {token}
```

**Response:** `200 OK` with `EarningsResponseDto`

**Notes:**
- Creates a point-in-time snapshot of earnings
- Useful for historical tracking
- Automatically created during payout processing

---

### Statistics

#### 10. Get Earnings Statistics
```http
GET /api/v1/earnings/statistics
Authorization: Bearer {token}
```

**Response:**
```json
{
  "totalPlatformEarnings": 125000.00,
  "totalPendingEarnings": 15000.00,
  "totalPaidEarnings": 110000.00,
  "averagePhotographerEarnings": 2500.00,
  "topEarningPhotographer": {
    "id": 5,
    "name": "John Doe",
    "earnings": 15000.00
  }
}
```

**Notes:**
- Admin-only operation
- Platform-wide statistics

---

## 📊 Payout Audit API

**Base Path:** `/api/v1/payouts/audits`

### Audit Retrieval

#### 1. Get Audit by ID
```http
GET /api/v1/payouts/audits/{auditId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `PayoutAuditDto` or `404 Not Found`

---

#### 2. Get Audits by Payout
```http
GET /api/v1/payouts/audits/payout/{payoutId}?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<PayoutAuditDto>`

**Notes:**
- Returns all audit entries for a specific payout
- Ordered by creation date (descending)

---

#### 3. Get Audits by Admin
```http
GET /api/v1/payouts/audits/admin/{adminId}?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<PayoutAuditDto>`

**Notes:**
- Returns all actions performed by a specific admin
- Useful for admin activity tracking

---

#### 4. Get Audits by Action
```http
GET /api/v1/payouts/audits/action/{action}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `action`: APPROVED, REJECTED, CANCELLED, PROCESSED, COMPLETED, etc.

**Example:**
```http
GET /api/v1/payouts/audits/action/APPROVED?page=0&size=20
Authorization: Bearer {token}
```

---

#### 5. Get Audits by New Status
```http
GET /api/v1/payouts/audits/status/{newStatus}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `newStatus`: PENDING, PROCESSING, COMPLETED, REJECTED, etc.

---

#### 6. Get Audits by Date Range
```http
GET /api/v1/payouts/audits/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20
Authorization: Bearer {token}
```

---

### Full History

#### 7. Get Payout Audit History
```http
GET /api/v1/payouts/audits/payout/{payoutId}/full-history
Authorization: Bearer {token}
```

**Response:** `List<PayoutAuditDto>`

**Notes:**
- Returns complete audit trail (not paginated)
- Ordered chronologically

---

### Statistics

#### 8. Count Audits for Payout
```http
GET /api/v1/payouts/audits/payout/{payoutId}/count
Authorization: Bearer {token}
```

**Response:**
```json
{
  "payoutId": 1,
  "auditCount": 5
}
```

---

#### 9. Count Audits by Admin
```http
GET /api/v1/payouts/audits/admin/{adminId}/count
Authorization: Bearer {token}
```

**Response:**
```json
{
  "adminId": 5,
  "auditCount": 142
}
```

---

#### 10. Get Audit Statistics
```http
GET /api/v1/payouts/audits/statistics
Authorization: Bearer {token}
```

**Response:**
```json
{
  "totalAudits": 5000,
  "approvalActions": 1450,
  "rejectionActions": 50,
  "cancellationActions": 25,
  "mostActiveAdmin": {
    "id": 5,
    "name": "Admin User",
    "actionCount": 500
  }
}
```

---

### Export

#### 11. Export Audits as CSV
```http
GET /api/v1/payouts/audits/payout/{payoutId}/export/csv
Authorization: Bearer {token}
```

**Response:** CSV file download

**Headers:**
```
Content-Disposition: attachment; filename="payout-audits.csv"
Content-Type: text/csv
```

**Notes:**
- Exports complete audit trail for a payout
- Useful for compliance and reporting

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

#### Scenario 1: Complete Payout Request Flow

```bash
# 1. Login as photographer
PHOTO_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "photographer@example.com",
    "password": "Password123!"
  }' | jq -r '.accessToken')

# 2. Check pending earnings
curl -X GET "http://localhost:8080/api/v1/earnings/photographer/1/pending" \
  -H "Authorization: Bearer $PHOTO_TOKEN"

# 3. Validate payout eligibility
curl -X POST "http://localhost:8080/api/v1/payouts/requests/validate?photographerId=1&requestedAmount=500.00" \
  -H "Authorization: Bearer $PHOTO_TOKEN"

# 4. Submit payout request
PAYOUT_ID=$(curl -s -X POST http://localhost:8080/api/v1/payouts/requests \
  -H "Authorization: Bearer $PHOTO_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "photographerId": 1,
    "amount": 500.00,
    "paymentMethod": "BANK_TRANSFER",
    "bankAccountNumber": "1234567890",
    "bankName": "Bank of Ceylon"
  }' | jq -r '.id')

echo "Payout Request ID: $PAYOUT_ID"

# 5. Check request status
curl -X GET "http://localhost:8080/api/v1/payouts/$PAYOUT_ID" \
  -H "Authorization: Bearer $PHOTO_TOKEN"
```

---

#### Scenario 2: Admin Approval Workflow

```bash
# 1. Login as admin
ADMIN_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin@example.com",
    "password": "AdminPass123!"
  }' | jq -r '.accessToken')

# 2. Get pending payouts
curl -X GET "http://localhost:8080/api/v1/payouts/approvals/pending?page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. Validate payout for approval
curl -X GET "http://localhost:8080/api/v1/payouts/approvals/$PAYOUT_ID/validate" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 4. Approve payout
curl -X POST http://localhost:8080/api/v1/payouts/approvals/approve \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"payoutId\": $PAYOUT_ID,
    \"adminId\": 5,
    \"notes\": \"Approved - documentation verified\",
    \"transactionId\": \"TXN-2024-001234\"
  }"

# 5. Check audit trail
curl -X GET "http://localhost:8080/api/v1/payouts/audits/payout/$PAYOUT_ID/full-history" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 3: Earnings Tracking

```bash
# 1. Get earnings summary
curl -X GET "http://localhost:8080/api/v1/earnings/photographer/1/summary" \
  -H "Authorization: Bearer $PHOTO_TOKEN"

# 2. Get earnings breakdown
curl -X GET "http://localhost:8080/api/v1/earnings/photographer/1/breakdown" \
  -H "Authorization: Bearer $PHOTO_TOKEN"

# 3. Get earnings for specific period
curl -X GET "http://localhost:8080/api/v1/earnings/photographer/1/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59" \
  -H "Authorization: Bearer $PHOTO_TOKEN"

# 4. Create earnings snapshot
curl -X POST "http://localhost:8080/api/v1/earnings/photographer/1/snapshot" \
  -H "Authorization: Bearer $PHOTO_TOKEN"

# 5. Get earnings history
curl -X GET "http://localhost:8080/api/v1/earnings/photographer/1/history?page=0&size=10" \
  -H "Authorization: Bearer $PHOTO_TOKEN"
```

---

#### Scenario 4: Payout Rejection

```bash
# 1. Reject payout
curl -X POST http://localhost:8080/api/v1/payouts/approvals/reject \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"payoutId\": $PAYOUT_ID,
    \"adminId\": 5,
    \"notes\": \"Rejected - missing documentation\",
    \"reason\": \"Bank account verification documents not provided\"
  }"

# 2. Verify earnings returned to pending
curl -X GET "http://localhost:8080/api/v1/earnings/photographer/1/pending" \
  -H "Authorization: Bearer $PHOTO_TOKEN"

# 3. Check audit trail
curl -X GET "http://localhost:8080/api/v1/payouts/audits/payout/$PAYOUT_ID/full-history" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 5: Statistics & Reporting

```bash
# 1. Get payout statistics
curl -X GET "http://localhost:8080/api/v1/payouts/statistics" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 2. Get review statistics
curl -X GET "http://localhost:8080/api/v1/payouts/approvals/statistics" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. Get earnings statistics
curl -X GET "http://localhost:8080/api/v1/earnings/statistics" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 4. Get audit statistics
curl -X GET "http://localhost:8080/api/v1/payouts/audits/statistics" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 5. Export audit trail
curl -X GET "http://localhost:8080/api/v1/payouts/audits/payout/$PAYOUT_ID/export/csv" \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  --output payout-audit.csv
```

---

## 📝 Best Practices

### Payout Requests

1. **Validate before submission** - Always check eligibility and amount validity
2. **Check for pending requests** - Prevent duplicate submissions
3. **Provide complete information** - Include all required payment details
4. **Use appropriate payment method** - Choose based on photographer location
5. **Add meaningful notes** - Help admins process requests faster

### Admin Approvals

1. **Verify documentation** - Ensure all required documents are provided
2. **Check payment details** - Validate bank account or PayPal information
3. **Add detailed notes** - Document approval/rejection reasons
4. **Process in FIFO order** - Handle oldest requests first
5. **Monitor review metrics** - Track approval rates and review times

### Earnings Management

1. **Create regular snapshots** - Preserve historical earnings data
2. **Track commission rates** - Monitor platform fee calculations
3. **Reconcile regularly** - Verify earnings match order data
4. **Monitor pending earnings** - Alert photographers when eligible for payout
5. **Provide detailed breakdowns** - Help photographers understand earnings

### Audit Trails

1. **Log all actions** - Maintain complete audit history
2. **Include context** - Add meaningful notes to audit entries
3. **Export regularly** - Create backups of audit data
4. **Monitor admin activity** - Track who performs what actions
5. **Use for compliance** - Maintain records for financial audits

### Security

1. **Validate permissions** - Ensure users can only access their own data
2. **Encrypt sensitive data** - Protect bank account and PayPal information
3. **Implement rate limiting** - Prevent abuse of payout requests
4. **Audit admin actions** - Track all administrative operations
5. **Secure payment processing** - Use trusted payment providers

---

## 🔒 Security Notes

### Access Control

- **Photographers**: Can only view/request their own payouts and earnings
- **Admins**: Full access to all payouts, approvals, and audits
- **System**: Automated processes for earnings calculations

### Data Protection

- Bank account numbers: Encrypted at rest
- PayPal emails: Validated and encrypted
- Transaction IDs: Logged for reconciliation
- Audit trails: Immutable and timestamped

### Validation Rules

- Minimum payout: $50.00 (configurable)
- Maximum payout: $10,000.00 (configurable)
- Pending earnings must cover requested amount
- No duplicate pending requests allowed
- Payment details required based on method

---

## 📊 API Endpoints Summary

| Controller | Endpoints | Description |
|------------|-----------|-------------|
| PayoutController | 14 | Payout history and status queries |
| PayoutRequestController | 8 | Payout request submission and validation |
| PayoutApprovalController | 10 | Admin approval and rejection operations |
| EarningsController | 10 | Earnings calculations and summaries |
| PayoutAuditController | 11 | Audit log retrieval and export |

**Total Endpoints:** 53

---

**Version:** 1.0.0  
**Last Updated:** December 2024  
**Module:** Payout Management  
**Platform:** Ceylon Wild Capture Backend
