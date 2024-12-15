package com.Hyperfume.Backend.controller;

import com.nimbusds.jose.JOSEException;
import com.Hyperfume.Backend.dto.request.ApiResponse;
import com.Hyperfume.Backend.dto.request.AuthenticationRequest;
import com.Hyperfume.Backend.dto.request.IntrospectRequest;
import com.Hyperfume.Backend.dto.response.AuthenticationResponse;
import com.Hyperfume.Backend.dto.response.IntrospectResponse;
import com.Hyperfume.Backend.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
    {
        var result=authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();

    }
    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result=authenticationService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();
    }
}
