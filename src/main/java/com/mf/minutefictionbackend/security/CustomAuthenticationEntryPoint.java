package com.mf.minutefictionbackend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
    throws IOException {
        response.setContentType("application/json");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        response.getWriter().write("Invalid username or password. Please try to log in again.");
    }

}
