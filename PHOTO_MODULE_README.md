# Photo Module Testing Guide

This guide provides comprehensive testing instructions for the Photo Category Module in the Ceylon Wild Capture backend.

## 📋 Table of Contents

1. [Prerequisites](#prerequisites)
2. [Category Management Endpoints](#category-management-endpoints)
3. [Testing with Postman](#testing-with-postman)
4. [Testing with curl](#testing-with-curl)
5. [Common Test Scenarios](#common-test-scenarios)
6. [Troubleshooting](#troubleshooting)

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

## 📂 Category Management Endpoints

### 1. Create Category
**Endpoint:** `POST /api/v1/categories`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Wildlife",
  "description": "Wildlife photography from Sri Lanka",
  "slug": "wildlife",
  "imageUrl": "https://example.com/categories/wildlife.jpg",
  "isActive": true,
  "displayOrder": 1
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Wildlife",
  "description": "Wildlife photography from Sri Lanka",
  "slug": "wildlife",
  "imageUrl": "https://example.com/categories/wildlife.jpg",
  "isActive": true,
  "displayOrder": 1,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

**Notes:**
- If `slug` is not provided, it will be automatically generated from the name
- If `isActive` is not provided, it defaults to `true`
- Category name and slug must be unique

### 2. Update Category
**Endpoint:** `PUT /api/v1/categories/{id}`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Wildlife Photography",
  "description": "Wildlife photography from Sri Lankan jungles",
  "slug": "wildlife-photography",
  "imageUrl": "https://example.com/categories/wildlife-updated.jpg",
  "isActive": true,
  "displayOrder": 1
}
```

**Response:**
```json
{
  "id": 1,
  "name": "Wildlife Photography",
  "description": "Wildlife photography from Sri Lankan jungles",
  "slug": "wildlife-photography",
  "imageUrl": "https://example.com/categories/wildlife-updated.jpg",
  "isActive": true,
  "displayOrder": 1,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:00:00"
}
```

### 3. Update Category Info (Partial Update)
**Endpoint:** `PATCH /api/v1/categories/{id}/info`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `name` (optional): New category name
- `description` (optional): New category description

**Example:**
```
PATCH /api/v1/categories/1/info?name=Wildlife&description=Updated description
```

**Response:**
```json
{
  "id": 1,
  "name": "Wildlife",
  "description": "Updated description",
  "slug": "wildlife-photography",
  "imageUrl": "https://example.com/categories/wildlife-updated.jpg",
  "isActive": true,
  "displayOrder": 1,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:30:00"
}
```

### 4. Update Category Image
**Endpoint:** `PATCH /api/v1/categories/{id}/image`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `imageUrl` (required): New image URL

**Example:**
```
PATCH /api/v1/categories/1/image?imageUrl=https://example.com/new-image.jpg
```

**Response:**
```json
{
  "id": 1,
  "name": "Wildlife",
  "description": "Updated description",
  "slug": "wildlife-photography",
  "imageUrl": "https://example.com/new-image.jpg",
  "isActive": true,
  "displayOrder": 1,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:45:00"
}
```

### 5. Update Category Display Order
**Endpoint:** `PATCH /api/v1/categories/{id}/order`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `displayOrder` (required): New display order (minimum: 0)

**Example:**
```
PATCH /api/v1/categories/1/order?displayOrder=5
```

**Response:**
```json
{
  "id": 1,
  "name": "Wildlife",
  "description": "Updated description",
  "slug": "wildlife-photography",
  "imageUrl": "https://example.com/new-image.jpg",
  "isActive": true,
  "displayOrder": 5,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T12:00:00"
}
```

### 6. Get Category by ID
**Endpoint:** `GET /api/v1/categories/{id}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "name": "Wildlife",
  "description": "Wildlife photography from Sri Lanka",
  "slug": "wildlife",
  "imageUrl": "https://example.com/categories/wildlife.jpg",
  "isActive": true,
  "displayOrder": 1,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

**Error Response (404):**
```json
{
  "timestamp": "2024-01-01T10:00:00",
  "status": 404,
  "error": "Not Found"
}
```

### 7. Get Category by Slug
**Endpoint:** `GET /api/v1/categories/slug/{slug}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
GET /api/v1/categories/slug/wildlife
```

**Response:**
```json
{
  "id": 1,
  "name": "Wildlife",
  "description": "Wildlife photography from Sri Lanka",
  "slug": "wildlife",
  "imageUrl": "https://example.com/categories/wildlife.jpg",
  "isActive": true,
  "displayOrder": 1,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### 8. Get Category by Name
**Endpoint:** `GET /api/v1/categories/name/{name}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
GET /api/v1/categories/name/Wildlife
```

**Response:**
```json
{
  "id": 1,
  "name": "Wildlife",
  "description": "Wildlife photography from Sri Lanka",
  "slug": "wildlife",
  "imageUrl": "https://example.com/categories/wildlife.jpg",
  "isActive": true,
  "displayOrder": 1,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### 9. Get All Categories (Paginated)
**Endpoint:** `GET /api/v1/categories?page=0&size=10&sort=displayOrder,asc`

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
      "name": "Wildlife",
      "description": "Wildlife photography from Sri Lanka",
      "slug": "wildlife",
      "imageUrl": "https://example.com/categories/wildlife.jpg",
      "isActive": true,
      "displayOrder": 1,
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00"
    },
    {
      "id": 2,
      "name": "Landscapes",
      "description": "Beautiful Sri Lankan landscapes",
      "slug": "landscapes",
      "imageUrl": "https://example.com/categories/landscapes.jpg",
      "isActive": true,
      "displayOrder": 2,
      "createdAt": "2024-01-01T10:05:00",
      "updatedAt": "2024-01-01T10:05:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 10,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    }
  },
  "totalElements": 2,
  "totalPages": 1,
  "last": true,
  "first": true,
  "numberOfElements": 2,
  "size": 10,
  "number": 0,
  "empty": false
}
```

### 10. Get Active Categories (Paginated)
**Endpoint:** `GET /api/v1/categories/active?page=0&size=10`

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
      "name": "Wildlife",
      "description": "Wildlife photography from Sri Lanka",
      "slug": "wildlife",
      "imageUrl": "https://example.com/categories/wildlife.jpg",
      "isActive": true,
      "displayOrder": 1,
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1
}
```

### 11. Get Active Categories (List)
**Endpoint:** `GET /api/v1/categories/active/list`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "Wildlife",
    "description": "Wildlife photography from Sri Lanka",
    "slug": "wildlife",
    "imageUrl": "https://example.com/categories/wildlife.jpg",
    "isActive": true,
    "displayOrder": 1,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  },
  {
    "id": 2,
    "name": "Landscapes",
    "description": "Beautiful Sri Lankan landscapes",
    "slug": "landscapes",
    "imageUrl": "https://example.com/categories/landscapes.jpg",
    "isActive": true,
    "displayOrder": 2,
    "createdAt": "2024-01-01T10:05:00",
    "updatedAt": "2024-01-01T10:05:00"
  }
]
```

### 12. Get Ordered Categories
**Endpoint:** `GET /api/v1/categories/ordered`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "Wildlife",
    "slug": "wildlife",
    "displayOrder": 1,
    "isActive": true
  },
  {
    "id": 2,
    "name": "Landscapes",
    "slug": "landscapes",
    "displayOrder": 2,
    "isActive": true
  }
]
```

### 13. Get Active Ordered Categories
**Endpoint:** `GET /api/v1/categories/active/ordered`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "Wildlife",
    "slug": "wildlife",
    "displayOrder": 1,
    "isActive": true
  },
  {
    "id": 3,
    "name": "Birds",
    "slug": "birds",
    "displayOrder": 3,
    "isActive": true
  }
]
```

### 14. Search Categories
**Endpoint:** `GET /api/v1/categories/search?q=wild&page=0&size=10`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `q` (required): Search term
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 10)

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "name": "Wildlife",
      "description": "Wildlife photography from Sri Lanka",
      "slug": "wildlife",
      "imageUrl": "https://example.com/categories/wildlife.jpg",
      "isActive": true,
      "displayOrder": 1,
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00"
    }
  ],
  "totalElements": 1,
  "totalPages": 1
}
```

### 15. Get Empty Categories
**Endpoint:** `GET /api/v1/categories/empty`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
[
  {
    "id": 5,
    "name": "New Category",
    "description": "Category with no photos",
    "slug": "new-category",
    "imageUrl": null,
    "isActive": true,
    "displayOrder": 10,
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  }
]
```

### 16. Get Categories with Photo Count
**Endpoint:** `GET /api/v1/categories/with-photo-count?page=0&size=10`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "content": [
    [
      {
        "id": 1,
        "name": "Wildlife",
        "slug": "wildlife"
      },
      25
    ],
    [
      {
        "id": 2,
        "name": "Landscapes",
        "slug": "landscapes"
      },
      18
    ]
  ],
  "totalElements": 2,
  "totalPages": 1
}
```

**Note:** Each element is an array where:
- Index 0: Category object
- Index 1: Photo count (Long)

### 17. Count Active Categories
**Endpoint:** `GET /api/v1/categories/counts/active`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
5
```

### 18. Count Total Categories
**Endpoint:** `GET /api/v1/categories/counts/total`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
7
```

### 19. Reorder Categories
**Endpoint:** `POST /api/v1/categories/reorder`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
[
  {
    "id": 1,
    "displayOrder": 3
  },
  {
    "id": 2,
    "displayOrder": 1
  },
  {
    "id": 3,
    "displayOrder": 2
  }
]
```

**Response:**
```json
[
  {
    "id": 2,
    "name": "Landscapes",
    "slug": "landscapes",
    "displayOrder": 1,
    "isActive": true
  },
  {
    "id": 3,
    "name": "Birds",
    "slug": "birds",
    "displayOrder": 2,
    "isActive": true
  },
  {
    "id": 1,
    "name": "Wildlife",
    "slug": "wildlife",
    "displayOrder": 3,
    "isActive": true
  }
]
```

### 20. Activate Category
**Endpoint:** `POST /api/v1/categories/{id}/activate`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "name": "Wildlife",
  "description": "Wildlife photography from Sri Lanka",
  "slug": "wildlife",
  "imageUrl": "https://example.com/categories/wildlife.jpg",
  "isActive": true,
  "displayOrder": 1,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T13:00:00"
}
```

### 21. Deactivate Category
**Endpoint:** `POST /api/v1/categories/{id}/deactivate`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "name": "Wildlife",
  "description": "Wildlife photography from Sri Lanka",
  "slug": "wildlife",
  "imageUrl": "https://example.com/categories/wildlife.jpg",
  "isActive": false,
  "displayOrder": 1,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T13:15:00"
}
```

### 22. Delete Category
**Endpoint:** `DELETE /api/v1/categories/{id}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** `204 No Content`

**Notes:**
- Cannot delete a category that has associated photos
- Returns `400 Bad Request` if category has photos

## 📮 Testing with Postman

### Step 1: Setup Environment
1. Create a new environment in Postman
2. Add variables:
   - `base_url`: `http://localhost:8080`
   - `jwt_token`: (will be set after login)

### Step 2: Login and Get Token
1. **Login Request:**
   - Method: POST
   - URL: `{{base_url}}/api/auth/login`
   - Body:
   ```json
   {
     "usernameOrEmail": "admin",
     "password": "password123"
   }
   ```
   - Tests script:
   ```javascript
   if (pm.response.code === 200) {
       const response = pm.response.json();
       pm.environment.set("jwt_token", response.accessToken);
   }
   ```

### Step 3: Test Category Endpoints
All category endpoints require authentication. Add to Headers:
- Key: `Authorization`
- Value: `Bearer {{jwt_token}}`

### Step 4: Example Test Collection

#### Create Category Test
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Category created with correct name", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.name).to.eql("Wildlife");
});

pm.test("Slug is auto-generated", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData.slug).to.exist;
});

// Save category ID for future tests
if (pm.response.code === 200) {
    const response = pm.response.json();
    pm.environment.set("category_id", response.id);
}
```

#### Get Category Test
```javascript
pm.test("Status code is 200", function () {
    pm.response.to.have.status(200);
});

pm.test("Category has all required fields", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('id');
    pm.expect(jsonData).to.have.property('name');
    pm.expect(jsonData).to.have.property('slug');
    pm.expect(jsonData).to.have.property('isActive');
});
```

## 📋 Testing with curl

### Setup
```bash
# Login and save token
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin",
    "password": "password123"
  }' | jq -r '.accessToken')

echo "Token: $TOKEN"
```

### Category Operations

#### Create Category
```bash
curl -X POST http://localhost:8080/api/v1/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Wildlife",
    "description": "Wildlife photography from Sri Lanka",
    "imageUrl": "https://example.com/wildlife.jpg",
    "isActive": true,
    "displayOrder": 1
  }' | jq
```

#### Update Category
```bash
curl -X PUT http://localhost:8080/api/v1/categories/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Wildlife Photography",
    "description": "Updated description",
    "slug": "wildlife-photography",
    "isActive": true,
    "displayOrder": 1
  }' | jq
```

#### Update Category Info
```bash
curl -X PATCH "http://localhost:8080/api/v1/categories/1/info?name=Wildlife&description=New%20description" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Update Category Image
```bash
curl -X PATCH "http://localhost:8080/api/v1/categories/1/image?imageUrl=https://example.com/new-image.jpg" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Update Display Order
```bash
curl -X PATCH "http://localhost:8080/api/v1/categories/1/order?displayOrder=5" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get Category by ID
```bash
curl -X GET http://localhost:8080/api/v1/categories/1 \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get Category by Slug
```bash
curl -X GET http://localhost:8080/api/v1/categories/slug/wildlife \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get All Categories (Paginated)
```bash
curl -X GET "http://localhost:8080/api/v1/categories?page=0&size=10&sort=displayOrder,asc" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get Active Categories
```bash
curl -X GET "http://localhost:8080/api/v1/categories/active?page=0&size=10" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get Active Categories List
```bash
curl -X GET http://localhost:8080/api/v1/categories/active/list \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Search Categories
```bash
curl -X GET "http://localhost:8080/api/v1/categories/search?q=wild&page=0&size=10" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get Empty Categories
```bash
curl -X GET http://localhost:8080/api/v1/categories/empty \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Count Categories
```bash
# Count active categories
curl -X GET http://localhost:8080/api/v1/categories/counts/active \
  -H "Authorization: Bearer $TOKEN"

# Count total categories
curl -X GET http://localhost:8080/api/v1/categories/counts/total \
  -H "Authorization: Bearer $TOKEN"
```

#### Reorder Categories
```bash
curl -X POST http://localhost:8080/api/v1/categories/reorder \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '[
    {"id": 1, "displayOrder": 3},
    {"id": 2, "displayOrder": 1},
    {"id": 3, "displayOrder": 2}
  ]' | jq
```

#### Activate/Deactivate Category
```bash
# Activate
curl -X POST http://localhost:8080/api/v1/categories/1/activate \
  -H "Authorization: Bearer $TOKEN" | jq

# Deactivate
curl -X POST http://localhost:8080/api/v1/categories/1/deactivate \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Delete Category
```bash
curl -X DELETE http://localhost:8080/api/v1/categories/1 \
  -H "Authorization: Bearer $TOKEN" -v
```

## 🧪 Common Test Scenarios

### Scenario 1: Complete Category Lifecycle
```bash
# 1. Create category
CATEGORY_ID=$(curl -s -X POST http://localhost:8080/api/v1/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Category",
    "description": "Test description"
  }' | jq -r '.id')

echo "Created category ID: $CATEGORY_ID"

# 2. Update category
curl -X PUT http://localhost:8080/api/v1/categories/$CATEGORY_ID \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Updated Test Category",
    "description": "Updated description",
    "isActive": true
  }'

# 3. Get category
curl -X GET http://localhost:8080/api/v1/categories/$CATEGORY_ID \
  -H "Authorization: Bearer $TOKEN"

# 4. Deactivate category
curl -X POST http://localhost:8080/api/v1/categories/$CATEGORY_ID/deactivate \
  -H "Authorization: Bearer $TOKEN"

# 5. Delete category
curl -X DELETE http://localhost:8080/api/v1/categories/$CATEGORY_ID \
  -H "Authorization: Bearer $TOKEN"
```

### Scenario 2: Duplicate Name/Slug Validation
```bash
# Create first category
curl -X POST http://localhost:8080/api/v1/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Wildlife",
    "slug": "wildlife"
  }'

# Try to create duplicate (should fail with 400)
curl -X POST http://localhost:8080/api/v1/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Wildlife",
    "slug": "wildlife"
  }'
```

### Scenario 3: Slug Auto-Generation
```bash
# Create category without slug
curl -X POST http://localhost:8080/api/v1/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sri Lankan Wildlife"
  }' | jq

# Expected slug: "sri-lankan-wildlife"
```

### Scenario 4: Category Ordering
```bash
# Create multiple categories
for i in {1..5}; do
  curl -s -X POST http://localhost:8080/api/v1/categories \
    -H "Authorization: Bearer $TOKEN" \
    -H "Content-Type: application/json" \
    -d "{
      \"name\": \"Category $i\",
      \"displayOrder\": $i
    }"
done

# Get ordered categories
curl -X GET http://localhost:8080/api/v1/categories/ordered \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Scenario 5: Search Functionality
```bash
# Create test categories
curl -X POST http://localhost:8080/api/v1/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "Wildlife Photography"}'

curl -X POST http://localhost:8080/api/v1/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name": "Landscape Photography"}'

# Search for "photo"
curl -X GET "http://localhost:8080/api/v1/categories/search?q=photo" \
  -H "Authorization: Bearer $TOKEN" | jq
```

## 🔧 Troubleshooting

### Common Issues

#### 1. Cannot Delete Category with Photos
**Problem:** `400 Bad Request` - "Cannot delete category with associated photos"
**Solution:**
- Delete or reassign all photos in the category first
- Or deactivate the category instead of deleting

#### 2. Duplicate Slug/Name Error
**Problem:** `400 Bad Request` - "Category name already exists"
**Solution:**
- Use a unique name
- If updating, ensure the new name doesn't conflict with existing categories

#### 3. Invalid Display Order
**Problem:** `400 Bad Request` - Display order must be >= 0
**Solution:**
- Ensure displayOrder is a non-negative integer

#### 4. Category Not Found
**Problem:** `404 Not Found`
**Solution:**
- Verify the category ID exists
- Check if category was deleted
- Ensure you're using the correct endpoint

#### 5. Unauthorized Access
**Problem:** `401 Unauthorized`
**Solution:**
- Check JWT token is valid and not expired
- Ensure proper Bearer token format
- Re-login if token expired

### Debug Tips

1. **Enable Debug Logging:**
   ```yaml
   logging:
     level:
       lk.ceylonwildcapture_backend.modules.photo: DEBUG
   ```

2. **Check Category Exists:**
   ```sql
   SELECT * FROM categories WHERE id = 1;
   ```

3. **Verify Slug Uniqueness:**
   ```sql
   SELECT slug, COUNT(*) FROM categories GROUP BY slug HAVING COUNT(*) > 1;
   ```

4. **Check Photo Associations:**
   ```sql
   SELECT c.name, COUNT(p.id) as photo_count
   FROM categories c
   LEFT JOIN photos p ON c.id = p.category_id
   GROUP BY c.id, c.name;
   ```

## 📊 Test Data

### Sample Categories (SQL)
```sql
-- Wildlife Category
INSERT INTO categories (name, description, slug, image_url, is_active, display_order, created_at, updated_at)
VALUES ('Wildlife', 'Wildlife photography from Sri Lankan jungles', 'wildlife', 'https://example.com/wildlife.jpg', true, 1, NOW(), NOW());

-- Landscape Category
INSERT INTO categories (name, description, slug, image_url, is_active, display_order, created_at, updated_at)
VALUES ('Landscapes', 'Beautiful Sri Lankan landscapes', 'landscapes', 'https://example.com/landscapes.jpg', true, 2, NOW(), NOW());

-- Birds Category
INSERT INTO categories (name, description, slug, image_url, is_active, display_order, created_at, updated_at)
VALUES ('Birds', 'Endemic and migratory birds of Sri Lanka', 'birds', 'https://example.com/birds.jpg', true, 3, NOW(), NOW());

-- Marine Life Category
INSERT INTO categories (name, description, slug, image_url, is_active, display_order, created_at, updated_at)
VALUES ('Marine Life', 'Underwater photography from Sri Lankan coasts', 'marine-life', 'https://example.com/marine.jpg', true, 4, NOW(), NOW());

-- Cultural Category
INSERT INTO categories (name, description, slug, image_url, is_active, display_order, created_at, updated_at)
VALUES ('Cultural', 'Sri Lankan culture and heritage', 'cultural', 'https://example.com/cultural.jpg', true, 5, NOW(), NOW());
```

## 📝 API Documentation

Once the application is running, you can access:
- **Swagger UI:** `http://localhost:8080/swagger-ui.html`
- **OpenAPI Spec:** `http://localhost:8080/v3/api-docs`

## 🚨 Validation Rules

### Category Name
- **Required:** Yes
- **Minimum length:** 1 character
- **Maximum length:** 255 characters
- **Unique:** Yes
- **Example:** "Wildlife", "Landscapes"

### Category Slug
- **Required:** No (auto-generated if not provided)
- **Format:** lowercase, alphanumeric with hyphens
- **Unique:** Yes
- **Example:** "wildlife", "sri-lankan-landscapes"

### Display Order
- **Required:** No
- **Type:** Integer
- **Minimum:** 0
- **Default:** null
- **Example:** 1, 5, 10

### Image URL
- **Required:** No
- **Format:** Valid URL string
- **Example:** "https://example.com/image.jpg"

## 📞 Support

For issues or questions:
1. Check application logs in `logs/` directory
2. Verify database connectivity
3. Validate JWT token
4. Review validation constraints
5. Check category associations (photos)

## 🎯 Best Practices

1. **Always use unique slugs** - Helps with SEO and URL structure
2. **Set display order** - Controls category ordering in UI
3. **Use descriptive names** - Makes categories easy to understand
4. **Add images** - Enhances visual presentation
5. **Keep categories active** - Deactivate instead of delete when possible
6. **Regular cleanup** - Remove empty categories periodically

---

**Happy Testing! 🎉**
