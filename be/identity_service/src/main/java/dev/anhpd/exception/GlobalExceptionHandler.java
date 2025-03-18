package dev.anhpd.exception;

import java.nio.file.AccessDeniedException;
import java.util.Map;
import java.util.Objects;

import jakarta.validation.ConstraintViolation;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import dev.anhpd.entity.dto.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final String MIN_ATTRIBUTE = "min";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        AppException appException = new AppException(ErrorCode.UNKNOWN_ERROR);
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .message(e.getMessage())
                        .code(appException.getErrorCode().getCode())
                        .build());
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse> handleAppException(AppException e) {
        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .message(e.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(401)
                .body(ApiResponse.builder()
                        .message(e.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(401)
                .body(ApiResponse.builder()
                        .message(e.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> attr = null;
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.UNKNOWN_ERROR;
        try {
            errorCode = ErrorCode.valueOf(message);
            // get constraint violation from exception
            var constraintViolation = e.getBindingResult().getAllErrors().get(0).unwrap(ConstraintViolation.class);
            // get min value of constraint
            attr = constraintViolation.getConstraintDescriptor().getAttributes();
            return ResponseEntity.badRequest()
                    .body(ApiResponse.builder()
                            .message(
                                    Objects.nonNull(attr)
                                            ? mapAttribute(errorCode.getMessage(), attr)
                                            : errorCode.getMessage())
                            .code(errorCode.getCode())
                            .build());
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.builder()
                            .message(message)
                            .code(errorCode.getCode())
                            .build());
        }
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        ErrorCode errorCode = null;
        if (e.getMessage().contains("email")) {
            errorCode = ErrorCode.EMAIL_EXISTED;
        } else if (e.getMessage().contains("username")) {
            errorCode = ErrorCode.USER_ALREADY_EXISTS;
        } else {
            errorCode = ErrorCode.UNKNOWN_ERROR;
        }
        return ResponseEntity.badRequest()
                .body(ApiResponse.builder()
                        .message(errorCode.getMessage())
                        .code(errorCode.getCode())
                        .build());
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {
        String min = attributes.get(MIN_ATTRIBUTE).toString();
        message = message.replace("{" + MIN_ATTRIBUTE + "}", min);
        return message;
    }
}
