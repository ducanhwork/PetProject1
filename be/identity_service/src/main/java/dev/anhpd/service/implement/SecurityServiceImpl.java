package dev.anhpd.service.implement;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import dev.anhpd.entity.dto.request.AuthenticateRequest;
import dev.anhpd.entity.dto.request.IntrospectRequest;
import dev.anhpd.entity.dto.request.LogoutRequest;
import dev.anhpd.entity.dto.request.RefeshRequest;
import dev.anhpd.entity.dto.response.AuthenticateResponse;
import dev.anhpd.entity.dto.response.IntrospectResponse;
import dev.anhpd.entity.model.InvalidateToken;
import dev.anhpd.entity.model.User;
import dev.anhpd.exception.AppException;
import dev.anhpd.exception.ErrorCode;
import dev.anhpd.repository.InvalidateTokenRepository;
import dev.anhpd.repository.UserRepository;
import dev.anhpd.service.SecurityService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SecurityServiceImpl implements SecurityService {
    private static final Logger log = LogManager.getLogger(SecurityServiceImpl.class);
    UserRepository userRepository;
    InvalidateTokenRepository invalidateTokenRepository;

    @NonFinal
    @Value("${JWT_VALID_DURATION}")
    protected long jwtValidDuration;
    @NonFinal
    @Value("${JWT_REFRESH_DURATION}")
    protected long jwtRefreshDuration;
    @NonFinal
    @Value("${JWT_SIGNER_KEY}")
    protected String jwtSignerKey;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean valid = true;
        try {
            var signedJWT = verifyToken(token, false);

        } catch (AppException e) {
            valid = false;
        }
        return IntrospectResponse.builder().valid(valid).build();
    }

    @Override
    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        // find user by username
        User user = userRepository
                .findByUsername(request.getUsername())
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
        // tao header cho token
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS256);
        // payload
        // claim
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("dev.danh")
                .issueTime(new Date())
                // thoi gian het han cua token
                .expirationTime(new Date(Instant.now().plus(jwtValidDuration, ChronoUnit.SECONDS).toEpochMilli()))
                // id cua token
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());
        // tao token
        JWSObject jwsObject = new JWSObject(header, payload);
        // sign token
        try {
            jwsObject.sign(new MACSigner(jwtSignerKey.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void logout(LogoutRequest request) throws ParseException {
        try {
            // kiem tra token co hop le khong
            var signToken = verifyToken(request.getToken(), true);
            // lay thong tin user tu token
            String jit = signToken.getJWTClaimsSet().getJWTID();
            Date expiration = signToken.getJWTClaimsSet().getExpirationTime();

            InvalidateToken invalidateToken =
                    InvalidateToken.builder().id(jit).expiryTime(expiration).build();
            // luu token vao db
        } catch (AppException e) {
            log.info("Token already invalidated");
        }

    }

    /**
     * Refresh token by using refresh token
     *
     * @param refreshToken
     * @return AuthenticateResponse
     * @throws ParseException
     */
    @Override
    public AuthenticateResponse refreshToken(RefeshRequest refreshToken) throws ParseException {
        // kiem tra token co hop le khong
        var signToken = verifyToken(refreshToken.getToken(), true);
        // lay thong tin user tu token
        var jit = signToken.getJWTClaimsSet().getJWTID();
        var expiration = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidateToken invalidateToken =
                InvalidateToken.builder().id(jit).expiryTime(expiration).build();
        // luu invalidate token vao db
        invalidateTokenRepository.save(invalidateToken);
        // lay thong tin user tu token
        String username = signToken.getJWTClaimsSet().getSubject();
        User user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        // tao token moi
        return AuthenticateResponse.builder()
                .token(generateToken(user))
                .authenticated(true)
                .build();
    }

    // kiem tra token
    private SignedJWT verifyToken(String token, boolean isRefresh) {
        try {
            // verifier token
            JWSVerifier verifier = new MACVerifier(jwtSignerKey);
            // parse token
            SignedJWT signedJWT = SignedJWT.parse(token);
            // kiem tra xem token het han chua
            Date expiration =
                    (isRefresh) ?
                            new Date(signedJWT.getJWTClaimsSet().getIssueTime().toInstant().plus(jwtRefreshDuration, ChronoUnit.SECONDS).toEpochMilli()) :
                            signedJWT.getJWTClaimsSet().getExpirationTime();
            var verified = signedJWT.verify(verifier);
            if (!verified || !expiration.after(new Date())) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
            if (invalidateTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())) {
                throw new AppException(ErrorCode.INVALID_TOKEN);
            }
            return signedJWT;
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException(e);
        }
    }

    // tao scope cho token
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                stringJoiner.add("ROLE_" + role.getName());
                if (!CollectionUtils.isEmpty(role.getPermissions())) {
                    role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                }
            });
        }
        return stringJoiner.toString();
    }
}
