package dev.anhpd.config;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.anhpd.entity.dto.response.ApiResponse;
import dev.anhpd.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        log.info("Responding with unauthorized error. Message - {}", authException.getMessage());
        ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;
        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // tao response
        ApiResponse apiResponse = ApiResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        // chuyen response thanh json
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        // gui request ve client
        response.flushBuffer();
    }
}
