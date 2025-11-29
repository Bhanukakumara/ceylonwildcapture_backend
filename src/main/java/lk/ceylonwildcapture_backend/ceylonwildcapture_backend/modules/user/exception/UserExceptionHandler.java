package lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.exception;

import lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for user module.
 * Handles all exceptions thrown by user-related controllers and services.
 */
@RestControllerAdvice(basePackages = "lk.ceylonwildcapture_backend.ceylonwildcapture_backend.modules.user")
@Slf4j
public class UserExceptionHandler {

    private static final String UNAUTHORIZED = "Unauthorized";
    private static final String BAD_REQUEST = "Bad Request";
    private static final String URI_PREFIX = "uri=";

    /**
     * Handle UserNotFoundException
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(
            UserNotFoundException ex, WebRequest request) {
        log.error("User not found: {}", ex.getMessage());

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handle DuplicateUserException
     */
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponseDto> handleDuplicateUserException(
            DuplicateUserException ex, WebRequest request) {
        log.error("Duplicate user: {}", ex.getMessage());

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Conflict")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handle UserAlreadyExistsException
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(
            UserAlreadyExistsException ex, WebRequest request) {
        log.error("User already exists: {}", ex.getMessage());

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("Conflict")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    /**
     * Handle InvalidCredentialsException
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentialsException(
            InvalidCredentialsException ex, WebRequest request) {
        log.error("Invalid credentials: {}", ex.getMessage());

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(UNAUTHORIZED)
                .message(ex.getMessage())
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle InvalidPasswordException
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidPasswordException(
            InvalidPasswordException ex, WebRequest request) {
        log.error("Invalid password: {}", ex.getMessage());

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(BAD_REQUEST)
                .message(ex.getMessage())
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle InvalidTokenException
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidTokenException(
            InvalidTokenException ex, WebRequest request) {
        log.error("Invalid token: {}", ex.getMessage());

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(UNAUTHORIZED)
                .message(ex.getMessage())
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle AccountLockedException
     */
    @ExceptionHandler(AccountLockedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccountLockedException(
            AccountLockedException ex, WebRequest request) {
        log.error("Account locked: {}", ex.getMessage());

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.FORBIDDEN.value())
                .error("Forbidden")
                .message(ex.getMessage())
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    /**
     * Handle JSON parsing errors (HttpMessageNotReadableException)
     */
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadableException(
            org.springframework.http.converter.HttpMessageNotReadableException ex, WebRequest request) {
        log.error("Invalid JSON format: {}", ex.getMessage());

        String message = "Invalid request body format";
        if (ex.getCause() != null) {
            String causeMessage = ex.getCause().getMessage();
            if (causeMessage != null && causeMessage.contains("Cannot map `null` into type")) {
                message = "Invalid request: required fields cannot be null";
            } else if (causeMessage != null) {
                message = "Invalid JSON format: " + causeMessage.split("\n")[0];
            }
        }

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(BAD_REQUEST)
                .message(message)
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle validation errors (Bean Validation)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(
            MethodArgumentNotValidException ex, WebRequest request) {
        log.error("Validation error: {}", ex.getMessage());

        Map<String, String> validationErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            validationErrors.put(fieldName, errorMessage);
        });

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Validation Failed")
                .message("Invalid input data")
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .validationErrors(validationErrors)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle Spring Security BadCredentialsException
     */
    @ExceptionHandler(org.springframework.security.authentication.BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentialsException(
            org.springframework.security.authentication.BadCredentialsException ex, WebRequest request) {
        log.error("Bad credentials: {}", ex.getMessage());

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(UNAUTHORIZED)
                .message("Invalid username or password")
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle Spring Security AuthenticationException (parent of BadCredentialsException)
     */
    @ExceptionHandler(org.springframework.security.core.AuthenticationException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthenticationException(
            org.springframework.security.core.AuthenticationException ex, WebRequest request) {
        log.error("Authentication failed: {}", ex.getMessage());

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.UNAUTHORIZED.value())
                .error(UNAUTHORIZED)
                .message("Authentication failed: " + ex.getMessage())
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Handle MethodArgumentTypeMismatchException (e.g., invalid enum values)
     */
    @ExceptionHandler(org.springframework.web.method.annotation.MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentTypeMismatchException(
            org.springframework.web.method.annotation.MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error("Method argument type mismatch: {}", ex.getMessage());

        String message = String.format("Invalid value '%s' for parameter '%s'", ex.getValue(), ex.getName());

        if (ex.getRequiredType() != null && ex.getRequiredType().isEnum()) {
            Object[] enumConstants = ex.getRequiredType().getEnumConstants();
            if (enumConstants != null && enumConstants.length > 0) {
                String validValues = String.join(", ",
                    java.util.Arrays.stream(enumConstants)
                        .map(Object::toString)
                        .toArray(String[]::new));
                message = String.format("Invalid value '%s' for parameter '%s'. Valid values are: %s",
                    ex.getValue(), ex.getName(), validValues);
            }
        }

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(BAD_REQUEST)
                .message(message)
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(
            IllegalArgumentException ex, WebRequest request) {
        log.error("Illegal argument: {}", ex.getMessage());

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(BAD_REQUEST)
                .message(ex.getMessage())
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle generic exceptions
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(
            Exception ex, WebRequest request) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);

        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An unexpected error occurred. Please try again later.")
                .path(request.getDescription(false).replace(URI_PREFIX, ""))
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
