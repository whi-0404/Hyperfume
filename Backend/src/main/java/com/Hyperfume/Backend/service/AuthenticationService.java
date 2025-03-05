package com.Hyperfume.Backend.service;

import com.Hyperfume.Backend.dto.request.AuthenticationRequest;
import com.Hyperfume.Backend.dto.request.IntrospectRequest;
import com.Hyperfume.Backend.dto.request.LogoutRequest;
import com.Hyperfume.Backend.dto.request.RefreshRequest;
import com.Hyperfume.Backend.dto.response.AuthenticationResponse;
import com.Hyperfume.Backend.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
    void logout(LogoutRequest request) throws ParseException, JOSEException;
    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
