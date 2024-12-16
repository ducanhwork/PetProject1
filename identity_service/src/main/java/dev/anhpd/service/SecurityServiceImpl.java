package dev.anhpd.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.anhpd.entity.dto.request.AuthenticateRequest;
import dev.anhpd.entity.dto.request.IntrospectRequest;
import dev.anhpd.entity.dto.response.AuthenticateResponse;
import dev.anhpd.entity.dto.response.IntrospectResponse;
import dev.anhpd.entity.model.User;
import dev.anhpd.exception.AppException;
import dev.anhpd.exception.ErrorCode;
import dev.anhpd.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SecurityServiceImpl implements SecurityService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    @NonFinal
    @Value("${JWT_SIGNER_KEY}")
    protected String jwtSignerKey;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        try {
            //verifier token
            JWSVerifier verifier = new MACVerifier(jwtSignerKey);
            //parse token
            SignedJWT signedJWT = SignedJWT.parse(token);
            //kiem tra xem token het han chua
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified = signedJWT.verify(verifier);
            String role = signedJWT.getJWTClaimsSet().getStringClaim("scope");
            return IntrospectResponse.builder()
                    .valid(verified && expiration.after(new Date()))
                    .role(role)
                    .build();
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        //find user by username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return AuthenticateResponse.builder()
                    .token(generateToken(user))
                    .authenticated(true)
                    .build();
        } else {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        }
    }

    @Override
    public String generateToken(User user) {
        //tao header cho token
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        //payload
        //claim
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("dev.danh")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                //id cua token
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        //tao token
        JWSObject jwsObject = new JWSObject(header, payload);
        //sign token
        try {
            jwsObject.sign(new MACSigner(jwtSignerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
    public String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!user.getRole().isEmpty()){
            user.getRole().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }

}
