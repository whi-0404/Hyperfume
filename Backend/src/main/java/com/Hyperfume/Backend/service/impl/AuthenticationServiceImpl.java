package com.Hyperfume.Backend.service.impl;

import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

import com.Hyperfume.Backend.dto.request.*;
import com.Hyperfume.Backend.repository.RoleRepository;
import com.Hyperfume.Backend.repository.httpClient.OutboundIdentityClient;
import com.Hyperfume.Backend.repository.httpClient.OutboundUserClient;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Hyperfume.Backend.dto.response.AuthenticationResponse;
import com.Hyperfume.Backend.dto.response.IntrospectResponse;
import com.Hyperfume.Backend.entity.InvalidatedToken;
import com.Hyperfume.Backend.entity.User;
import com.Hyperfume.Backend.exception.AppException;
import com.Hyperfume.Backend.exception.ErrorCode;
import com.Hyperfume.Backend.repository.InvalidatedTokenRepository;
import com.Hyperfume.Backend.repository.UserRepository;
import com.Hyperfume.Backend.service.AuthenticationService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    OutboundIdentityClient outboundIdentityClient;
    OutboundUserClient outboundUserClient;
    RoleRepository roleRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @NonFinal
    @Value("${outbound.identity.client-id}")
    protected String CLIENT_ID;

    @NonFinal
    @Value("${outbound.identity.client-secret}")
    protected String CLIENT_SECRET;

    @NonFinal
    @Value("${outbound.identity.redirect-uri}")
    protected String REDIRECT_URI;

    @NonFinal
    protected String GRANT_TYPE = "authorization_code";

    protected static final int MAX_AGE_AT_COOKIE = 60*60;
    protected static final int MAX_AGE_RT_COOKIE = 7 * 24 * 60 * 60;

    public AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response) {
        var user = userRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if (!authenticated) throw new AppException(ErrorCode.UNAUTHENTICATED);

        var token = generateToken(user, false);
        var tokenRefresh = generateToken(user, true);

        setTokenToCookie(token, false, response);
        setTokenToCookie(tokenRefresh, true, response);

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(tokenRefresh)
                .build();
    }

    public AuthenticationResponse outboundAuthenticate(String code, HttpServletResponse httpServletResponse){
        var response = outboundIdentityClient.exchangeToken(ExchangeTokenRequest.builder()
                        .clientId(CLIENT_ID)
                        .clientSecret(CLIENT_SECRET)
                        .redirectUri(REDIRECT_URI)
                        .code(code)
                        .grantType(GRANT_TYPE)
                .build());

        var userInfo = outboundUserClient.getUserInfo("json", response.getAccessToken());

        //Onboarding user
        User user = userRepository.findByUsername(userInfo.getEmail()).orElseGet(
                () -> userRepository.save(User.builder()
                                .username(userInfo.getEmail())
                                .email(userInfo.getEmail())
                                .role(roleRepository.findByName("USER")
                                        .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTED)))
                        .build())
        );

        String tokenFromUserGG = generateToken(user, false);
        String tokenRefresh = generateToken(user, true);

        setTokenToCookie(tokenFromUserGG, false, httpServletResponse);
        setTokenToCookie(tokenRefresh, true, httpServletResponse);

        return AuthenticationResponse.builder()
                .token(tokenFromUserGG)
                .refreshToken(tokenRefresh)
                .build();
    }

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        ErrorCode errorCode = null;
        boolean isvalid = true;
        try {
            verifyToken(token);
        } catch (AppException e) {
            isvalid = false;
            errorCode = e.getErrorCode();
        }

        return IntrospectResponse.builder()
                .valid(isvalid)
                .errorCode(errorCode)
                .build();
    }

    public void logout(HttpServletRequest request, HttpServletResponse response)
            throws ParseException{

        var signToken = SignedJWT.parse(request.getHeader("Authorization").substring(7));

        String jit = signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder().id(jit).expiryTime(expiryTime).build();

        invalidatedTokenRepository.save(invalidatedToken);

        removeCookie("jwt", "/", response);
        removeCookie("refresh_jwt", "/", response);
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        // Get SIGNER_KEY
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        // Get ExpirationTime
        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        // Check SIGNER_KEY
        var verified = signedJWT.verify(verifier);
        if (!(verified))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if(!(expiryTime.after(new Date()))){
            throw new AppException(ErrorCode.EXPIRED_TOKEN);
        }
        // Check logout
        if (invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }

    public AuthenticationResponse refreshToken(String refreshToken, HttpServletRequest request, HttpServletResponse response)
            throws ParseException, JOSEException {

        var signedJWT = verifyToken(refreshToken);

        var jit = signedJWT.getJWTClaimsSet().getJWTID();
        var expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        //Check blackList
        if(invalidatedTokenRepository.existsById(jit)){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        InvalidatedToken invalidatedToken =
                InvalidatedToken.builder()
                        .id(jit).
                        expiryTime(expiryTime)
                        .build();
        //Save into blackList
        invalidatedTokenRepository.save(invalidatedToken);

        var username = signedJWT.getJWTClaimsSet().getSubject();

        var user =
                userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

        var newAccessToken = generateToken(user, false);
        var newRefreshToken = generateToken(user, true);

        setTokenToCookie(newRefreshToken, true, response);
        setTokenToCookie(newAccessToken, false, response);

        return AuthenticationResponse.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    private String generateToken(User user, boolean isRefresh) {
        // header of token
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        // payload of token
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("hyperfume.com")
                .issueTime(new Date())
                .expirationTime(Date.from(Instant.now().plus(isRefresh ? Duration.ofDays(7) : Duration.ofSeconds(10))))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }

    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (user.getRole() != null) {
            stringJoiner.add(user.getRole().getName());
        }
        return stringJoiner.toString();
    }

    private void setTokenToCookie(String token, boolean isRefresh, HttpServletResponse response){
        Cookie cookie = new Cookie(isRefresh ? "refresh_jwt" : "jwt", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(isRefresh ? MAX_AGE_RT_COOKIE : MAX_AGE_AT_COOKIE);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    private void removeCookie(String name, String path, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, null);
        cookie.setHttpOnly(true);
        cookie.setPath(path);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
