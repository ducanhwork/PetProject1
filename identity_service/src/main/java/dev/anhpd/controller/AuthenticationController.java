package dev.anhpd.controller;

import dev.anhpd.entity.dto.request.AuthenticateRequest;
import dev.anhpd.entity.dto.request.IntrospectRequest;
import dev.anhpd.entity.dto.response.ApiResponse;
import dev.anhpd.service.SecurityServiceImpl;
import dev.anhpd.service.UserServiceImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
