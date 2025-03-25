package com.Hyperfume.Backend.service;

import java.text.ParseException;

import jakarta.servlet.http.HttpServletResponse;

import com.Hyperfume.Backend.dto.request.AuthenticationRequest;
import com.Hyperfume.Backend.dto.request.IntrospectRequest;
import com.Hyperfume.Backend.dto.request.LogoutRequest;
import com.Hyperfume.Backend.dto.request.RefreshRequest;
import com.Hyperfume.Backend.dto.response.AuthenticationResponse;
import com.Hyperfume.Backend.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;

public interface AuthenticationService {
    AuthenticationResponse authenticate(AuthenticationRequest request, HttpServletResponse response);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
