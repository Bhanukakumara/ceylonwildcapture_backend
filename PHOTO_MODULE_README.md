# Photo Module Testing Guide

This guide provides comprehensive testing instructions for the Photo Category Module in the Ceylon Wild Capture backend.

## 📋 Table of Contents

1. [Prerequisites](#prerequisites)
2. [Category Management Endpoints](#category-management-endpoints)
3. [Tag Management Endpoints](#tag-management-endpoints)
4. [Testing with Postman](#testing-with-postman)
5. [Testing with curl](#testing-with-curl)
6. [Common Test Scenarios](#common-test-scenarios)
7. [Troubleshooting](#troubleshooting)

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

---

## 🏷️ Tag Management Endpoints

### 1. Create Tag
**Endpoint:** `POST /api/v1/tags`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "leopard",
  "description": "Photos featuring leopards"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "leopard",
  "description": "Photos featuring leopards",
  "usageCount": 0,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

**Notes:**
- Tag names are automatically normalized to lowercase
- Tag names must be unique
- Description is optional

### 2. Create Tag by Name (Simple)
**Endpoint:** `POST /api/v1/tags/simple?name={tagName}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
POST /api/v1/tags/simple?name=elephant
```

**Response:**
```json
{
  "id": 2,
  "name": "elephant",
  "description": null,
  "usageCount": 0,
  "createdAt": "2024-01-01T10:05:00",
  "updatedAt": "2024-01-01T10:05:00"
}
```

### 3. Update Tag
**Endpoint:** `PUT /api/v1/tags/{id}`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "sri-lankan-leopard",
  "description": "Photos of Sri Lankan leopards (Panthera pardus kotiya)"
}
```

**Response:**
```json
{
  "id": 1,
  "name": "sri-lankan-leopard",
  "description": "Photos of Sri Lankan leopards (Panthera pardus kotiya)",
  "usageCount": 0,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:30:00"
}
```

**Notes:**
- Both name and description are optional in update
- Only provided fields will be updated

### 4. Update Tag Description
**Endpoint:** `PATCH /api/v1/tags/{id}/description?description={description}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
PATCH /api/v1/tags/1/description?description=Updated%20description
```

**Response:**
```json
{
  "id": 1,
  "name": "sri-lankan-leopard",
  "description": "Updated description",
  "usageCount": 0,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:45:00"
}
```

### 5. Rename Tag
**Endpoint:** `PATCH /api/v1/tags/{id}/rename?newName={newName}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
PATCH /api/v1/tags/1/rename?newName=leopard-kotiya
```

**Response:**
```json
{
  "id": 1,
  "name": "leopard-kotiya",
  "description": "Updated description",
  "usageCount": 0,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T11:00:00"
}
```

### 6. Get Tag by ID
**Endpoint:** `GET /api/v1/tags/{id}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "name": "leopard",
  "description": "Photos featuring leopards",
  "usageCount": 15,
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

### 7. Get Tag by Name
**Endpoint:** `GET /api/v1/tags/name/{name}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
GET /api/v1/tags/name/leopard
```

**Response:**
```json
{
  "id": 1,
  "name": "leopard",
  "description": "Photos featuring leopards",
  "usageCount": 15,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

### 8. Get All Tags (Paginated)
**Endpoint:** `GET /api/v1/tags?page=0&size=10&sort=name,asc`

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
      "name": "elephant",
      "description": null,
      "usageCount": 25,
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00"
    },
    {
      "id": 2,
      "name": "leopard",
      "description": "Photos featuring leopards",
      "usageCount": 15,
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

### 9. Get Top Tags
**Endpoint:** `GET /api/v1/tags/top?limit={limit}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `limit` (optional): Number of top tags to return (default: 10, minimum: 1)

**Example:**
```
GET /api/v1/tags/top?limit=5
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "wildlife",
    "description": null,
    "usageCount": 150,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  },
  {
    "id": 2,
    "name": "elephant",
    "description": null,
    "usageCount": 75,
    "createdAt": "2024-01-01T10:05:00",
    "updatedAt": "2024-01-01T10:05:00"
  },
  {
    "id": 3,
    "name": "leopard",
    "description": "Photos featuring leopards",
    "usageCount": 50,
    "createdAt": "2024-01-01T10:10:00",
    "updatedAt": "2024-01-01T10:10:00"
  }
]
```

**Notes:**
- Returns tags ordered by usage count (descending)
- Useful for displaying popular tags

### 10. Get Popular Tags
**Endpoint:** `GET /api/v1/tags/popular?minUsageCount={count}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `minUsageCount` (optional): Minimum usage count threshold (default: 1, minimum: 0)

**Example:**
```
GET /api/v1/tags/popular?minUsageCount=10
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "wildlife",
    "description": null,
    "usageCount": 150,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  },
  {
    "id": 2,
    "name": "elephant",
    "description": null,
    "usageCount": 75,
    "createdAt": "2024-01-01T10:05:00",
    "updatedAt": "2024-01-01T10:05:00"
  }
]
```

**Notes:**
- Returns all tags with usage count >= minUsageCount
- Ordered by usage count descending

### 11. Get Unused Tags
**Endpoint:** `GET /api/v1/tags/unused`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
[
  {
    "id": 10,
    "name": "new-tag",
    "description": null,
    "usageCount": 0,
    "createdAt": "2024-01-01T15:00:00",
    "updatedAt": "2024-01-01T15:00:00"
  },
  {
    "id": 11,
    "name": "unused-tag",
    "description": "Never been used",
    "usageCount": 0,
    "createdAt": "2024-01-01T15:05:00",
    "updatedAt": "2024-01-01T15:05:00"
  }
]
```

**Notes:**
- Returns tags with usageCount = 0
- Useful for cleanup operations

### 12. Search Tags
**Endpoint:** `GET /api/v1/tags/search?q={searchTerm}&page=0&size=10`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `q` (required): Search term
- `page` (optional): Page number (default: 0)
- `size` (optional): Page size (default: 10)

**Example:**
```
GET /api/v1/tags/search?q=leo&page=0&size=10
```

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "name": "leopard",
      "description": "Photos featuring leopards",
      "usageCount": 50,
      "createdAt": "2024-01-01T10:00:00",
      "updatedAt": "2024-01-01T10:00:00"
    },
    {
      "id": 5,
      "name": "sri-lankan-leopard",
      "description": "Endemic leopard subspecies",
      "usageCount": 30,
      "createdAt": "2024-01-01T11:00:00",
      "updatedAt": "2024-01-01T11:00:00"
    }
  ],
  "totalElements": 2,
  "totalPages": 1
}
```

**Notes:**
- Searches in both tag name and description
- Case-insensitive search

### 13. Get Tags by IDs
**Endpoint:** `POST /api/v1/tags/by-ids`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
[1, 2, 5, 10]
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "leopard",
    "description": "Photos featuring leopards",
    "usageCount": 50,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  },
  {
    "id": 2,
    "name": "elephant",
    "description": null,
    "usageCount": 75,
    "createdAt": "2024-01-01T10:05:00",
    "updatedAt": "2024-01-01T10:05:00"
  }
]
```

**Notes:**
- Returns only tags that exist
- Non-existent IDs are silently ignored

### 14. Get Tags by Names
**Endpoint:** `POST /api/v1/tags/by-names`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
["leopard", "elephant", "whale", "nonexistent"]
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "leopard",
    "description": "Photos featuring leopards",
    "usageCount": 50,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  },
  {
    "id": 2,
    "name": "elephant",
    "description": null,
    "usageCount": 75,
    "createdAt": "2024-01-01T10:05:00",
    "updatedAt": "2024-01-01T10:05:00"
  },
  {
    "id": 8,
    "name": "whale",
    "description": "Marine mammals",
    "usageCount": 20,
    "createdAt": "2024-01-01T12:00:00",
    "updatedAt": "2024-01-01T12:00:00"
  }
]
```

**Notes:**
- Tag names are case-insensitive
- Non-existent tags are ignored

### 15. Get or Create Tags by Names
**Endpoint:** `POST /api/v1/tags/get-or-create`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
["leopard", "new-species", "elephant"]
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "leopard",
    "description": "Photos featuring leopards",
    "usageCount": 50,
    "createdAt": "2024-01-01T10:00:00",
    "updatedAt": "2024-01-01T10:00:00"
  },
  {
    "id": 15,
    "name": "new-species",
    "description": null,
    "usageCount": 0,
    "createdAt": "2024-01-01T16:00:00",
    "updatedAt": "2024-01-01T16:00:00"
  },
  {
    "id": 2,
    "name": "elephant",
    "description": null,
    "usageCount": 75,
    "createdAt": "2024-01-01T10:05:00",
    "updatedAt": "2024-01-01T10:05:00"
  }
]
```

**Notes:**
- Creates tags if they don't exist
- Returns existing tags if they already exist
- Very useful for bulk tag operations

### 16. Count Total Tags
**Endpoint:** `GET /api/v1/tags/count`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
42
```

### 17. Increment Usage Count
**Endpoint:** `POST /api/v1/tags/{id}/increment-usage`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "name": "leopard",
  "description": "Photos featuring leopards",
  "usageCount": 51,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T16:30:00"
}
```

**Notes:**
- Increases usage count by 1
- Call when a tag is assigned to a photo

### 18. Decrement Usage Count
**Endpoint:** `POST /api/v1/tags/{id}/decrement-usage`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "id": 1,
  "name": "leopard",
  "description": "Photos featuring leopards",
  "usageCount": 50,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T16:35:00"
}
```

**Notes:**
- Decreases usage count by 1
- Cannot go below 0
- Call when a tag is removed from a photo

### 19. Recalculate Usage Counts
**Endpoint:** `POST /api/v1/tags/recalculate-usage`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
25
```

**Notes:**
- Returns the number of tags updated
- Recalculates usage count based on actual photo_tags associations
- Use when usage counts become inconsistent

### 20. Merge Tags
**Endpoint:** `POST /api/v1/tags/merge`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "sourceTagIds": [5, 10, 15],
  "targetTagId": 1
}
```

**Response:**
```json
{
  "id": 1,
  "name": "leopard",
  "description": "Photos featuring leopards",
  "usageCount": 125,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T17:00:00"
}
```

**Notes:**
- Merges multiple tags into one target tag
- All photo associations are moved to target tag
- Source tags are deleted after merge
- Usage count is recalculated
- Useful for consolidating duplicate or similar tags

### 21. Delete Tag
**Endpoint:** `DELETE /api/v1/tags/{id}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** `204 No Content`

**Notes:**
- Deletes the tag and all its photo associations
- Cannot be undone

### 22. Delete Unused Tags
**Endpoint:** `DELETE /api/v1/tags/unused`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
5
```

**Notes:**
- Returns the number of deleted tags
- Only deletes tags with usageCount = 0
- Useful for cleanup operations

### 23. Check Tag Exists
**Endpoint:** `GET /api/v1/tags/exists?name={tagName}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
GET /api/v1/tags/exists?name=leopard
```

**Response:**
```json
true
```

**Notes:**
- Returns `true` if tag exists, `false` otherwise
- Tag name is case-insensitive

---

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

---

### Tag Operations

#### Create Tag
```bash
curl -X POST http://localhost:8080/api/v1/tags \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "leopard",
    "description": "Photos featuring leopards"
  }' | jq
```

#### Create Tag (Simple)
```bash
curl -X POST "http://localhost:8080/api/v1/tags/simple?name=elephant" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Update Tag
```bash
curl -X PUT http://localhost:8080/api/v1/tags/1 \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "sri-lankan-leopard",
    "description": "Endemic leopard subspecies"
  }' | jq
```

#### Update Tag Description
```bash
curl -X PATCH "http://localhost:8080/api/v1/tags/1/description?description=Updated%20description" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Rename Tag
```bash
curl -X PATCH "http://localhost:8080/api/v1/tags/1/rename?newName=leopard-kotiya" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get Tag by ID
```bash
curl -X GET http://localhost:8080/api/v1/tags/1 \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get Tag by Name
```bash
curl -X GET http://localhost:8080/api/v1/tags/name/leopard \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get All Tags (Paginated)
```bash
curl -X GET "http://localhost:8080/api/v1/tags?page=0&size=10&sort=name,asc" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get Top Tags
```bash
curl -X GET "http://localhost:8080/api/v1/tags/top?limit=5" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get Popular Tags
```bash
curl -X GET "http://localhost:8080/api/v1/tags/popular?minUsageCount=10" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get Unused Tags
```bash
curl -X GET http://localhost:8080/api/v1/tags/unused \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Search Tags
```bash
curl -X GET "http://localhost:8080/api/v1/tags/search?q=leo&page=0&size=10" \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Get Tags by IDs
```bash
curl -X POST http://localhost:8080/api/v1/tags/by-ids \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '[1, 2, 5, 10]' | jq
```

#### Get Tags by Names
```bash
curl -X POST http://localhost:8080/api/v1/tags/by-names \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '["leopard", "elephant", "whale"]' | jq
```

#### Get or Create Tags by Names
```bash
curl -X POST http://localhost:8080/api/v1/tags/get-or-create \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '["leopard", "new-species", "elephant"]' | jq
```

#### Count Total Tags
```bash
curl -X GET http://localhost:8080/api/v1/tags/count \
  -H "Authorization: Bearer $TOKEN"
```

#### Increment Usage Count
```bash
curl -X POST http://localhost:8080/api/v1/tags/1/increment-usage \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Decrement Usage Count
```bash
curl -X POST http://localhost:8080/api/v1/tags/1/decrement-usage \
  -H "Authorization: Bearer $TOKEN" | jq
```

#### Recalculate Usage Counts
```bash
curl -X POST http://localhost:8080/api/v1/tags/recalculate-usage \
  -H "Authorization: Bearer $TOKEN"
```

#### Merge Tags
```bash
curl -X POST http://localhost:8080/api/v1/tags/merge \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "sourceTagIds": [5, 10, 15],
    "targetTagId": 1
  }' | jq
```

#### Delete Tag
```bash
curl -X DELETE http://localhost:8080/api/v1/tags/1 \
  -H "Authorization: Bearer $TOKEN" -v
```

#### Delete Unused Tags
```bash
curl -X DELETE http://localhost:8080/api/v1/tags/unused \
  -H "Authorization: Bearer $TOKEN"
```

#### Check Tag Exists
```bash
curl -X GET "http://localhost:8080/api/v1/tags/exists?name=leopard" \
  -H "Authorization: Bearer $TOKEN"
```

---

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

### Scenario 6: Tag Lifecycle
```bash
# 1. Create multiple tags
TAG1_ID=$(curl -s -X POST http://localhost:8080/api/v1/tags \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "leopard",
    "description": "Photos featuring leopards"
  }' | jq -r '.id')

TAG2_ID=$(curl -s -X POST "http://localhost:8080/api/v1/tags/simple?name=elephant" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.id')

echo "Created tag IDs: $TAG1_ID, $TAG2_ID"

# 2. Update tag
curl -X PUT http://localhost:8080/api/v1/tags/$TAG1_ID \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "sri-lankan-leopard",
    "description": "Endemic leopard subspecies"
  }'

# 3. Increment usage count (simulating photo tagging)
curl -X POST http://localhost:8080/api/v1/tags/$TAG1_ID/increment-usage \
  -H "Authorization: Bearer $TOKEN"

# 4. Get tag details
curl -X GET http://localhost:8080/api/v1/tags/$TAG1_ID \
  -H "Authorization: Bearer $TOKEN"

# 5. Search tags
curl -X GET "http://localhost:8080/api/v1/tags/search?q=leopard" \
  -H "Authorization: Bearer $TOKEN"

# 6. Delete tag
curl -X DELETE http://localhost:8080/api/v1/tags/$TAG2_ID \
  -H "Authorization: Bearer $TOKEN"
```

### Scenario 7: Tag Name Normalization
```bash
# Create tag with mixed case
curl -X POST http://localhost:8080/api/v1/tags \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sri-Lankan-Leopard"
  }' | jq

# Name is automatically normalized to lowercase: "sri-lankan-leopard"
```

### Scenario 8: Get or Create Tags (Bulk Operations)
```bash
# Request multiple tags, some exist, some don't
curl -X POST http://localhost:8080/api/v1/tags/get-or-create \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '["leopard", "elephant", "whale", "new-species"]' | jq

# Returns existing tags + creates new ones automatically
```

### Scenario 9: Tag Merge Operation
```bash
# Create duplicate/similar tags
TAG1=$(curl -s -X POST "http://localhost:8080/api/v1/tags/simple?name=leopard" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.id')

TAG2=$(curl -s -X POST "http://localhost:8080/api/v1/tags/simple?name=leopards" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.id')

TAG3=$(curl -s -X POST "http://localhost:8080/api/v1/tags/simple?name=panthera-pardus" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.id')

# Merge all into TAG1
curl -X POST http://localhost:8080/api/v1/tags/merge \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d "{
    \"sourceTagIds\": [$TAG2, $TAG3],
    \"targetTagId\": $TAG1
  }" | jq

# TAG2 and TAG3 are deleted, all associations moved to TAG1
```

### Scenario 10: Tag Cleanup Operations
```bash
# Get unused tags
curl -X GET http://localhost:8080/api/v1/tags/unused \
  -H "Authorization: Bearer $TOKEN" | jq

# Delete all unused tags
DELETED_COUNT=$(curl -s -X DELETE http://localhost:8080/api/v1/tags/unused \
  -H "Authorization: Bearer $TOKEN")

echo "Deleted $DELETED_COUNT unused tags"
```

### Scenario 11: Popular Tags
```bash
# Get top 10 most used tags
curl -X GET "http://localhost:8080/api/v1/tags/top?limit=10" \
  -H "Authorization: Bearer $TOKEN" | jq

# Get tags with at least 20 usages
curl -X GET "http://localhost:8080/api/v1/tags/popular?minUsageCount=20" \
  -H "Authorization: Bearer $TOKEN" | jq
```

### Scenario 12: Tag Usage Count Management
```bash
# Simulate photo tagging operations
TAG_ID=1

# When tag is added to a photo
curl -X POST http://localhost:8080/api/v1/tags/$TAG_ID/increment-usage \
  -H "Authorization: Bearer $TOKEN"

# When tag is removed from a photo
curl -X POST http://localhost:8080/api/v1/tags/$TAG_ID/decrement-usage \
  -H "Authorization: Bearer $TOKEN"

# If counts become inconsistent, recalculate all
curl -X POST http://localhost:8080/api/v1/tags/recalculate-usage \
  -H "Authorization: Bearer $TOKEN"
```

## 🔧 Troubleshooting

### Common Issues

#### 1. Cannot Delete Category with Photos
**Problem:** `400 Bad Request` - "Cannot delete category with associated photos"
**Solution:**
- Delete or reassign all photos in the category first
- Or deactivate the category instead of deleting

#### 2. Duplicate Slug/Name Error (Category)
**Problem:** `400 Bad Request` - "Category name already exists"
**Solution:**
- Use a unique name
- If updating, ensure the new name doesn't conflict with existing categories

#### 3. Duplicate Tag Name
**Problem:** `400 Bad Request` - "Tag name already exists"
**Solution:**
- Tag names are unique and case-insensitive
- Use a different name or retrieve the existing tag
- Consider using the merge endpoint to consolidate similar tags

#### 4. Invalid Display Order
**Problem:** `400 Bad Request` - Display order must be >= 0
**Solution:**
- Ensure displayOrder is a non-negative integer

#### 5. Category/Tag Not Found
**Problem:** `404 Not Found`
**Solution:**
- Verify the category/tag ID exists
- Check if it was deleted
- Ensure you're using the correct endpoint

#### 6. Tag Merge Failure
**Problem:** `400 Bad Request` - "Cannot merge tag into itself" or "Source tag not found"
**Solution:**
- Ensure target tag is not in the source list
- Verify all source and target tag IDs exist
- Check that source and target tags are different

#### 7. Unauthorized Access
**Problem:** `401 Unauthorized`
**Solution:**
- Check JWT token is valid and not expired
- Ensure proper Bearer token format
- Re-login if token expired

#### 8. Usage Count Inconsistency
**Problem:** Tag usage counts don't match actual photo associations
**Solution:**
- Use the recalculate usage endpoint: `POST /api/v1/tags/recalculate-usage`
- This will sync all counts with actual database associations

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

3. **Check Tag Exists:**
   ```sql
   SELECT * FROM tags WHERE id = 1;
   ```

4. **Verify Slug Uniqueness:**
   ```sql
   SELECT slug, COUNT(*) FROM categories GROUP BY slug HAVING COUNT(*) > 1;
   ```

5. **Verify Tag Name Uniqueness:**
   ```sql
   SELECT name, COUNT(*) FROM tags GROUP BY name HAVING COUNT(*) > 1;
   ```

6. **Check Photo-Category Associations:**
   ```sql
   SELECT c.name, COUNT(p.id) as photo_count
   FROM categories c
   LEFT JOIN photos p ON c.id = p.category_id
   GROUP BY c.id, c.name;
   ```

7. **Check Photo-Tag Associations:**
   ```sql
   SELECT t.name, t.usage_count, COUNT(pt.photo_id) as actual_count
   FROM tags t
   LEFT JOIN photo_tags pt ON t.id = pt.tag_id
   GROUP BY t.id, t.name, t.usage_count;
   ```

8. **Find Inconsistent Tag Usage Counts:**
   ```sql
   SELECT t.id, t.name, t.usage_count, COUNT(pt.photo_id) as actual_count
   FROM tags t
   LEFT JOIN photo_tags pt ON t.id = pt.tag_id
   GROUP BY t.id, t.name, t.usage_count
   HAVING t.usage_count != COUNT(pt.photo_id);
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

### Sample Tags (SQL)
```sql
-- Wildlife Tags
INSERT INTO tags (name, description, usage_count, created_at, updated_at)
VALUES 
  ('leopard', 'Sri Lankan leopard (Panthera pardus kotiya)', 0, NOW(), NOW()),
  ('elephant', 'Asian elephant (Elephas maximus)', 0, NOW(), NOW()),
  ('sloth-bear', 'Sloth bear (Melursus ursinus)', 0, NOW(), NOW()),
  ('spotted-deer', 'Spotted deer (Axis axis)', 0, NOW(), NOW()),
  ('sambhur', 'Sambhur deer (Rusa unicolor)', 0, NOW(), NOW());

-- Bird Tags
INSERT INTO tags (name, description, usage_count, created_at, updated_at)
VALUES 
  ('peacock', 'Indian peafowl', 0, NOW(), NOW()),
  ('hornbill', 'Sri Lanka grey hornbill', 0, NOW(), NOW()),
  ('kingfisher', 'Various kingfisher species', 0, NOW(), NOW()),
  ('eagle', 'Crested serpent eagle', 0, NOW(), NOW());

-- Marine Tags
INSERT INTO tags (name, description, usage_count, created_at, updated_at)
VALUES 
  ('whale', 'Blue whales and other species', 0, NOW(), NOW()),
  ('dolphin', 'Various dolphin species', 0, NOW(), NOW()),
  ('sea-turtle', 'Sea turtles in Sri Lankan waters', 0, NOW(), NOW()),
  ('coral-reef', 'Coral reef ecosystems', 0, NOW(), NOW());

-- Location Tags
INSERT INTO tags (name, description, usage_count, created_at, updated_at)
VALUES 
  ('yala', 'Yala National Park', 0, NOW(), NOW()),
  ('wilpattu', 'Wilpattu National Park', 0, NOW(), NOW()),
  ('udawalawe', 'Udawalawe National Park', 0, NOW(), NOW()),
  ('mirissa', 'Mirissa coastal area', 0, NOW(), NOW()),
  ('sigiriya', 'Sigiriya rock fortress', 0, NOW(), NOW());
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

### Tag Name
- **Required:** Yes
- **Minimum length:** 2 characters
- **Maximum length:** 50 characters
- **Unique:** Yes (case-insensitive)
- **Format:** Automatically normalized to lowercase
- **Example:** "leopard", "sri-lankan-elephant"

### Tag Description
- **Required:** No
- **Maximum length:** 255 characters
- **Example:** "Photos featuring Sri Lankan leopards"

### Tag Usage Count
- **Type:** Integer
- **Minimum:** 0
- **Managed by:** System (auto-incremented/decremented)
- **Can be recalculated:** Yes

### Tag Merge Operation
- **sourceTagIds:** Required, must be a non-empty list of valid tag IDs
- **targetTagId:** Required, must be a valid tag ID
- **Constraint:** Target tag cannot be in source list

## 📞 Support

For issues or questions:
1. Check application logs in `logs/` directory
2. Verify database connectivity
3. Validate JWT token
4. Review validation constraints
5. Check category associations (photos)

## 🎯 Best Practices

### Categories
1. **Always use unique slugs** - Helps with SEO and URL structure
2. **Set display order** - Controls category ordering in UI
3. **Use descriptive names** - Makes categories easy to understand
4. **Add images** - Enhances visual presentation
5. **Keep categories active** - Deactivate instead of delete when possible
6. **Regular cleanup** - Remove empty categories periodically

### Tags
1. **Use consistent naming** - Tag names are automatically normalized to lowercase
2. **Add descriptions** - Helps users understand tag purpose
3. **Leverage get-or-create** - Use bulk operations for efficiency
4. **Merge duplicates** - Consolidate similar tags regularly
5. **Clean up unused tags** - Remove tags with zero usage periodically
6. **Recalculate counts** - Run recalculate-usage if counts seem off
7. **Use meaningful names** - Make tags searchable and intuitive
8. **Avoid over-tagging** - Keep tag lists focused and relevant

### General
1. **Use pagination** - For large datasets, always use page/size parameters
2. **Handle 404s gracefully** - Check existence before operations
3. **Validate input** - Follow validation rules to avoid errors
4. **Use search endpoints** - More efficient than fetching all and filtering
5. **Monitor usage counts** - Track popular categories and tags
6. **Batch operations** - Use bulk endpoints for multiple items

---

**Happy Testing! 🎉**
