# User Module Exception Handling Implementation

## Overview
Comprehensive exception handling has been implemented for the user module with custom exceptions and a global exception handler.

## Custom Exception Classes

All custom exceptions are located in `src/main/java/lk/ceylonwildcapture_backend/ceylonwildcapture_backend/modules/user/exception/`

### 1. UserNotFoundException
- **Purpose**: Thrown when a user is not found
- **HTTP Status**: 404 NOT FOUND
- **Usage**:
  - `new UserNotFoundException(userId)`
  - `new UserNotFoundException("email", emailValue)`
  - `new UserNotFoundException("Custom message")`

### 2. DuplicateUserException
- **Purpose**: Thrown when attempting to create/update a user with existing email or username
- **HTTP Status**: 409 CONFLICT
- **Usage**:
  - `new DuplicateUserException("Email", emailValue)`
  - `new DuplicateUserException("Username", usernameValue)`

### 3. UserAlreadyExistsException
- **Purpose**: Thrown when a user already exists (e.g., admin registration)
- **HTTP Status**: 409 CONFLICT
- **Usage**:
  - `new UserAlreadyExistsException("Custom message")`

### 4. InvalidCredentialsException
- **Purpose**: Thrown when authentication credentials are invalid
- **HTTP Status**: 401 UNAUTHORIZED
- **Usage**:
  - `new InvalidCredentialsException()`
  - `new InvalidCredentialsException("Custom message")`

### 5. InvalidPasswordException
- **Purpose**: Thrown for password validation failures
- **HTTP Status**: 400 BAD REQUEST
- **Usage**:
  - `new InvalidPasswordException("Password too short")`
  - `new InvalidPasswordException("Passwords don't match")`

### 6. InvalidTokenException
- **Purpose**: Thrown when JWT or other tokens are invalid
- **HTTP Status**: 401 UNAUTHORIZED
- **Usage**:
  - `new InvalidTokenException()`
  - `new InvalidTokenException("Token expired")`

### 7. AccountLockedException
- **Purpose**: Thrown when attempting to access a locked account
- **HTTP Status**: 403 FORBIDDEN
- **Usage**:
  - `new AccountLockedException()`
  - `new AccountLockedException("Custom message")`

## Global Exception Handler

**Location**: `UserExceptionHandler.java`

The `@RestControllerAdvice` handles all exceptions thrown by the user module and returns standardized error responses.

### Error Response Format

```json
{
  "timestamp": "2025-11-28T18:00:00",
  "status": 404,
  "error": "Not Found",
  "message": "User not found with ID: 123",
  "path": "/api/v1/users/123",
  "validationErrors": {
    "field1": "error message",
    "field2": "error message"
  }
}
```

### Handled Exceptions

1. **UserNotFoundException** → 404 NOT FOUND
2. **DuplicateUserException** → 409 CONFLICT
3. **UserAlreadyExistsException** → 409 CONFLICT
4. **InvalidCredentialsException** → 401 UNAUTHORIZED
5. **InvalidPasswordException** → 400 BAD REQUEST
6. **InvalidTokenException** → 401 UNAUTHORIZED
7. **AccountLockedException** → 403 FORBIDDEN
8. **BadCredentialsException** (Spring Security) → 401 UNAUTHORIZED
9. **AuthenticationException** (Spring Security) → 401 UNAUTHORIZED
10. **MethodArgumentNotValidException** → 400 BAD REQUEST (Bean Validation)
11. **IllegalArgumentException** → 400 BAD REQUEST
12. **Exception** → 500 INTERNAL SERVER ERROR (Generic fallback)

## Service Layer Updates

**UserServiceImpl** has been updated to use custom exceptions instead of `IllegalArgumentException`:

- Email/username uniqueness checks → `DuplicateUserException`
- User not found → `UserNotFoundException`
- Password validation → `InvalidPasswordException`
- Admin already exists → `UserAlreadyExistsException`

## Controller Layer Updates

### AuthController
- Removed try-catch blocks
- Exceptions are now propagated to the global handler
- Cleaner, more maintainable code

### UserController
- No changes needed
- Already lets service layer exceptions propagate
- Global handler catches all exceptions

## Benefits

1. **Consistent Error Responses**: All errors follow the same format
2. **Better Client Experience**: Meaningful error messages and proper HTTP status codes
3. **Cleaner Code**: No repetitive try-catch blocks in controllers
4. **Centralized Error Handling**: All exception logic in one place
5. **Type Safety**: Specific exception types for different error scenarios
6. **Easier Testing**: Custom exceptions are easier to test
7. **Better Logging**: Exceptions are logged with appropriate levels

## Usage Examples

### Service Layer
```java
// Before
if (userRepository.existsByEmail(email)) {
    throw new IllegalArgumentException("Email already exists");
}

// After
if (userRepository.existsByEmail(email)) {
    throw new DuplicateUserException("Email", email);
}
```

### Controller Layer
```java
// Before
try {
    User user = userService.getUserById(id);
    return ResponseEntity.ok(user);
} catch (Exception e) {
    return ResponseEntity.status(500).build();
}

// After
User user = userService.getUserById(id);
return ResponseEntity.ok(user);
// Global handler catches exceptions automatically
```

## Testing

To test exception handling:

1. **404 Error**: `GET /api/v1/users/999999` (non-existent ID)
2. **409 Error**: Create user with existing email
3. **400 Error**: Submit invalid user data (validation)
4. **401 Error**: Login with wrong credentials
5. **403 Error**: Access locked account

## Future Enhancements

Consider adding:
- Request ID tracking in error responses
- Stack traces in development mode
- Internationalized error messages
- Rate limiting exceptions
- Custom exceptions for other modules
