package dev.anhpd.service;

import java.text.ParseException;

import dev.anhpd.entity.dto.request.AuthenticateRequest;
import dev.anhpd.entity.dto.request.IntrospectRequest;
import dev.anhpd.entity.dto.request.LogoutRequest;
import dev.anhpd.entity.dto.request.RefeshRequest;
import dev.anhpd.entity.dto.response.AuthenticateResponse;
import dev.anhpd.entity.dto.response.IntrospectResponse;
import dev.anhpd.entity.model.User;

public interface SecurityService {
    IntrospectResponse introspect(IntrospectRequest request);

    AuthenticateResponse authenticate(AuthenticateRequest request);

    String generateToken(User user);

    void logout(LogoutRequest request) throws ParseException;

    AuthenticateResponse refreshToken(RefeshRequest refreshToken) throws ParseException;
}
