package dev.anhpd.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    UNKNOWN_ERROR("Unknown error", 500, HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHORIZED("Unauthorized", 403, HttpStatus.UNAUTHORIZED),
    UNAUTHENTICATED("You do not have access to this api", 403, HttpStatus.FORBIDDEN),
    INVALID_CREDENTIALS("Invalid credentials", 401, HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("User not found", 404, HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS("User already exists", 409, HttpStatus.CONFLICT),
    USER_FOUND("User found", 200, HttpStatus.OK),
    USERS_FOUND("Users found", 200, HttpStatus.OK),
    EMAIL_EXISTED("Email existed", 409, HttpStatus.CONFLICT),
    INVALID_EMAIL_FORMAT("Invalid email format", 400, HttpStatus.BAD_REQUEST),
    NULL_INPUT("Input must be not null", 400, HttpStatus.BAD_REQUEST),
    LENGTH_USERNAME("Username must be between 5 and 50 characters", 400, HttpStatus.BAD_REQUEST),
    LENGTH_PASSWORD("Password must be at least 8 characters", 400, HttpStatus.BAD_REQUEST),
    INVALID_DOB("Invalid date of birth, your age must be greater than {min}", 400, HttpStatus.BAD_REQUEST);


    private String message;
    private int code;
    private HttpStatus httpStatusCode;

    ErrorCode(String s, int i, HttpStatus httpStatusCode) {
        this.message = s;
        this.code = i;
        this.httpStatusCode = httpStatusCode;
    }
}
