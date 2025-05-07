package com.Hyperfume.Backend.configuration.security;

import java.io.IOException;
import java.util.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class JwtCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            if (request.getCookies() != null) {
                Optional<Cookie> jwtCookie = Arrays.stream(request.getCookies())
                        .filter(cookie -> "jwt".equals(cookie.getName()))
                        .findFirst();

                if (jwtCookie.isPresent()) {
                    String token = jwtCookie.get().getValue();
                    if (token != null && !token.isEmpty()) {
                        HttpServletRequest wrappedRequest = new HttpServletRequestWrapper(request) {
                            @Override
                            public String getHeader(String name) {
                                if ("Authorization".equalsIgnoreCase(name)) {
                                    return "Bearer " + token;
                                }
                                return super.getHeader(name);
                            }

                            @Override
                            public Enumeration<String> getHeaders(String name) {
                                if ("Authorization".equalsIgnoreCase(name)) {
                                    return Collections.enumeration(List.of("Bearer " + token));
                                }
                                return super.getHeaders(name);
                            }

                            @Override
                            public Enumeration<String> getHeaderNames() {
                                List<String> headerNames = Collections.list(super.getHeaderNames());
                                if (!headerNames.contains("Authorization")) {
                                    headerNames.add("Authorization");
                                }
                                return Collections.enumeration(headerNames);
                            }
                        };

                        filterChain.doFilter(wrappedRequest, response);
                        return;
                    }
                }
            }

        filterChain.doFilter(request, response);
    }
}