package com.kieran.jwt.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthProvider authProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest req,
            HttpServletResponse resp,
            FilterChain filterChain) throws ServletException, IOException {
        String header = req.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] authElements = header.split(" ");
            if (authElements.length == 2 && authElements[0].equals("Bearer")) {
                try {
                    if (req.getMethod().equals("GET"))
                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(authProvider.validateToken(authElements[1]));
                    else
                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(authProvider.validateTokenStrong(authElements[1]));
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }
        filterChain.doFilter(req, resp);
    }
}
