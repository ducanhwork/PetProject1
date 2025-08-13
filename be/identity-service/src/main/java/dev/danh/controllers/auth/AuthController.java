package dev.danh.controllers.auth;

import dev.danh.entities.dtos.request.AuthenticationRequest;
import dev.danh.entities.dtos.request.IntrospectRequest;
import dev.danh.entities.dtos.request.LogoutRequest;
import dev.danh.entities.dtos.response.APIResponse;
import dev.danh.services.auth.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthService authService;

    @PostMapping("/token")
    public ResponseEntity<APIResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var response = authService.authenticate(request);
        return ResponseEntity.ok(APIResponse.builder()
                .message("Authenticate successfully")
                .data(response)
                .statusCode(200)
                .build());
    }

    @PostMapping("/introspect")
    public ResponseEntity<APIResponse> introspect(@RequestBody IntrospectRequest request) {
        var response = authService.introspect(request);
        return ResponseEntity.ok(APIResponse.builder()
                .message("Introspect successfully")
                .data(response)
                .statusCode(200)
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<APIResponse> logout(@RequestBody LogoutRequest request) {
        authService.logout(request);
        return ResponseEntity.ok(APIResponse.builder()
                .message("Log out successfully")
                .statusCode(200)
                .build());
    }
    @PostMapping("/refresh")
    public ResponseEntity<APIResponse> refreshToken(@RequestBody dev.danh.entities.dtos.request.RefreshRequest request) {
        var response = authService.refreshToken(request);
        return ResponseEntity.ok(APIResponse.builder()
                .message("Refresh token successfully")
                .data(response)
                .statusCode(200)
                .build());
    }
    @GetMapping("/login/google")
    public ResponseEntity<?> googleCallback() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Redirect to /oauth2/authorization/google");
        response.put("url", "/oauth2/authorization/google");
        return ResponseEntity.ok(APIResponse.builder()
                .message("Redirect to Google OAuth2 login")
                .data(response)
                .statusCode(200)
                .build());
    }
    @GetMapping("/failure")
    public ResponseEntity<APIResponse> failure(@RequestParam String error) {
        return ResponseEntity.badRequest().body(APIResponse.builder()
                .message("Authentication failed: " + error)
                .statusCode(400)
                .build());
    }

}
