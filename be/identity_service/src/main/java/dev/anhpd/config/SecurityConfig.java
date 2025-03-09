package dev.anhpd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final String[] PUBLIC_ENDPOINTS = {"/api/auth/v1/**", "/api/user/v1/add", "/swagger-ui/**", "/v3/api-docs/**"};
    @Autowired
    private CustomJwtDecoder customJwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //config endpoint
        http.authorizeHttpRequests(request -> request
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                .anyRequest().authenticated()
        );
        //disable csrf
        http.csrf(AbstractHttpConfigurer::disable);
        //jwt
        http.oauth2ResourceServer(oauth2 -> oauth2
                /**
                 * Configures this application as an OAuth2 Resource Server.
                 * This enables authentication via JWT tokens.
                 */
                .jwt(jwtConfigurer -> jwtConfigurer
                        /**
                         * Specifies a custom JWT decoder to parse and validate JWT tokens.
                         * The decoder is responsible for verifying the signature and extracting claims from the token.
                         */
                        .decoder(customJwtDecoder)

                        /**
                         * Configures a custom JWT authentication converter.
                         * This converter extracts claims from the JWT and maps them to Spring Security authorities.
                         */
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())
                )

                /**
                 * Configures a custom AuthenticationEntryPoint to handle unauthorized access.
                 * This ensures a proper response when authentication fails due to missing or invalid JWT tokens.
                 */
                .authenticationEntryPoint(new JWTAuthenticationEntryPoint())
        );

        return http.build();
    }


    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtAuthenticationConverter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
