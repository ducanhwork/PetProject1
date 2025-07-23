package dev.danh.services.auth;

import dev.danh.entities.dtos.request.AuthenticationRequest;
import dev.danh.entities.dtos.request.IntrospectRequest;
import dev.danh.entities.dtos.request.LogoutRequest;
import dev.danh.entities.dtos.request.RefreshRequest;
import dev.danh.entities.dtos.response.AuthenticationResponse;
import dev.danh.entities.dtos.response.IntrospectResponse;

public interface AuthService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request);
    void logout(LogoutRequest token);
    AuthenticationResponse refreshToken(RefreshRequest request);
}
