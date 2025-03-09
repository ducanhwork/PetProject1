package dev.anhpd.controller;

import dev.anhpd.entity.dto.request.AuthenticateRequest;
import dev.anhpd.entity.dto.request.IntrospectRequest;
import dev.anhpd.entity.dto.request.LogoutRequest;
import dev.anhpd.entity.dto.request.RefeshRequest;
import dev.anhpd.entity.dto.response.ApiResponse;
import dev.anhpd.service.implement.SecurityServiceImpl;
import dev.anhpd.service.implement.UserServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/api/auth/v1")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    SecurityServiceImpl securityService;
    UserServiceImpl userService;
    @PostMapping("/introspect")
    public ResponseEntity<ApiResponse> introspect(@RequestBody IntrospectRequest request) {
        var response = securityService.introspect(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Introspect successfully")
                .data(response)
                .code(200)
                .build()
        );
    }
    @PostMapping("/token")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody AuthenticateRequest request) {
        var response = securityService.authenticate(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Authenticate successfully")
                .data(response)
                .code(200)
                .build()
        );
    }
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@RequestBody LogoutRequest request) throws ParseException {
        securityService.logout(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Logout successfully")
                .code(200)
                .build()
        );
    }
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody RefeshRequest request) throws ParseException {
        var response = securityService.refreshToken(request);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("Refresh token successfully")
                .data(response)
                .code(200)
                .build()
        );
    }
}
