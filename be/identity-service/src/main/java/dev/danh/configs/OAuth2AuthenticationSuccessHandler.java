package dev.danh.configs;

import dev.danh.entities.dtos.apps.CustomOAuth2User;
import dev.danh.entities.dtos.request.AuthenticationRequest;
import dev.danh.entities.dtos.response.AuthenticationResponse;
import dev.danh.repositories.user.UserRepository;
import dev.danh.services.auth.impl.AuthServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private AuthServiceImpl authService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        String providerId = oAuth2User.getUser().getProviderId();

        AuthenticationRequest authRequest = AuthenticationRequest.builder()
                .username(email)
                .password(providerId) // Assuming providerId is used as a password for OAuth2
                .build();
        AuthenticationResponse token =  authService.authenticate(authRequest);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write("{\"accessToken\": \"" + token.getToken());
        response.getWriter().flush();
    }

}
