# Payment Module - Complete API Documentation

**Ceylon Wild Capture Backend - Payment Processing & Transaction Management System**

This comprehensive documentation covers all API endpoints for the Payment Module, including payment processing, callbacks, webhooks, and transaction logging.

---

## 📋 Table of Contents

1. [Module Overview](#module-overview)
2. [Data Models](#data-models)
3. [Payment Management API](#payment-management-api)
4. [Payment Callback API](#payment-callback-api)
5. [Payment Webhook API](#payment-webhook-api)
6. [Payment Log API](#payment-log-api)
7. [Testing Guide](#testing-guide)
8. [Best Practices](#best-practices)

---

## 🔎 Module Overview

The Payment Module handles all payment processing operations, integrating with multiple payment gateways and providing comprehensive transaction tracking for the Ceylon Wild Capture platform.

### Key Features

- **Multi-Gateway Support**: Stripe, PayPal, and Razorpay integration
- **Payment Intent Creation**: Secure payment initialization
- **Callback Handling**: Success, failure, and cancellation callbacks
- **Webhook Processing**: Real-time payment status updates from gateways
- **Transaction Logging**: Comprehensive audit trail for all transactions
- **Refund Management**: Admin-controlled refund processing
- **Payment Verification**: Secure payment validation
- **Search & Filtering**: Advanced payment query capabilities
- **Error Handling**: Robust error tracking and retry mechanisms

### Controllers

1. **PaymentController** (`/api/v1/payments`) - Core payment operations
2. **PaymentCallbackController** (`/api/v1/payments/callback`) - Payment callback handling
3. **PaymentWebhookController** (`/api/v1/payments/webhook`) - Gateway webhook processing
4. **PaymentLogController** (`/api/v1/payments/logs`) - Transaction log management

### Payment Providers

- **Stripe**: Credit/debit cards, digital wallets
- **PayPal**: PayPal accounts, credit/debit cards
- **Razorpay**: India-specific payment methods

### Payment Status Flow

```
PENDING → PROCESSING → COMPLETED
    ↓           ↓
FAILED    CANCELLED
    ↓
REFUNDED (from COMPLETED)
```

---

## 🗂️ Data Models

### Payment Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `order` | Order | Required, unique | Associated order |
| `paymentMethod` | String | Max 50 chars, required | Payment method type |
| `paymentProvider` | String | Max 50 chars | Payment gateway provider |
| `transactionId` | String | Unique, max 100 chars | Gateway transaction ID |
| `paymentId` | String | Unique, max 100 chars | Gateway payment ID |
| `amount` | BigDecimal | Required, precision 12,2 | Payment amount |
| `fee` | BigDecimal | Precision 10,2, Default: 0 | Processing fee |
| `netAmount` | BigDecimal | Precision 12,2 | Net amount after fees |
| `currency` | String | Max 10 chars | Currency code (USD, LKR, etc.) |
| `status` | String | Max 30 chars, required | Payment status |
| `payerEmail` | String | Max 100 chars | Payer's email address |
| `payerName` | String | Max 100 chars | Payer's name |
| `cardLastFour` | String | 4 chars | Last 4 digits of card |
| `cardBrand` | String | Max 30 chars | Card brand (Visa, Mastercard) |
| `paymentResponse` | String | TEXT | Raw gateway response |
| `errorMessage` | String | TEXT | Error details if failed |
| `paidAt` | LocalDateTime | - | Payment completion timestamp |
| `refundedAt` | LocalDateTime | - | Refund timestamp |
| `refundAmount` | BigDecimal | Precision 12,2 | Refunded amount |
| `refundReason` | String | TEXT | Refund reason |
| `createdAt` | LocalDateTime | Auto-generated | Creation timestamp |
| `updatedAt` | LocalDateTime | Auto-updated | Last update timestamp |

---

### PaymentLog Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `payment` | Payment | Required | Associated payment |
| `transactionType` | TransactionType | Required | Type of transaction |
| `providerReference` | String | Max 200 chars | Provider reference ID |
| `requestPayload` | String | TEXT | Request sent to gateway |
| `responsePayload` | String | TEXT | Response from gateway |
| `httpStatusCode` | Integer | - | HTTP status code |
| `status` | String | Max 30 chars | Transaction status |
| `errorMessage` | String | TEXT | Error details |
| `errorCode` | String | Max 50 chars | Error code |
| `ipAddress` | String | Max 45 chars | Client IP address |
| `userAgent` | String | TEXT | Client user agent |
| `metadata` | String | TEXT | Additional metadata (JSON) |
| `createdAt` | LocalDateTime | Auto-generated | Log creation timestamp |

---

### WebhookEvent Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `eventId` | String | Unique, max 200 chars, required | Gateway event ID |
| `provider` | PaymentProvider | Required | Payment provider |
| `eventType` | WebhookEventType | Required | Type of webhook event |
| `paymentId` | String | Max 100 chars | Associated payment ID |
| `eventPayload` | String | TEXT, required | Raw webhook payload |
| `signature` | String | TEXT | Webhook signature for verification |
| `processed` | Boolean | Default: false | Processing status |
| `processedAt` | LocalDateTime | - | Processing timestamp |
| `processingError` | String | TEXT | Error during processing |
| `retryCount` | Integer | Default: 0 | Number of retry attempts |
| `ipAddress` | String | Max 45 chars | Gateway IP address |
| `createdAt` | LocalDateTime | Auto-generated | Event receipt timestamp |
| `updatedAt` | LocalDateTime | Auto-updated | Last update timestamp |

---

## 💳 Payment Management API

**Base Path:** `/api/v1/payments`

### Payment Creation

#### 1. Create Payment Intent
```http
POST /api/v1/payments/intent
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "orderId": 123,
  "amount": 150.00,
  "currency": "USD",
  "paymentMethod": "CARD",
  "paymentProvider": "STRIPE",
  "returnUrl": "https://example.com/payment/success",
  "cancelUrl": "https://example.com/payment/cancel",
  "metadata": {
    "customerNote": "Express delivery requested"
  }
}
```

**Response:** `201 Created`
```json
{
  "paymentId": 1,
  "clientSecret": "pi_3abc123xyz_secret_def456",
  "providerPaymentId": "pi_3abc123xyz",
  "amount": 150.00,
  "currency": "USD",
  "status": "PENDING",
  "paymentUrl": "https://checkout.stripe.com/pay/cs_test_abc123",
  "expiresAt": "2024-12-06T14:40:55Z"
}
```

**Notes:**
- Requires CUSTOMER or PHOTOGRAPHER role
- Creates payment intent with selected gateway
- Returns client secret for frontend payment completion
- Payment URL for redirect-based flows

---

### Payment Retrieval

#### 2. Get Payment by ID
```http
GET /api/v1/payments/{paymentId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `PaymentResponseDto` or `404 Not Found`

**Notes:**
- Users can only view their own payments
- Admins can view any payment

---

#### 3. Get Payment by Provider Payment ID
```http
GET /api/v1/payments/provider/{providerPaymentId}
Authorization: Bearer {token}
```

**Example:**
```http
GET /api/v1/payments/provider/pi_3abc123xyz
Authorization: Bearer {token}
```

**Response:** `200 OK` with `PaymentResponseDto`

**Notes:**
- Useful for webhook processing
- Requires CUSTOMER, PHOTOGRAPHER, or ADMIN role

---

#### 4. Get Payment by Order ID
```http
GET /api/v1/payments/order/{orderId}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `PaymentResponseDto`

**Notes:**
- Returns the payment associated with an order
- One-to-one relationship between order and payment

---

#### 5. Get My Payments
```http
GET /api/v1/payments/my-payments?page=0&size=20&sort=createdAt,desc
Authorization: Bearer {token}
```

**Response:** `Page<PaymentResponseDto>`

**Notes:**
- Returns current user's payments
- Supports pagination and sorting

---

### Admin Operations

#### 6. Get Payments by Status (Admin)
```http
GET /api/v1/payments/status/{status}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `status`: PENDING, PROCESSING, COMPLETED, FAILED, CANCELLED, REFUNDED

**Example:**
```http
GET /api/v1/payments/status/COMPLETED?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<PaymentResponseDto>`

**Notes:**
- Admin-only operation
- Useful for monitoring payment statuses

---

#### 7. Get Payments by Date Range (Admin)
```http
GET /api/v1/payments/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `startDate` (required): Start date in ISO format
- `endDate` (required): End date in ISO format

**Response:** `Page<PaymentResponseDto>`

**Notes:**
- Admin-only operation
- Useful for financial reporting

---

#### 8. Search Payments (Admin)
```http
GET /api/v1/payments/search?searchTerm=john.doe@example.com&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `searchTerm` (required): Search term (email, transaction ID, payer name)

**Response:** `Page<PaymentResponseDto>`

**Notes:**
- Admin-only operation
- Searches across multiple fields

---

### Payment Actions

#### 9. Process Refund (Admin)
```http
POST /api/v1/payments/refund
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "paymentId": 123,
  "amount": 150.00,
  "reason": "Customer requested refund - product not as described",
  "notifyCustomer": true
}
```

**Response:** `200 OK` with updated `PaymentResponseDto`

**Notes:**
- Admin-only operation
- Can be full or partial refund
- Processes refund through payment gateway
- Updates payment status to REFUNDED

---

#### 10. Cancel Payment
```http
POST /api/v1/payments/{paymentId}/cancel
Authorization: Bearer {token}
```

**Response:** `200 OK` with updated `PaymentResponseDto`

**Notes:**
- Can only cancel PENDING or PROCESSING payments
- User can cancel their own payments
- Updates status to CANCELLED

---

### Statistics

#### 11. Get My Payment Count
```http
GET /api/v1/payments/my-payments/count
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
42
```

**Notes:**
- Returns total number of payments for current user

---

## 🔄 Payment Callback API

**Base Path:** `/api/v1/payments/callback`

### Callback Handling

#### 1. Handle Payment Success
```http
POST /api/v1/payments/callback/success
Content-Type: application/json
```

**Request Body:**
```json
{
  "paymentId": "pi_3abc123xyz",
  "orderId": 123,
  "transactionId": "txn_abc123",
  "amount": 150.00,
  "currency": "USD",
  "payerEmail": "customer@example.com",
  "payerName": "John Doe",
  "cardLastFour": "4242",
  "cardBrand": "Visa",
  "metadata": {}
}
```

**Response:** `200 OK` with `PaymentResponseDto`

**Notes:**
- Called by payment gateway after successful payment
- Updates payment status to COMPLETED
- Triggers order fulfillment
- No authentication required (validated by signature)

---

#### 2. Handle Payment Failure
```http
POST /api/v1/payments/callback/failure
Content-Type: application/json
```

**Request Body:**
```json
{
  "paymentId": "pi_3abc123xyz",
  "orderId": 123,
  "errorCode": "card_declined",
  "errorMessage": "Your card was declined",
  "failureReason": "insufficient_funds"
}
```

**Response:** `200 OK` with `PaymentResponseDto`

**Notes:**
- Updates payment status to FAILED
- Records error details
- Notifies customer

---

#### 3. Handle Payment Cancellation
```http
POST /api/v1/payments/callback/cancel?paymentId=pi_3abc123xyz
```

**Query Parameters:**
- `paymentId` (required): Provider payment ID

**Response:** `200 OK` with `PaymentResponseDto`

**Notes:**
- Updates payment status to CANCELLED
- Called when user cancels payment at gateway

---

### Payment Verification

#### 4. Verify Payment
```http
POST /api/v1/payments/callback/verify
Content-Type: application/json
```

**Request Body:**
```json
{
  "paymentId": "pi_3abc123xyz",
  "provider": "STRIPE",
  "signature": "t=1234567890,v1=abc123..."
}
```

**Response:** `200 OK`
```json
{
  "verified": true,
  "paymentId": "pi_3abc123xyz",
  "status": "COMPLETED",
  "amount": 150.00,
  "currency": "USD",
  "verifiedAt": "2024-12-06T13:40:55Z"
}
```

**Notes:**
- Verifies payment with gateway
- Validates signature
- Returns current payment status

---

### Redirect URLs

#### 5. Get Success Redirect URL
```http
GET /api/v1/payments/callback/redirect/success?paymentId=pi_3abc123xyz
```

**Response:** `200 OK`
```
https://example.com/payment/success?paymentId=pi_3abc123xyz&status=completed
```

---

#### 6. Get Failure Redirect URL
```http
GET /api/v1/payments/callback/redirect/failure?paymentId=pi_3abc123xyz&errorMessage=Card%20declined
```

**Response:** `200 OK`
```
https://example.com/payment/failure?paymentId=pi_3abc123xyz&error=Card%20declined
```

---

#### 7. Get Cancel Redirect URL
```http
GET /api/v1/payments/callback/redirect/cancel?paymentId=pi_3abc123xyz
```

**Response:** `200 OK`
```
https://example.com/payment/cancel?paymentId=pi_3abc123xyz
```

---

## 🔔 Payment Webhook API

**Base Path:** `/api/v1/payments/webhook`

### Gateway Webhooks

#### 1. Handle Stripe Webhook
```http
POST /api/v1/payments/webhook/stripe
Content-Type: application/json
Stripe-Signature: t=1234567890,v1=abc123...
```

**Request Body:** Raw Stripe webhook payload

**Response:** `202 Accepted` with `WebhookEventDto`

**Notes:**
- No authentication required (validated by signature)
- Processes Stripe events (payment_intent.succeeded, etc.)
- Asynchronous processing
- Returns immediately with event ID

---

#### 2. Handle PayPal Webhook
```http
POST /api/v1/payments/webhook/paypal
Content-Type: application/json
PAYPAL-TRANSMISSION-SIG: abc123...
```

**Request Body:** Raw PayPal webhook payload

**Response:** `202 Accepted` with `WebhookEventDto`

**Notes:**
- Validates PayPal signature
- Processes PayPal events (PAYMENT.CAPTURE.COMPLETED, etc.)

---

#### 3. Handle Razorpay Webhook
```http
POST /api/v1/payments/webhook/razorpay
Content-Type: application/json
X-Razorpay-Signature: abc123...
```

**Request Body:** Raw Razorpay webhook payload

**Response:** `202 Accepted` with `WebhookEventDto`

**Notes:**
- Validates Razorpay signature
- Processes Razorpay events (payment.captured, etc.)

---

### Webhook Management

#### 4. Retry Webhook Processing (Admin)
```http
POST /api/v1/payments/webhook/{eventId}/retry
Authorization: Bearer {token}
```

**Response:** `200 OK`

**Notes:**
- Admin-only operation
- Retries failed webhook processing
- Increments retry count

---

#### 5. Get Webhook Events by Payment ID (Admin)
```http
GET /api/v1/payments/webhook/payment/{paymentId}?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<WebhookEventDto>`

**Notes:**
- Admin-only operation
- Returns all webhook events for a payment
- Useful for debugging

---

#### 6. Get Webhook Events by Provider (Admin)
```http
GET /api/v1/payments/webhook/provider/{provider}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `provider`: STRIPE, PAYPAL, RAZORPAY

**Response:** `Page<WebhookEventDto>`

---

#### 7. Get Failed Webhook Events (Admin)
```http
GET /api/v1/payments/webhook/failed?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<WebhookEventDto>`

**Notes:**
- Admin-only operation
- Returns webhooks that failed processing
- Useful for monitoring and retry

---

## 📋 Payment Log API

**Base Path:** `/api/v1/payments/logs`

**Note:** All endpoints require ADMIN role

### Log Retrieval

#### 1. Get Payment Logs
```http
GET /api/v1/payments/logs/payment/{paymentId}?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<TransactionDetailsDto>`

**Notes:**
- Returns all transaction logs for a payment
- Includes request/response payloads
- Ordered by creation date (descending)

---

#### 2. Get Payment Logs List
```http
GET /api/v1/payments/logs/payment/{paymentId}/list
Authorization: Bearer {token}
```

**Response:** `List<TransactionDetailsDto>`

**Notes:**
- Returns complete log history (not paginated)
- Useful for detailed debugging

---

#### 3. Get Logs by Transaction Type
```http
GET /api/v1/payments/logs/type/{transactionType}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `transactionType`: PAYMENT_INTENT, PAYMENT_CONFIRM, REFUND, CANCEL, WEBHOOK, etc.

**Response:** `Page<TransactionDetailsDto>`

---

#### 4. Get Failed Transactions
```http
GET /api/v1/payments/logs/failed?page=0&size=20
Authorization: Bearer {token}
```

**Response:** `Page<TransactionDetailsDto>`

**Notes:**
- Returns logs with error status
- Useful for troubleshooting

---

### Log Maintenance

#### 5. Delete Old Payment Logs
```http
DELETE /api/v1/payments/logs/cleanup?daysToKeep=90
Authorization: Bearer {token}
```

**Query Parameters:**
- `daysToKeep` (optional, default: 90): Number of days to retain logs

**Response:** `200 OK`
```json
1547
```

**Notes:**
- Admin-only operation
- Returns number of deleted logs
- Helps manage database size
- Recommended to run periodically

---

## 🧪 Testing Guide

### Prerequisites

- Java 25+
- Maven 3.6+
- MySQL 8.0+
- Payment gateway test accounts (Stripe, PayPal, Razorpay)
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
   export STRIPE_SECRET_KEY="sk_test_..."
   export STRIPE_WEBHOOK_SECRET="whsec_..."
   export PAYPAL_CLIENT_ID="..."
   export PAYPAL_CLIENT_SECRET="..."
   export RAZORPAY_KEY_ID="..."
   export RAZORPAY_KEY_SECRET="..."
   ```

3. **Run Application:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### Example Test Scenarios

#### Scenario 1: Complete Payment Flow (Stripe)

```bash
# 1. Login as customer
CUSTOMER_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "customer@example.com",
    "password": "Password123!"
  }' | jq -r '.accessToken')

# 2. Create payment intent
PAYMENT_RESPONSE=$(curl -s -X POST http://localhost:8080/api/v1/payments/intent \
  -H "Authorization: Bearer $CUSTOMER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": 123,
    "amount": 150.00,
    "currency": "USD",
    "paymentMethod": "CARD",
    "paymentProvider": "STRIPE",
    "returnUrl": "https://example.com/success",
    "cancelUrl": "https://example.com/cancel"
  }')

PAYMENT_ID=$(echo $PAYMENT_RESPONSE | jq -r '.paymentId')
CLIENT_SECRET=$(echo $PAYMENT_RESPONSE | jq -r '.clientSecret')

echo "Payment ID: $PAYMENT_ID"
echo "Client Secret: $CLIENT_SECRET"

# 3. Simulate payment completion (in real scenario, use Stripe.js)
# This would be done on frontend with Stripe Elements

# 4. Check payment status
curl -X GET "http://localhost:8080/api/v1/payments/$PAYMENT_ID" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"
```

---

#### Scenario 2: Webhook Processing

```bash
# 1. Simulate Stripe webhook (normally sent by Stripe)
curl -X POST http://localhost:8080/api/v1/payments/webhook/stripe \
  -H "Content-Type: application/json" \
  -H "Stripe-Signature: t=1234567890,v1=test_signature" \
  -d '{
    "id": "evt_test_webhook",
    "type": "payment_intent.succeeded",
    "data": {
      "object": {
        "id": "pi_3abc123xyz",
        "amount": 15000,
        "currency": "usd",
        "status": "succeeded"
      }
    }
  }'

# 2. Login as admin
ADMIN_TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin@example.com",
    "password": "AdminPass123!"
  }' | jq -r '.accessToken')

# 3. Check webhook events
curl -X GET "http://localhost:8080/api/v1/payments/webhook/payment/pi_3abc123xyz?page=0&size=10" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 3: Refund Processing

```bash
# 1. Process refund (admin only)
curl -X POST http://localhost:8080/api/v1/payments/refund \
  -H "Authorization: Bearer $ADMIN_TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"paymentId\": $PAYMENT_ID,
    \"amount\": 150.00,
    \"reason\": \"Customer requested refund\",
    \"notifyCustomer\": true
  }"

# 2. Verify refund status
curl -X GET "http://localhost:8080/api/v1/payments/$PAYMENT_ID" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. Check payment logs
curl -X GET "http://localhost:8080/api/v1/payments/logs/payment/$PAYMENT_ID/list" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

#### Scenario 4: Payment Cancellation

```bash
# 1. Create payment intent
PAYMENT_ID=$(curl -s -X POST http://localhost:8080/api/v1/payments/intent \
  -H "Authorization: Bearer $CUSTOMER_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "orderId": 124,
    "amount": 75.00,
    "currency": "USD",
    "paymentMethod": "CARD",
    "paymentProvider": "STRIPE"
  }' | jq -r '.paymentId')

# 2. Cancel payment
curl -X POST "http://localhost:8080/api/v1/payments/$PAYMENT_ID/cancel" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"

# 3. Verify cancellation
curl -X GET "http://localhost:8080/api/v1/payments/$PAYMENT_ID" \
  -H "Authorization: Bearer $CUSTOMER_TOKEN"
```

---

#### Scenario 5: Admin Monitoring

```bash
# 1. Get all completed payments
curl -X GET "http://localhost:8080/api/v1/payments/status/COMPLETED?page=0&size=20" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 2. Get payments by date range
curl -X GET "http://localhost:8080/api/v1/payments/date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 3. Search payments
curl -X GET "http://localhost:8080/api/v1/payments/search?searchTerm=customer@example.com&page=0&size=20" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 4. Get failed webhook events
curl -X GET "http://localhost:8080/api/v1/payments/webhook/failed?page=0&size=20" \
  -H "Authorization: Bearer $ADMIN_TOKEN"

# 5. Get failed transactions
curl -X GET "http://localhost:8080/api/v1/payments/logs/failed?page=0&size=20" \
  -H "Authorization: Bearer $ADMIN_TOKEN"
```

---

## 📝 Best Practices

### Payment Processing

1. **Use payment intents** - Always create payment intent before processing
2. **Validate amounts** - Ensure amounts match order totals
3. **Handle idempotency** - Use idempotency keys for retry safety
4. **Secure client secrets** - Never expose client secrets in logs
5. **Implement timeouts** - Set reasonable timeouts for gateway calls

### Webhook Handling

1. **Verify signatures** - Always validate webhook signatures
2. **Process asynchronously** - Return 200 immediately, process in background
3. **Implement retry logic** - Handle failed webhook processing
4. **Log all events** - Maintain complete webhook history
5. **Monitor failures** - Alert on repeated webhook failures

### Security

1. **Encrypt sensitive data** - Protect card details and tokens
2. **Use HTTPS only** - Never process payments over HTTP
3. **Validate user permissions** - Ensure users can only access their payments
4. **Implement rate limiting** - Prevent payment API abuse
5. **PCI compliance** - Never store full card numbers

### Error Handling

1. **Provide clear error messages** - Help users understand failures
2. **Log all errors** - Maintain detailed error logs
3. **Implement fallbacks** - Handle gateway downtime gracefully
4. **Notify on failures** - Alert admins of critical payment failures
5. **Track error patterns** - Monitor for systematic issues

### Testing

1. **Use test mode** - Always test with gateway test credentials
2. **Test all scenarios** - Success, failure, cancellation, refunds
3. **Validate webhooks** - Test webhook signature validation
4. **Simulate errors** - Test error handling paths
5. **Load testing** - Ensure system handles payment volume

---

## 🔒 Security Notes

### Payment Data Protection

- **PCI DSS Compliance**: Never store full card numbers
- **Tokenization**: Use gateway tokens for card storage
- **Encryption**: Encrypt sensitive payment data at rest
- **TLS**: All payment communications over HTTPS
- **Audit Logs**: Complete transaction history

### Webhook Security

- **Signature Validation**: Verify all webhook signatures
- **IP Whitelisting**: Restrict webhook endpoints to gateway IPs
- **Replay Protection**: Prevent duplicate webhook processing
- **Rate Limiting**: Limit webhook endpoint requests

### Access Control

- **Customers**: Can only view/cancel their own payments
- **Admins**: Full access to all payments and refunds
- **API Keys**: Secure storage of gateway credentials
- **Token Expiration**: Payment intents expire after set time

---

## 📊 API Endpoints Summary

| Controller | Endpoints | Description |
|------------|-----------|-------------|
| PaymentController | 11 | Core payment operations and management |
| PaymentCallbackController | 7 | Payment callback and verification handling |
| PaymentWebhookController | 7 | Gateway webhook processing |
| PaymentLogController | 5 | Transaction log management |

**Total Endpoints:** 30

---

## 🔧 Payment Gateway Configuration

### Stripe

```yaml
payment:
  stripe:
    secret-key: ${STRIPE_SECRET_KEY}
    webhook-secret: ${STRIPE_WEBHOOK_SECRET}
    api-version: "2023-10-16"
```

### PayPal

```yaml
payment:
  paypal:
    client-id: ${PAYPAL_CLIENT_ID}
    client-secret: ${PAYPAL_CLIENT_SECRET}
    mode: sandbox # or live
```

### Razorpay

```yaml
payment:
  razorpay:
    key-id: ${RAZORPAY_KEY_ID}
    key-secret: ${RAZORPAY_KEY_SECRET}
```

---

**Version:** 1.0.0  
**Last Updated:** December 2024  
**Module:** Payment Processing  
**Platform:** Ceylon Wild Capture Backend
