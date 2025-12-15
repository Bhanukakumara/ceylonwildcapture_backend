# Photo Module - Complete API Documentation

**Ceylon Wild Capture Backend - Wildlife Photography Management System**

This comprehensive documentation covers all API endpoints for the Photo Module, including photo management, categories, tags, and advanced search functionality.

---

## 📋 Table of Contents

1. [Module Overview](#module-overview)
2. [Data Models](#data-models)
3. [Photo Management API](#photo-management-api)
4. [Category Management API](#category-management-api)
5. [Tag Management API](#tag-management-api)
6. [Advanced Search API](#advanced-search-api)
7. [Testing Guide](#testing-guide)
8. [Best Practices](#best-practices)

---

## 🔎 Module Overview

The Photo Module is the core component of the Ceylon Wild Capture platform, managing wildlife photography content, taxonomy, and search functionality.

### Key Features

- **Photo Upload & Management**: Multipart file upload with metadata
- **EXIF Data Support**: Camera model, lens, aperture, ISO, shutter speed
- **Multi-Tier Pricing**: Base, Commercial, Editorial, Extended licenses
- **Approval Workflow**: Admin moderation before public visibility
- **Category System**: Hierarchical organization with display ordering
- **Tag System**: Flexible tagging with usage tracking and merge capabilities
- **Advanced Search**: Complex filtering by location, price, EXIF data, dimensions
- **Engagement Tracking**: Views, downloads, and likes
- **Similar Photo Discovery**: Tag-based recommendations

### Controllers

1. **PhotoController** (`/api/v1/photos`) - Core photo CRUD operations
2. **CategoryController** (`/api/v1/categories`) - Category management
3. **TagController** (`/api/v1/tags`) - Tag management
4. **PhotoSearchController** (`/api/v1/photos/search`) - Advanced search operations

---

## 🗂️ Data Models

### Photo Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `title` | String | 3-200 chars, required | Photo title |
| `description` | String | Max 2000 chars | Detailed description |
| `imageUrl` | String | - | High-resolution image URL |
| `thumbnailUrl` | String | - | Optimized thumbnail URL |
| `watermarkedUrl` | String | - | Watermarked preview URL |
| `photographer` | User | Required | Photographer (User entity) |
| `fileSize` | Long | - | File size in bytes |
| `width` | Integer | - | Image width in pixels |
| `height` | Integer | - | Image height in pixels |
| `format` | String | Max 20 chars | Image format (jpg, png, etc.) |
| `basePrice` | BigDecimal | Required, precision 10,2 | Standard license price |
| `commercialPrice` | BigDecimal | Precision 10,2 | Commercial license price |
| `editorialPrice` | BigDecimal | Precision 10,2 | Editorial license price |
| `extendedPrice` | BigDecimal | Precision 10,2 | Extended license price |
| `isApproved` | Boolean | Default: false | Admin approval status |
| `isFeatured` | Boolean | Default: false | Featured on homepage |
| `isActive` | Boolean | Default: true | Visibility flag |
| `viewCount` | Integer | Default: 0 | Total views |
| `downloadCount` | Integer | Default: 0 | Total downloads |
| `likeCount` | Integer | Default: 0 | Total likes |
| `location` | String | Max 200 chars | Capture location |
| `cameraModel` | String | Max 100 chars | Camera model |
| `lens` | String | Max 100 chars | Lens used |
| `focalLength` | String | Max 20 chars | Focal length |
| `aperture` | String | Max 20 chars | Aperture value |
| `shutterSpeed` | String | Max 20 chars | Shutter speed |
| `iso` | String | Max 20 chars | ISO value |
| `captureDate` | LocalDateTime | - | Photo capture date |
| `createdAt` | LocalDateTime | Auto-generated | Upload timestamp |
| `updatedAt` | LocalDateTime | Auto-updated | Last update timestamp |
| `tags` | List<Tag> | Many-to-Many | Associated tags |
| `categories` | List<Category> | Many-to-Many | Associated categories |

### Category Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `name` | String | 2-100 chars, unique, required | Category name |
| `description` | String | Max 500 chars | Category description |
| `slug` | String | Unique, max 100 chars | URL-friendly identifier |
| `imageUrl` | String | - | Category cover image |
| `isActive` | Boolean | Default: true | Visibility flag |
| `displayOrder` | Integer | - | Sort order for UI |
| `createdAt` | LocalDateTime | Auto-generated | Creation timestamp |
| `updatedAt` | LocalDateTime | Auto-updated | Last update timestamp |

### Tag Entity

| Field | Type | Constraints | Description |
|-------|------|-------------|-------------|
| `id` | Long | Auto-generated | Unique identifier |
| `name` | String | 2-50 chars, unique, required | Tag name (lowercase) |
| `description` | String | Max 200 chars | Tag description |
| `usageCount` | Integer | Default: 0 | Number of photos using tag |
| `createdAt` | LocalDateTime | Auto-generated | Creation timestamp |
| `updatedAt` | LocalDateTime | Auto-updated | Last update timestamp |

---

## 📷 Photo Management API

**Base Path:** `/api/v1/photos`

### Upload & Creation

#### 1. Upload Photo with File
```http
POST /api/v1/photos/upload
Content-Type: multipart/form-data
Authorization: Bearer {token}
```

**Request Parts:**
- `file` (MultipartFile, required): Image file
- `data` (JSON, required): Photo metadata

**Photo Data JSON:**
```json
{
  "title": "Sri Lankan Leopard in Yala",
  "description": "A magnificent leopard spotted in Yala National Park",
  "photographerId": 1,
  "basePrice": 50.00,
  "commercialPrice": 150.00,
  "editorialPrice": 100.00,
  "extendedPrice": 250.00,
  "location": "Yala National Park, Sri Lanka",
  "tagIds": [1, 2, 3],
  "categoryIds": [1, 2],
  "cameraModel": "Canon EOS R5",
  "lens": "RF 100-500mm f/4.5-7.1L IS USM",
  "focalLength": "400mm",
  "aperture": "f/5.6",
  "shutterSpeed": "1/1000",
  "iso": "800",
  "captureDate": "2024-11-30T08:30:00"
}
```

**Response:** `201 Created` with `PhotoResponseDto`

---

#### 2. Create Photo (Metadata Only)
```http
POST /api/v1/photos
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:** Same as photo data above (without file)

**Response:** `201 Created` with `PhotoResponseDto`

---

### Update Operations

#### 3. Update Photo (Full)
```http
PUT /api/v1/photos/{id}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:** `PhotoUpdateDto` (all fields optional)

---

#### 4. Update Metadata Only
```http
PATCH /api/v1/photos/{id}/metadata
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "title": "Updated Title",
  "description": "Updated description",
  "location": "Updated Location"
}
```

---

#### 5. Update Pricing Only
```http
PATCH /api/v1/photos/{id}/pricing
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "basePrice": 55.00,
  "commercialPrice": 165.00,
  "editorialPrice": 110.00,
  "extendedPrice": 275.00
}
```

---

#### 6. Update EXIF Data Only
```http
PATCH /api/v1/photos/{id}/exif
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "cameraModel": "Sony A7R V",
  "lens": "FE 200-600mm F5.6-6.3 G OSS",
  "focalLength": "600mm",
  "aperture": "f/6.3",
  "shutterSpeed": "1/2000",
  "iso": "1600",
  "captureDate": "2024-11-30T14:30:00"
}
```

---

### Retrieval Operations

#### 7. Get Photo by ID
```http
GET /api/v1/photos/{id}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `PhotoResponseDto` or `404 Not Found`

---

#### 8. Get Photo with Photographer Details
```http
GET /api/v1/photos/{id}/with-photographer
Authorization: Bearer {token}
```

**Response:** `200 OK` with `PhotoResponseDto` (photographer eagerly loaded)

---

#### 9. Get All Photos (Paginated)
```http
GET /api/v1/photos?page=0&size=20&sort=createdAt,desc
Authorization: Bearer {token}
```

**Response:** `Page<PhotoResponseDto>`

---

#### 10. Get Photos by Photographer
```http
GET /api/v1/photos/photographer/{photographerId}?page=0&size=20
Authorization: Bearer {token}
```

---

#### 11. Get Approved Photos by Photographer
```http
GET /api/v1/photos/photographer/{photographerId}/approved?page=0&size=20
Authorization: Bearer {token}
```

---

#### 12. Get Approved & Active Photos (Public Feed)
```http
GET /api/v1/photos/approved-active?page=0&size=20
Authorization: Bearer {token}
```

---

#### 13. Get Featured Photos
```http
GET /api/v1/photos/featured?page=0&size=10
Authorization: Bearer {token}
```

---

#### 14. Get Pending Approval Photos
```http
GET /api/v1/photos/pending-approval?page=0&size=20
Authorization: Bearer {token}
```

---

#### 15. Get Most Viewed Photos
```http
GET /api/v1/photos/most-viewed?page=0&size=20
Authorization: Bearer {token}
```

---

#### 16. Get Most Downloaded Photos
```http
GET /api/v1/photos/most-downloaded?page=0&size=20
Authorization: Bearer {token}
```

---

#### 17. Get Most Liked Photos
```http
GET /api/v1/photos/most-liked?page=0&size=20
Authorization: Bearer {token}
```

---

#### 18. Get Recently Uploaded Photos
```http
GET /api/v1/photos/recent?page=0&size=20
Authorization: Bearer {token}
```

---

#### 19. Get Similar Photos
```http
GET /api/v1/photos/{id}/similar?limit=10
Authorization: Bearer {token}
```

**Query Parameters:**
- `limit` (optional, default: 10, min: 1): Number of similar photos

**Response:** `List<PhotoResponseDto>`

---

### Filtering Operations

#### 20. Get Photos by Tag
```http
GET /api/v1/photos/by-tag/{tagName}?page=0&size=20
Authorization: Bearer {token}
```

---

#### 21. Get Photos by Category
```http
GET /api/v1/photos/by-category/{categorySlug}?page=0&size=20
Authorization: Bearer {token}
```

---

#### 22. Get Photos by Location
```http
GET /api/v1/photos/by-location?location=Yala&page=0&size=20
Authorization: Bearer {token}
```

---

#### 23. Get Photos by Price Range
```http
GET /api/v1/photos/by-price-range?minPrice=10.00&maxPrice=100.00&page=0&size=20
Authorization: Bearer {token}
```

---

#### 24. Get Photos Uploaded Between Dates
```http
GET /api/v1/photos/uploaded-between?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20
Authorization: Bearer {token}
```

---

### Search Operations

#### 25. Simple Search
```http
GET /api/v1/photos/search?q=leopard&page=0&size=20
Authorization: Bearer {token}
```

**Searches:** Title and description fields

---

#### 26. Advanced Search
```http
POST /api/v1/photos/advanced-search?page=0&size=20
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "keyword": "leopard",
  "categoryIds": [1, 2],
  "tagNames": ["wildlife", "mammal"],
  "photographerId": 1,
  "minPrice": 10.00,
  "maxPrice": 200.00,
  "location": "Yala",
  "isApproved": true,
  "isActive": true,
  "isFeatured": false,
  "minViewCount": 100,
  "format": "jpg",
  "minWidth": 1920,
  "minHeight": 1080
}
```

---

### Status Management

#### 27. Deactivate Photo
```http
PATCH /api/v1/photos/{id}/deactivate
Authorization: Bearer {token}
```

---

#### 28. Activate Photo
```http
PATCH /api/v1/photos/{id}/activate
Authorization: Bearer {token}
```

---

#### 29. Approve Photo (Admin)
```http
PATCH /api/v1/photos/{id}/approve
Authorization: Bearer {token}
```

---

#### 30. Reject Photo (Admin)
```http
PATCH /api/v1/photos/{id}/reject?reason=Image%20quality%20too%20low
Authorization: Bearer {token}
```

**Query Parameters:**
- `reason` (required): Rejection reason

---

#### 31. Set Featured Status (Admin)
```http
PATCH /api/v1/photos/{id}/featured?featured=true
Authorization: Bearer {token}
```

**Query Parameters:**
- `featured` (required): true or false

---

### Tag & Category Management

#### 32. Assign Tags (Replace All)
```http
PUT /api/v1/photos/{id}/tags
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
[1, 2, 3, 5, 8]
```

---

#### 33. Add Single Tag
```http
POST /api/v1/photos/{id}/tags/{tagId}
Authorization: Bearer {token}
```

---

#### 34. Remove Tag
```http
DELETE /api/v1/photos/{id}/tags/{tagId}
Authorization: Bearer {token}
```

---

#### 35. Assign Categories (Replace All)
```http
PUT /api/v1/photos/{id}/categories
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
[1, 2, 4]
```

---

#### 36. Add Single Category
```http
POST /api/v1/photos/{id}/categories/{categoryId}
Authorization: Bearer {token}
```

---

#### 37. Remove Category
```http
DELETE /api/v1/photos/{id}/categories/{categoryId}
Authorization: Bearer {token}
```

---

### Engagement Tracking

#### 38. Increment View Count
```http
POST /api/v1/photos/{id}/increment-views
Authorization: Bearer {token}
```

---

#### 39. Increment Download Count
```http
POST /api/v1/photos/{id}/increment-downloads
Authorization: Bearer {token}
```

---

#### 40. Like Photo
```http
POST /api/v1/photos/{id}/like
Authorization: Bearer {token}
```

---

#### 41. Unlike Photo
```http
POST /api/v1/photos/{id}/unlike
Authorization: Bearer {token}
```

---

### Statistics

#### 42. Count Photos by Photographer
```http
GET /api/v1/photos/photographer/{photographerId}/count
Authorization: Bearer {token}
```

**Response:** `Long` (count)

---

#### 43. Count Approved Photos by Photographer
```http
GET /api/v1/photos/photographer/{photographerId}/count-approved
Authorization: Bearer {token}
```

---

#### 44. Count Pending Approval Photos
```http
GET /api/v1/photos/count-pending
Authorization: Bearer {token}
```

---

#### 45. Count Total Photos
```http
GET /api/v1/photos/count
Authorization: Bearer {token}
```

---

### Utility Operations

#### 46. Process Uploaded Photo
```http
POST /api/v1/photos/{id}/process
Authorization: Bearer {token}
```

**Note:** Triggers image processing (thumbnails, watermarks, EXIF extraction)

---

#### 47. Check Photo Exists
```http
GET /api/v1/photos/{id}/exists
Authorization: Bearer {token}
```

**Response:** `Boolean`

---

#### 48. Delete Photo
```http
DELETE /api/v1/photos/{id}
Authorization: Bearer {token}
```

**Response:** `204 No Content`

---

## 📂 Category Management API

**Base Path:** `/api/v1/categories`

### CRUD Operations

#### 1. Create Category
```http
POST /api/v1/categories
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "Wildlife",
  "description": "Wildlife photography from Sri Lanka",
  "slug": "wildlife",
  "imageUrl": "https://example.com/wildlife.jpg",
  "isActive": true,
  "displayOrder": 1
}
```

**Note:** `slug` is auto-generated from `name` if not provided

---

#### 2. Update Category (Full)
```http
PUT /api/v1/categories/{id}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:** `CategoryUpdateDto`

---

#### 3. Update Category Info (Partial)
```http
PATCH /api/v1/categories/{id}/info?name=Wildlife&description=Updated%20description
Authorization: Bearer {token}
```

**Query Parameters:**
- `name` (optional): New category name
- `description` (optional): New description

---

#### 4. Update Category Image
```http
PATCH /api/v1/categories/{id}/image?imageUrl=https://example.com/new-image.jpg
Authorization: Bearer {token}
```

**Query Parameters:**
- `imageUrl` (required): New image URL

---

#### 5. Update Display Order
```http
PATCH /api/v1/categories/{id}/order?displayOrder=5
Authorization: Bearer {token}
```

**Query Parameters:**
- `displayOrder` (required, min: 0): New display order

---

### Retrieval Operations

#### 6. Get Category by ID
```http
GET /api/v1/categories/{id}
Authorization: Bearer {token}
```

**Response:** `200 OK` with `CategoryResponseDto` or `404 Not Found`

---

#### 7. Get Category by Slug
```http
GET /api/v1/categories/slug/{slug}
Authorization: Bearer {token}
```

---

#### 8. Get Category by Name
```http
GET /api/v1/categories/name/{name}
Authorization: Bearer {token}
```

---

#### 9. Get All Categories (Paginated)
```http
GET /api/v1/categories?page=0&size=10&sort=displayOrder,asc
Authorization: Bearer {token}
```

---

#### 10. Get Active Categories (Paginated)
```http
GET /api/v1/categories/active?page=0&size=10
Authorization: Bearer {token}
```

---

#### 11. Get Active Categories (List)
```http
GET /api/v1/categories/active/list
Authorization: Bearer {token}
```

**Response:** `List<CategoryResponseDto>`

---

#### 12. Get Ordered Categories
```http
GET /api/v1/categories/ordered
Authorization: Bearer {token}
```

**Response:** All categories ordered by `displayOrder`

---

#### 13. Get Active Ordered Categories
```http
GET /api/v1/categories/active/ordered
Authorization: Bearer {token}
```

---

#### 14. Search Categories
```http
GET /api/v1/categories/search?q=wild&page=0&size=10
Authorization: Bearer {token}
```

---

#### 15. Get Empty Categories
```http
GET /api/v1/categories/empty
Authorization: Bearer {token}
```

**Response:** Categories with no associated photos

---

#### 16. Get Categories with Photo Count
```http
GET /api/v1/categories/with-photo-count?page=0&size=10
Authorization: Bearer {token}
```

**Response:** `Page<Object[]>` where each element is `[CategoryResponseDto, Long photoCount]`

---

### Statistics

#### 17. Count Active Categories
```http
GET /api/v1/categories/counts/active
Authorization: Bearer {token}
```

---

#### 18. Count Total Categories
```http
GET /api/v1/categories/counts/total
Authorization: Bearer {token}
```

---

### Bulk Operations

#### 19. Reorder Categories
```http
POST /api/v1/categories/reorder
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
[
  {"id": 1, "displayOrder": 3},
  {"id": 2, "displayOrder": 1},
  {"id": 3, "displayOrder": 2}
]
```

---

### Status Management

#### 20. Activate Category
```http
POST /api/v1/categories/{id}/activate
Authorization: Bearer {token}
```

---

#### 21. Deactivate Category
```http
POST /api/v1/categories/{id}/deactivate
Authorization: Bearer {token}
```

---

#### 22. Delete Category
```http
DELETE /api/v1/categories/{id}
Authorization: Bearer {token}
```

**Response:** `204 No Content`

**Note:** Cannot delete categories with associated photos

---

## 🏷️ Tag Management API

**Base Path:** `/api/v1/tags`

### CRUD Operations

#### 1. Create Tag
```http
POST /api/v1/tags
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "leopard",
  "description": "Photos featuring leopards"
}
```

**Note:** Tag names are automatically normalized to lowercase

---

#### 2. Create Tag (Simple)
```http
POST /api/v1/tags/simple?name=elephant
Authorization: Bearer {token}
```

---

#### 3. Update Tag
```http
PUT /api/v1/tags/{id}
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "name": "sri-lankan-leopard",
  "description": "Endemic leopard subspecies"
}
```

---

#### 4. Update Tag Description
```http
PATCH /api/v1/tags/{id}/description?description=Updated%20description
Authorization: Bearer {token}
```

---

#### 5. Rename Tag
```http
PATCH /api/v1/tags/{id}/rename?newName=leopard-kotiya
Authorization: Bearer {token}
```

---

### Retrieval Operations

#### 6. Get Tag by ID
```http
GET /api/v1/tags/{id}
Authorization: Bearer {token}
```

---

#### 7. Get Tag by Name
```http
GET /api/v1/tags/name/{name}
Authorization: Bearer {token}
```

---

#### 8. Get All Tags (Paginated)
```http
GET /api/v1/tags?page=0&size=10&sort=name,asc
Authorization: Bearer {token}
```

---

#### 9. Get Top Tags
```http
GET /api/v1/tags/top?limit=10
Authorization: Bearer {token}
```

**Query Parameters:**
- `limit` (optional, default: 10, min: 1): Number of top tags

**Response:** Tags ordered by `usageCount` descending

---

#### 10. Get Popular Tags
```http
GET /api/v1/tags/popular?minUsageCount=10
Authorization: Bearer {token}
```

**Query Parameters:**
- `minUsageCount` (optional, default: 1, min: 0): Minimum usage threshold

---

#### 11. Get Unused Tags
```http
GET /api/v1/tags/unused
Authorization: Bearer {token}
```

**Response:** Tags with `usageCount = 0`

---

#### 12. Search Tags
```http
GET /api/v1/tags/search?q=leo&page=0&size=10
Authorization: Bearer {token}
```

---

### Bulk Operations

#### 13. Get Tags by IDs
```http
POST /api/v1/tags/by-ids
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
[1, 2, 5, 10]
```

---

#### 14. Get Tags by Names
```http
POST /api/v1/tags/by-names
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
["leopard", "elephant", "whale"]
```

---

#### 15. Get or Create Tags by Names
```http
POST /api/v1/tags/get-or-create
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
["leopard", "new-species", "elephant"]
```

**Note:** Creates tags that don't exist, returns existing ones

---

### Statistics & Maintenance

#### 16. Count Total Tags
```http
GET /api/v1/tags/count
Authorization: Bearer {token}
```

---

#### 17. Increment Usage Count
```http
POST /api/v1/tags/{id}/increment-usage
Authorization: Bearer {token}
```

---

#### 18. Decrement Usage Count
```http
POST /api/v1/tags/{id}/decrement-usage
Authorization: Bearer {token}
```

---

#### 19. Recalculate Usage Counts
```http
POST /api/v1/tags/recalculate-usage
Authorization: Bearer {token}
```

**Response:** `Long` (number of tags updated)

**Note:** Syncs usage counts with actual photo associations

---

#### 20. Merge Tags
```http
POST /api/v1/tags/merge
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "sourceTagIds": [5, 10, 15],
  "targetTagId": 1
}
```

**Note:** Moves all photo associations to target tag, deletes source tags

---

### Deletion Operations

#### 21. Delete Tag
```http
DELETE /api/v1/tags/{id}
Authorization: Bearer {token}
```

**Response:** `204 No Content`

---

#### 22. Delete Unused Tags
```http
DELETE /api/v1/tags/unused
Authorization: Bearer {token}
```

**Response:** `Long` (number of deleted tags)

---

### Utility Operations

#### 23. Check Tag Exists
```http
GET /api/v1/tags/exists?name=leopard
Authorization: Bearer {token}
```

**Response:** `Boolean`

---

## 🔍 Advanced Search API

**Base Path:** `/api/v1/photos/search`

### Multi-Filter Search

#### 1. Search with Multiple Filters
```http
POST /api/v1/photos/search/filters?page=0&size=20
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "searchTerm": "wildlife",
  "categoryIds": [1, 2],
  "tagNames": ["leopard", "elephant"],
  "minPrice": 10.00,
  "maxPrice": 100.00,
  "location": "Yala National Park",
  "photographerId": 5,
  "isApproved": true,
  "isActive": true,
  "uploadStartDate": "2024-01-01T00:00:00",
  "uploadEndDate": "2024-12-31T23:59:59",
  "cameraModel": "Canon EOS R5",
  "minWidth": 1920,
  "minHeight": 1080
}
```

---

### Tag-Based Search

#### 2. Search by All Tags (AND Logic)
```http
GET /api/v1/photos/search/tags/all?tags=leopard&tags=yala&tags=wildlife&page=0&size=20
Authorization: Bearer {token}
```

**Note:** Returns photos that have ALL specified tags

---

#### 3. Search by Any Tags (OR Logic)
```http
GET /api/v1/photos/search/tags/any?tags=leopard&tags=elephant&tags=whale&page=0&size=20
Authorization: Bearer {token}
```

**Note:** Returns photos that have ANY of the specified tags

---

### Category-Based Search

#### 4. Search by Multiple Categories
```http
GET /api/v1/photos/search/categories?slugs=wildlife&slugs=nature&slugs=landscape&page=0&size=20
Authorization: Bearer {token}
```

---

### Combined Searches

#### 5. Search by Photographer and Tags
```http
GET /api/v1/photos/search/photographer/{photographerId}/tags?tags=leopard&tags=wildlife&page=0&size=20
Authorization: Bearer {token}
```

---

#### 6. Search by Location and Price Range
```http
GET /api/v1/photos/search/location-price?location=Yala&minPrice=10.00&maxPrice=100.00&page=0&size=20
Authorization: Bearer {token}
```

---

### Date-Based Search

#### 7. Search by Capture Date Range
```http
GET /api/v1/photos/search/capture-date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20
Authorization: Bearer {token}
```

---

### EXIF-Based Search

#### 8. Search by Camera Model
```http
GET /api/v1/photos/search/camera?model=Canon%20EOS%20R5&page=0&size=20
Authorization: Bearer {token}
```

---

#### 9. Search by Lens
```http
GET /api/v1/photos/search/lens?lens=RF%20100-500mm&page=0&size=20
Authorization: Bearer {token}
```

---

#### 10. Search by ISO Range
```http
GET /api/v1/photos/search/iso?range=100-800&page=0&size=20
Authorization: Bearer {token}
```

---

#### 11. Search by Aperture Range
```http
GET /api/v1/photos/search/aperture?range=f/2.8-f/5.6&page=0&size=20
Authorization: Bearer {token}
```

---

### Dimension-Based Search

#### 12. Search by Orientation
```http
GET /api/v1/photos/search/orientation/{orientation}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `orientation`: LANDSCAPE, PORTRAIT, or SQUARE

---

#### 13. Search by Minimum Dimensions
```http
GET /api/v1/photos/search/dimensions?minWidth=1920&minHeight=1080&page=0&size=20
Authorization: Bearer {token}
```

---

### Popularity & Trending

#### 14. Get Popular Photos
```http
GET /api/v1/photos/search/popular/{metric}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `metric`: views, downloads, or likes

---

#### 15. Get Trending Photos
```http
GET /api/v1/photos/search/trending?days=7&page=0&size=20
Authorization: Bearer {token}
```

**Query Parameters:**
- `days` (optional, default: 7, min: 1): Lookback period

---

#### 16. Get Recommended Photos
```http
GET /api/v1/photos/search/recommended/{userId}?page=0&size=20
Authorization: Bearer {token}
```

**Note:** Personalized recommendations based on user preferences

---

### Price-Based Search

#### 17. Get Photos by Price Tier
```http
GET /api/v1/photos/search/price-tier/{tier}?page=0&size=20
Authorization: Bearer {token}
```

**Path Parameters:**
- `tier`: BUDGET, STANDARD, PREMIUM, or LUXURY

---

### Text Search

#### 18. Full-Text Search
```http
GET /api/v1/photos/search/full-text?query=wildlife%20sri%20lanka&page=0&size=20
Authorization: Bearer {token}
```

**Note:** Searches across title, description, location, tags, and categories

---

### Filtered Searches

#### 19. Filter Approved Photos
```http
POST /api/v1/photos/search/approved?page=0&size=20
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:** `PhotoSearchCriteria` (automatically filters for approved photos)

---

#### 20. Get Recent Photos by Photographer
```http
GET /api/v1/photos/search/photographer/{photographerId}/recent?days=30&page=0&size=20
Authorization: Bearer {token}
```

---

### Comprehensive Search

#### 21. Comprehensive Search with Criteria
```http
POST /api/v1/photos/search/comprehensive?page=0&size=20
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:** Full `PhotoSearchCriteria` with all available filters

---

#### 22. Quick Search
```http
POST /api/v1/photos/search/quick?page=0&size=20
Content-Type: application/json
Authorization: Bearer {token}
```

**Request Body:**
```json
{
  "keyword": "wildlife",
  "categorySlugs": ["nature", "animals"],
  "tagNames": ["elephant", "safari"],
  "minPrice": 10.00,
  "maxPrice": 100.00,
  "location": "Yala National Park",
  "photographerId": 5,
  "orientation": "LANDSCAPE"
}
```

**Note:** Simplified search for public-facing applications

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

### Authentication

All endpoints require JWT authentication. First, obtain a token:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "usernameOrEmail": "admin",
    "password": "password123"
  }'
```

Save the `accessToken` from the response for subsequent requests.

### Example Test Scenarios

#### Scenario 1: Complete Photo Lifecycle

```bash
# 1. Upload photo
curl -X POST http://localhost:8080/api/v1/photos/upload \
  -H "Authorization: Bearer $TOKEN" \
  -F "file=@leopard.jpg" \
  -F 'data={"title":"Leopard","photographerId":1,"basePrice":50.00}'

# 2. Approve photo (admin)
curl -X PATCH http://localhost:8080/api/v1/photos/1/approve \
  -H "Authorization: Bearer $TOKEN"

# 3. Set as featured
curl -X PATCH "http://localhost:8080/api/v1/photos/1/featured?featured=true" \
  -H "Authorization: Bearer $TOKEN"

# 4. Track engagement
curl -X POST http://localhost:8080/api/v1/photos/1/increment-views \
  -H "Authorization: Bearer $TOKEN"
```

#### Scenario 2: Category Management

```bash
# 1. Create category
CATEGORY_ID=$(curl -s -X POST http://localhost:8080/api/v1/categories \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"name":"Wildlife","description":"Wildlife photos"}' \
  | jq -r '.id')

# 2. Update display order
curl -X PATCH "http://localhost:8080/api/v1/categories/$CATEGORY_ID/order?displayOrder=1" \
  -H "Authorization: Bearer $TOKEN"

# 3. Get active categories
curl -X GET http://localhost:8080/api/v1/categories/active/ordered \
  -H "Authorization: Bearer $TOKEN"
```

#### Scenario 3: Tag Operations

```bash
# 1. Create or get tags
curl -X POST http://localhost:8080/api/v1/tags/get-or-create \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '["leopard","wildlife","yala"]'

# 2. Assign tags to photo
curl -X PUT http://localhost:8080/api/v1/photos/1/tags \
  -H "Authorization: Bearer $TOKEN" \
  -H "Content-Type: application/json" \
  -d '[1,2,3]'

# 3. Get popular tags
curl -X GET "http://localhost:8080/api/v1/tags/popular?minUsageCount=5" \
  -H "Authorization: Bearer $TOKEN"
```

---

## 📝 Best Practices

### Photo Management

1. **Always set photographer ID** when creating photos
2. **Use multipart upload** for new photos with files
3. **Approve photos** before making them public
4. **Track engagement** (views, downloads, likes) for analytics
5. **Use featured flag** sparingly for homepage highlights
6. **Set appropriate pricing** for different license types

### Category Management

1. **Use unique, descriptive names** for categories
2. **Set display order** to control UI presentation
3. **Add category images** for better visual hierarchy
4. **Deactivate instead of delete** to preserve history
5. **Check for empty categories** periodically for cleanup

### Tag Management

1. **Use consistent naming conventions** (lowercase, hyphenated)
2. **Add descriptions** to clarify tag purpose
3. **Leverage get-or-create** for bulk operations
4. **Merge duplicate tags** to maintain data quality
5. **Recalculate usage counts** if inconsistencies occur
6. **Clean up unused tags** periodically

### Search Optimization

1. **Use specific search endpoints** instead of filtering all results
2. **Leverage pagination** for large result sets
3. **Combine filters** for precise results
4. **Use quick search** for public-facing applications
5. **Cache popular searches** for performance

### Security

1. **Always validate JWT tokens** on protected endpoints
2. **Restrict admin operations** (approve, reject, featured)
3. **Validate file uploads** for type and size
4. **Sanitize user inputs** in search queries
5. **Implement rate limiting** for public endpoints

---

## 📊 API Documentation

**Swagger UI:** `http://localhost:8080/swagger-ui.html`  
**OpenAPI Spec:** `http://localhost:8080/v3/api-docs`

---

## 🆘 Support

For issues or questions:
1. Check application logs
2. Verify database connectivity
3. Validate JWT token
4. Review validation constraints
5. Consult API documentation

---

**Version:** 1.0.0  
**Last Updated:** December 2024  
**Module:** Photo Management  
**Platform:** Ceylon Wild Capture Backend
