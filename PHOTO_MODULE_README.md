# Photo Module Testing Guide

This guide provides comprehensive testing instructions for the Photo Management Module in the Ceylon Wild Capture backend.

## 📋 Table of Contents

1. [Prerequisites](#prerequisites)
2. [Photo Management Endpoints](#photo-management-endpoints)
3. [Category Management Endpoints](#category-management-endpoints)
4. [Tag Management Endpoints](#tag-management-endpoints)
5. [Testing with Postman](#testing-with-postman)
6. [Testing with curl](#testing-with-curl)
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

## 📷 Photo Management Endpoints

### 1. Upload Photo
**Endpoint:** `POST /api/v1/photos/upload`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: multipart/form-data
```

**Request (Multipart Form Data):**
- **file** (file, required): The photo file to upload
- **data** (JSON, required): Photo metadata

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

**Response:**
```json
{
  "id": 1,
  "title": "Sri Lankan Leopard in Yala",
  "description": "A magnificent leopard spotted in Yala National Park",
  "imageUrl": "photos/photo-123456789.jpg",
  "thumbnailUrl": null,
  "watermarkedUrl": null,
  "photographerId": 1,
  "photographerName": "John Doe",
  "fileSize": null,
  "width": null,
  "height": null,
  "format": null,
  "basePrice": 50.00,
  "commercialPrice": 150.00,
  "editorialPrice": 100.00,
  "extendedPrice": 250.00,
  "isApproved": false,
  "isFeatured": false,
  "isActive": true,
  "viewCount": 0,
  "downloadCount": 0,
  "likeCount": 0,
  "location": "Yala National Park, Sri Lanka",
  "cameraModel": "Canon EOS R5",
  "lens": "RF 100-500mm f/4.5-7.1L IS USM",
  "focalLength": "400mm",
  "aperture": "f/5.6",
  "shutterSpeed": "1/1000",
  "iso": "800",
  "captureDate": "2024-11-30T08:30:00",
  "createdAt": "2024-11-30T10:00:00",
  "updatedAt": "2024-11-30T10:00:00",
  "tags": [...],
  "categories": [...]
}
```

**Notes:**
- File must be a valid image format (JPG, PNG, etc.)
- Photo is created with `isApproved=false` and requires admin approval
- If FileStorageService is not available, a placeholder URL will be used

### 2. Create Photo (Without File Upload)
**Endpoint:** `POST /api/v1/photos`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "title": "Elephant Herd",
  "description": "Family of elephants crossing the road",
  "photographerId": 1,
  "basePrice": 40.00,
  "location": "Udawalawe National Park",
  "tagIds": [2, 5],
  "categoryIds": [1]
}
```

**Response:** Same as Upload Photo response

**Notes:**
- Use this endpoint to create photo records without uploading files
- Useful for batch imports or when files are already stored

### 3. Update Photo
**Endpoint:** `PUT /api/v1/photos/{id}`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "title": "Updated Title",
  "description": "Updated description",
  "basePrice": 60.00,
  "commercialPrice": 180.00,
  "location": "Updated Location",
  "tagIds": [1, 2, 3, 4],
  "categoryIds": [1, 2],
  "isActive": true,
  "isFeatured": false
}
```

**Response:** Updated photo response DTO

**Notes:**
- All fields are optional
- Only provided fields will be updated

### 4. Update Photo Metadata
**Endpoint:** `PATCH /api/v1/photos/{id}/metadata`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "title": "New Title",
  "description": "New description",
  "location": "New Location"
}
```

**Response:** Updated photo response DTO

### 5. Update Photo Pricing
**Endpoint:** `PATCH /api/v1/photos/{id}/pricing`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
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

**Response:** Updated photo response DTO

### 6. Update Photo EXIF Data
**Endpoint:** `PATCH /api/v1/photos/{id}/exif`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
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

**Response:** Updated photo response DTO

### 7. Get Photo by ID
**Endpoint:** `GET /api/v1/photos/{id}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Photo response DTO with all details

### 8. Get Photo with Photographer Details
**Endpoint:** `GET /api/v1/photos/{id}/with-photographer`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Photo response DTO with photographer information eagerly loaded

### 9. Get All Photos (Paginated)
**Endpoint:** `GET /api/v1/photos?page=0&size=20&sort=createdAt,desc`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
{
  "content": [...],
  "pageable": {...},
  "totalElements": 150,
  "totalPages": 8,
  "size": 20,
  "number": 0
}
```

### 10. Get Photos by Photographer
**Endpoint:** `GET /api/v1/photos/photographer/{photographerId}?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Paginated list of photos by photographer

### 11. Get Approved Photos by Photographer
**Endpoint:** `GET /api/v1/photos/photographer/{photographerId}/approved?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Paginated list of approved photos by photographer

### 12. Get Approved and Active Photos
**Endpoint:** `GET /api/v1/photos/approved-active?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Paginated list of photos that are both approved and active

**Notes:**
- This endpoint is typically used for public gallery display

### 13. Get Featured Photos
**Endpoint:** `GET /api/v1/photos/featured?page=0&size=10`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Paginated list of featured photos

### 14. Get Pending Approval Photos
**Endpoint:** `GET /api/v1/photos/pending-approval?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Paginated list of photos awaiting approval

**Notes:**
- Typically restricted to admin users

### 15. Get Most Viewed Photos
**Endpoint:** `GET /api/v1/photos/most-viewed?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Paginated list of photos ordered by view count (descending)

### 16. Get Most Downloaded Photos
**Endpoint:** `GET /api/v1/photos/most-downloaded?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Paginated list of photos ordered by download count (descending)

### 17. Get Most Liked Photos
**Endpoint:** `GET /api/v1/photos/most-liked?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Paginated list of photos ordered by like count (descending)

### 18. Get Recently Uploaded Photos
**Endpoint:** `GET /api/v1/photos/recent?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Paginated list of photos ordered by upload date (descending)

### 19. Get Similar Photos
**Endpoint:** `GET /api/v1/photos/{id}/similar?limit=10`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `limit` (optional): Number of similar photos to return (default: 10, minimum: 1)

**Response:**
```json
[
  {...},
  {...},
  {...}
]
```

**Notes:**
- Finds similar photos based on shared tags
- Returns approved and active photos only

### 20. Get Photos by Tag
**Endpoint:** `GET /api/v1/photos/by-tag/{tagName}?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
GET /api/v1/photos/by-tag/leopard?page=0&size=20
```

**Response:** Paginated list of photos with the specified tag

### 21. Get Photos by Category
**Endpoint:** `GET /api/v1/photos/by-category/{categorySlug}?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
GET /api/v1/photos/by-category/wildlife?page=0&size=20
```

**Response:** Paginated list of photos in the specified category

### 22. Get Photos by Location
**Endpoint:** `GET /api/v1/photos/by-location?location={location}&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
GET /api/v1/photos/by-location?location=Yala&page=0&size=20
```

**Response:** Paginated list of photos from the specified location

**Notes:**
- Search is case-insensitive and uses partial matching

### 23. Get Photos by Price Range
**Endpoint:** `GET /api/v1/photos/by-price-range?minPrice=10.00&maxPrice=100.00&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `minPrice` (required): Minimum base price
- `maxPrice` (required): Maximum base price
- Standard pagination parameters

**Response:** Paginated list of photos within the price range

### 24. Get Photos Uploaded Between Dates
**Endpoint:** `GET /api/v1/photos/uploaded-between?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `startDate` (required): Start date in ISO format
- `endDate` (required): End date in ISO format
- Standard pagination parameters

**Response:** Paginated list of photos uploaded within the date range

### 25. Search Photos
**Endpoint:** `GET /api/v1/photos/search?q={searchTerm}&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
GET /api/v1/photos/search?q=leopard&page=0&size=20
```

**Response:** Paginated list of photos matching the search term

**Notes:**
- Searches in both title and description
- Case-insensitive search

### 26. Advanced Search
**Endpoint:** `POST /api/v1/photos/advanced-search?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
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

**Response:** Paginated list of photos matching all specified criteria

**Notes:**
- All criteria are optional
- Multiple criteria are combined with AND logic

### 27. Deactivate Photo
**Endpoint:** `PATCH /api/v1/photos/{id}/deactivate`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo with `isActive=false`

### 28. Activate Photo
**Endpoint:** `PATCH /api/v1/photos/{id}/activate`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo with `isActive=true`

### 29. Approve Photo
**Endpoint:** `PATCH /api/v1/photos/{id}/approve`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo with `isApproved=true`

**Notes:**
- Typically restricted to admin users

### 30. Reject Photo
**Endpoint:** `PATCH /api/v1/photos/{id}/reject?reason={reason}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `reason` (required): Reason for rejection

**Example:**
```
PATCH /api/v1/photos/1/reject?reason=Image%20quality%20too%20low
```

**Response:** Updated photo with `isApproved=false` and `isActive=false`

### 31. Set Featured Status
**Endpoint:** `PATCH /api/v1/photos/{id}/featured?featured={true|false}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
PATCH /api/v1/photos/1/featured?featured=true
```

**Response:** Updated photo with featured status

### 32. Assign Tags to Photo
**Endpoint:** `PUT /api/v1/photos/{id}/tags`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
[1, 2, 3, 5, 8]
```

**Response:** Updated photo with new tag assignments

**Notes:**
- Replaces all existing tags
- To add a single tag, use the Add Tag endpoint

### 33. Add Tag to Photo
**Endpoint:** `POST /api/v1/photos/{id}/tags/{tagId}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo with the tag added

**Notes:**
- Increments the tag's usage count
- If tag already exists on photo, no change occurs

### 34. Remove Tag from Photo
**Endpoint:** `DELETE /api/v1/photos/{id}/tags/{tagId}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo with the tag removed

**Notes:**
- Decrements the tag's usage count

### 35. Assign Categories to Photo
**Endpoint:** `PUT /api/v1/photos/{id}/categories`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
[1, 2, 4]
```

**Response:** Updated photo with new category assignments

**Notes:**
- Replaces all existing categories

### 36. Add Category to Photo
**Endpoint:** `POST /api/v1/photos/{id}/categories/{categoryId}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo with the category added

### 37. Remove Category from Photo
**Endpoint:** `DELETE /api/v1/photos/{id}/categories/{categoryId}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo with the category removed

### 38. Increment View Count
**Endpoint:** `POST /api/v1/photos/{id}/increment-views`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo with incremented view count

**Notes:**
- Call this when a photo is viewed/displayed

### 39. Increment Download Count
**Endpoint:** `POST /api/v1/photos/{id}/increment-downloads`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo with incremented download count

**Notes:**
- Call this when a photo is downloaded

### 40. Like Photo
**Endpoint:** `POST /api/v1/photos/{id}/like`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo with incremented like count

### 41. Unlike Photo
**Endpoint:** `POST /api/v1/photos/{id}/unlike`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo with decremented like count

### 42. Count Photos by Photographer
**Endpoint:** `GET /api/v1/photos/photographer/{photographerId}/count`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
125
```

### 43. Count Approved Photos by Photographer
**Endpoint:** `GET /api/v1/photos/photographer/{photographerId}/count-approved`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
98
```

### 44. Count Pending Approval Photos
**Endpoint:** `GET /api/v1/photos/count-pending`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
27
```

### 45. Count Total Photos
**Endpoint:** `GET /api/v1/photos/count`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
1547
```

### 46. Process Uploaded Photo
**Endpoint:** `POST /api/v1/photos/{id}/process`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Updated photo after processing

**Notes:**
- Placeholder for image processing (thumbnails, watermarks, EXIF extraction)
- Requires ImageProcessingService implementation

### 47. Check Photo Exists
**Endpoint:** `GET /api/v1/photos/{id}/exists`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:**
```json
true
```

### 48. Delete Photo
**Endpoint:** `DELETE /api/v1/photos/{id}`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** `204 No Content`

**Notes:**
- Deletes the photo record and associated files from storage
- Cannot be undone

---

## 🔍 Advanced Photo Search Endpoints

### 1. Search with Multiple Filters
**Endpoint:** `POST /api/v1/photos/search/filters?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
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
  "isFeatured": false,
  "uploadStartDate": "2024-01-01T00:00:00",
  "uploadEndDate": "2024-12-31T23:59:59",
  "captureStartDate": "2024-01-01T00:00:00",
  "captureEndDate": "2024-12-31T23:59:59",
  "cameraModel": "Canon EOS R5",
  "lens": "RF 100-500mm",
  "isoRange": "100-800",
  "apertureRange": "f/2.8-f/5.6",
  "orientation": "LANDSCAPE",
  "minWidth": 1920,
  "minHeight": 1080
}
```

**Response:** Paginated list of photos matching all specified filters

**Notes:**
- All fields are optional
- Multiple filters are combined with AND logic

### 2. Search by All Tags (AND)
**Endpoint:** `GET /api/v1/photos/search/tags/all?tags=leopard&tags=yala&tags=wildlife&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Photos that have ALL specified tags

**Notes:**
- Use multiple `tags` query parameters
- Returns only photos that contain every specified tag

### 3. Search by Any Tags (OR)
**Endpoint:** `GET /api/v1/photos/search/tags/any?tags=leopard&tags=elephant&tags=whale&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Photos that have ANY of the specified tags

**Notes:**
- Use multiple `tags` query parameters
- Returns photos that contain at least one of the specified tags

### 4. Search by Multiple Categories
**Endpoint:** `GET /api/v1/photos/search/categories?slugs=wildlife&slugs=nature&slugs=landscape&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Photos belonging to any of the specified categories

**Notes:**
- Use category slugs instead of IDs
- Use multiple `slugs` query parameters

### 5. Search by Photographer and Tags
**Endpoint:** `GET /api/v1/photos/search/photographer/{photographerId}/tags?tags=leopard&tags=wildlife&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
GET /api/v1/photos/search/photographer/5/tags?tags=leopard&tags=wildlife&page=0&size=20
```

**Response:** Photos by specific photographer that have all specified tags

### 6. Search by Location and Price Range
**Endpoint:** `GET /api/v1/photos/search/location-price?location=Yala&minPrice=10.00&maxPrice=100.00&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `location` (required): Location to search
- `minPrice` (optional): Minimum price
- `maxPrice` (optional): Maximum price

**Response:** Photos from specified location within price range

### 7. Search by Capture Date Range
**Endpoint:** `GET /api/v1/photos/search/capture-date-range?startDate=2024-01-01T00:00:00&endDate=2024-12-31T23:59:59&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `startDate` (required): Start date in ISO format
- `endDate` (required): End date in ISO format

**Response:** Photos captured within the specified date range

### 8. Search by Camera Model
**Endpoint:** `GET /api/v1/photos/search/camera?model=Canon%20EOS%20R5&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Photos taken with specified camera model

### 9. Search by Lens
**Endpoint:** `GET /api/v1/photos/search/lens?lens=RF%20100-500mm&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Response:** Photos taken with specified lens

### 10. Search by ISO Range
**Endpoint:** `GET /api/v1/photos/search/iso?range=100-800&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `range` (required): ISO range (e.g., "100-800", "1600-3200")

**Response:** Photos within specified ISO range

### 11. Search by Aperture Range
**Endpoint:** `GET /api/v1/photos/search/aperture?range=f/2.8-f/5.6&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `range` (required): Aperture range (e.g., "f/2.8-f/5.6")

**Response:** Photos within specified aperture range

### 12. Search by Orientation
**Endpoint:** `GET /api/v1/photos/search/orientation/{orientation}?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Path Parameters:**
- `orientation`: LANDSCAPE, PORTRAIT, or SQUARE

**Example:**
```
GET /api/v1/photos/search/orientation/LANDSCAPE?page=0&size=20
```

**Response:** Photos with specified orientation

### 13. Search by Minimum Dimensions
**Endpoint:** `GET /api/v1/photos/search/dimensions?minWidth=1920&minHeight=1080&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `minWidth` (optional): Minimum width in pixels
- `minHeight` (optional): Minimum height in pixels

**Response:** Photos meeting minimum dimension requirements

### 14. Get Popular Photos
**Endpoint:** `GET /api/v1/photos/search/popular/{metric}?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Path Parameters:**
- `metric`: views, downloads, or likes

**Example:**
```
GET /api/v1/photos/search/popular/views?page=0&size=20
```

**Response:** Photos ordered by specified popularity metric (descending)

### 15. Get Trending Photos
**Endpoint:** `GET /api/v1/photos/search/trending?days=7&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `days` (optional): Number of days to look back (default: 7, minimum: 1)

**Response:** Photos with highest engagement in recent days

**Notes:**
- Considers views, downloads, and likes in recent time period

### 16. Get Recommended Photos
**Endpoint:** `GET /api/v1/photos/search/recommended/{userId}?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Example:**
```
GET /api/v1/photos/search/recommended/10?page=0&size=20
```

**Response:** Personalized photo recommendations for user

**Notes:**
- Based on user's browsing history and preferences

### 17. Get Photos by Price Tier
**Endpoint:** `GET /api/v1/photos/search/price-tier/{tier}?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Path Parameters:**
- `tier`: BUDGET, STANDARD, PREMIUM, or LUXURY

**Example:**
```
GET /api/v1/photos/search/price-tier/PREMIUM?page=0&size=20
```

**Response:** Photos in specified price tier

### 18. Full-Text Search
**Endpoint:** `GET /api/v1/photos/search/full-text?query=wildlife%20sri%20lanka&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `query` (required): Full-text search query

**Response:** Photos matching full-text search across all text fields

**Notes:**
- Searches in title, description, location, tags, and categories

### 19. Filter Approved Photos
**Endpoint:** `POST /api/v1/photos/search/approved?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "keyword": "wildlife",
  "categorySlugs": ["nature", "wildlife"],
  "tagNames": ["leopard", "elephant"],
  "minPrice": 10.00,
  "maxPrice": 100.00,
  "location": "Yala",
  "photographerId": 5,
  "orientation": "LANDSCAPE",
  "minWidth": 1920,
  "minHeight": 1080,
  "cameraModel": "Canon EOS R5"
}
```

**Response:** Approved photos matching search criteria

**Notes:**
- Automatically filters for approved photos only
- All fields are optional

### 20. Get Recent Photos by Photographer
**Endpoint:** `GET /api/v1/photos/search/photographer/{photographerId}/recent?days=30&page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
```

**Query Parameters:**
- `days` (optional): Number of days to look back (default: 30, minimum: 1)

**Example:**
```
GET /api/v1/photos/search/photographer/5/recent?days=7&page=0&size=20
```

**Response:** Recent photos by specified photographer

### 21. Comprehensive Search with Criteria
**Endpoint:** `POST /api/v1/photos/search/comprehensive?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
```

**Request Body:**
```json
{
  "keyword": "wildlife",
  "categoryIds": [1, 2],
  "categorySlugs": ["nature", "wildlife"],
  "tagNames": ["leopard", "elephant"],
  "tagIds": [1, 2, 3],
  "photographerId": 5,
  "minPrice": 10.00,
  "maxPrice": 100.00,
  "location": "Yala National Park",
  "cameraModel": "Canon EOS R5",
  "uploadStartDate": "2024-01-01T00:00:00",
  "uploadEndDate": "2024-12-31T23:59:59",
  "captureStartDate": "2024-01-01T00:00:00",
  "captureEndDate": "2024-12-31T23:59:59",
  "isApproved": true,
  "isActive": true,
  "isFeatured": false,
  "minViewCount": 100,
  "minDownloadCount": 10,
  "minLikeCount": 50,
  "format": "jpg",
  "minWidth": 1920,
  "minHeight": 1080,
  "orientation": "LANDSCAPE",
  "priceTier": "PREMIUM",
  "sortBy": "createdAt",
  "sortDirection": "DESC",
  "hasWatermark": true,
  "hasThumbnail": true,
  "isoRange": "100-800",
  "apertureRange": "f/2.8-f/5.6",
  "lens": "RF 100-500mm",
  "includePhotographer": true,
  "includeTags": true,
  "includeCategories": true
}
```

**Response:** Photos matching all specified comprehensive criteria

**Notes:**
- Most flexible search endpoint
- All fields are optional
- Supports multiple sorting and filtering options
- Can eagerly load related entities

### 22. Quick Search
**Endpoint:** `POST /api/v1/photos/search/quick?page=0&size=20`

**Headers:**
```
Authorization: Bearer {access_token}
Content-Type: application/json
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

**Response:** Approved and active photos matching quick search criteria

**Notes:**
- Simplified search for public-facing searches
- Automatically filters for approved and active photos
- All fields are optional
- Returns empty results if no criteria match

---

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
