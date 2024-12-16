package dev.anhpd.service;

import dev.anhpd.entity.dto.request.AuthenticateRequest;
import dev.anhpd.entity.dto.request.IntrospectRequest;
import dev.anhpd.entity.dto.response.AuthenticateResponse;
import dev.anhpd.entity.dto.response.IntrospectResponse;
import dev.anhpd.entity.model.User;

public interface SecurityService {
    IntrospectResponse introspect(IntrospectRequest request);
    AuthenticateResponse authenticate(AuthenticateRequest request);
    String generateToken(User user);
}
