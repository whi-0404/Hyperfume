package com.Hyperfume.Backend.configuration;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

public class CookieBearerTokenResolver implements BearerTokenResolver {

    @Override
    public String resolve(HttpServletRequest request) {
        // First, check if the token is in the request attributes (set by our filter)
        Object tokenAttribute = request.getAttribute("jwt_token");
        if (tokenAttribute != null) {
            return tokenAttribute.toString();
        }

        // Fallback to header-based token (standard behavior)
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }

        return null;
    }
}
