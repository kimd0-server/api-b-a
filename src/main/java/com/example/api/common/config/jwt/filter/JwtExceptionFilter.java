package com.example.api.common.config.jwt.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (SignatureException e) {
            log.error("SignatureException JWT token");
            response.sendRedirect("/exception/signature");
        } catch (MalformedJwtException e) {
            log.error("MalformedJwtException JWT token");
            response.sendRedirect("/exception/malformed");
        } catch (ExpiredJwtException e) {
            log.error("ExpiredJwtException JWT token");
            response.sendRedirect("/exception/expired");
        } catch (UnsupportedJwtException e) {
            log.error("UnsupportedJwtException JWT token");
            response.sendRedirect("/exception/unsupported");
        } catch (IllegalArgumentException e) {
            log.error("IllegalArgumentException JWT claims string is empty.");
            response.sendRedirect("/exception/illegal");
        }
    }
}
