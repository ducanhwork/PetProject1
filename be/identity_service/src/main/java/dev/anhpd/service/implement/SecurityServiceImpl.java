package dev.anhpd.service.implement;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import dev.anhpd.entity.dto.request.AuthenticateRequest;
import dev.anhpd.entity.dto.request.IntrospectRequest;
import dev.anhpd.entity.dto.request.LogoutRequest;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SecurityServiceImpl implements SecurityService {
    UserRepository userRepository;
    InvalidateTokenRepository invalidateTokenRepository;
    @NonFinal
    @Value("${JWT_SIGNER_KEY}")
    protected String jwtSignerKey;

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) {
        var token = request.getToken();
        boolean valid = true;
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
    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
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
                //thoi gian het han cua token
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

    @Override
    public void logout(LogoutRequest request) throws ParseException {
        //kiem tra token co hop le khong
        var signToken = verifyToken(request.getToken());

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiration = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidateToken invalidateToken = InvalidateToken.builder()
                .id(jit)
                .expiryTime(expiration)
                .build();
        //luu token vao db
        invalidateTokenRepository.save(invalidateToken);

    }

    //kiem tra token
    private SignedJWT verifyToken(String token) {
        try {
            //verifier token
            JWSVerifier verifier = new MACVerifier(jwtSignerKey);
            //parse token
            SignedJWT signedJWT = SignedJWT.parse(token);
            //kiem tra xem token het han chua
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
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

    //tao scope cho token
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                        stringJoiner.add("ROLE_" + role.getName());
                        if (!CollectionUtils.isEmpty(role.getPermissions())) {
                            role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                        }
                    }
            );
        }
        return stringJoiner.toString();
    }

}
