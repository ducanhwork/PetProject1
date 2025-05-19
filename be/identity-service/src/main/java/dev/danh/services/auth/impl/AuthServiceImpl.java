package dev.danh.services.auth.impl;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.danh.entities.dtos.request.AuthenticationRequest;
import dev.danh.entities.dtos.request.IntrospectRequest;
import dev.danh.entities.dtos.request.LogoutRequest;
import dev.danh.entities.dtos.response.AuthenticationResponse;
import dev.danh.entities.dtos.response.IntrospectResponse;
import dev.danh.entities.models.InvalidToken;
import dev.danh.entities.models.User;
import dev.danh.enums.ErrorCode;
import dev.danh.exception.AppException;
import dev.danh.repositories.user.InvalidTokenRepository;
import dev.danh.repositories.user.UserRepository;
import dev.danh.services.auth.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Service
public class AuthServiceImpl implements AuthService {


    private static final Logger log = LogManager.getLogger(AuthServiceImpl.class);
    InvalidTokenRepository invalidTokenRepository;
    UserRepository userRepository;
    @Value("${jwt.secret}")
    @NonFinal
    String SECRET_KEY; // Replace with a secure key

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS);
        } else {
            return AuthenticationResponse.builder()
                    .token(generateToken(user.getUsername()))
                    .authenticated(true)
                    .build();
        }
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        Boolean valid = true;
        String token = request.getToken();
        try {
            var signedJWT = verifyToken(token);
        } catch (AppException e) {
            valid = false;
        }
        return IntrospectResponse.builder()
                .valid(valid)
                .build();
    }

    @Override
    public void logout(LogoutRequest token) {
        try {
            var verifyToken = verifyToken(token.getToken());
            String jid = verifyToken.getJWTClaimsSet().getJWTID();
            Date expiryDate = verifyToken.getJWTClaimsSet().getExpirationTime();
            InvalidToken invalidToken = InvalidToken.builder()
                    .id(jid)
                    .expiryTime(expiryDate)
                    .build();
            //save expireDate token to db
            invalidTokenRepository.save(invalidToken);
        }catch (AppException | ParseException e ){
            log.info("Token already invalidated");
        }

    }

    public SignedJWT verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());
            var isValid = signedJWT.verify(verifier);
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (isValid && expirationTime != null && expirationTime.after(new Date())) {
                return signedJWT;
            } else {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.INVALID_TOKEN);
        }
    }


    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("dev.danh")
                .expirationTime(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour expiration
                .jwtID(UUID.randomUUID().toString())
                .build();
        // Here you would sign the token with your secret key and return it
        Payload payload = new Payload(claimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
        } catch (JOSEException e) {
            throw new RuntimeException("Error signing JWT", e);
        }
        return jwsObject.serialize();
    }

}
