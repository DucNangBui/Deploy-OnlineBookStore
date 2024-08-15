package com.ecommerce.id.vn.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final JwtBlacklist jwtBlacklist;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private static final List<String> EXCLUDED_PATHS = Arrays.asList(
            "/api/auth/**",
            "/api/guest/**",
            "/images/**"
    );

    @Autowired
    public JwtRequestFilter(@Lazy UserDetailsService userDetailsService, JwtUtil jwtUtil, JwtBlacklist jwtBlacklist) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.jwtBlacklist = jwtBlacklist;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        logger.info("Request URL: " + request.getRequestURL());
        logger.info("Servlet Path: " + request.getServletPath());
        logger.info("Context Path: " + request.getContextPath());

        final String requestTokenHeader = request.getHeader("Authorization");
        String email = null;
        String jwtToken = null;

        String requestPath = request.getServletPath();
        if (EXCLUDED_PATHS.stream().anyMatch(path -> pathMatcher.match(path, requestPath))) {
            chain.doFilter(request, response);
            return;
        }

        if (request.getServletPath().equals("/logout")) {
            chain.doFilter(request, response);
            return;
        }

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                if (jwtBlacklist.isBlacklisted(jwtToken)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has been blacklisted");
                    return;
                }

                email = jwtUtil.extractEmail(jwtToken);
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
