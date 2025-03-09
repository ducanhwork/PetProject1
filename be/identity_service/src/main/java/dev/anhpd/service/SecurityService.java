package dev.anhpd.service;

import dev.anhpd.entity.dto.request.AuthenticateRequest;
import dev.anhpd.entity.dto.request.IntrospectRequest;
import dev.anhpd.entity.dto.request.LogoutRequest;
import dev.anhpd.entity.dto.response.AuthenticateResponse;
import dev.anhpd.entity.dto.response.IntrospectResponse;
import dev.anhpd.entity.model.User;

import java.text.ParseException;

public interface SecurityService {
    IntrospectResponse introspect(IntrospectRequest request);
    AuthenticateResponse authenticate(AuthenticateRequest request);
    String generateToken(User user);
    void logout(LogoutRequest request) throws ParseException;
}
