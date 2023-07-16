package com.roboworldbackend.security;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Authorization Filter for JWT authorized requests
 *
 * @author Blajan George
 */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private static final Pattern USER_ID_PATTERN = Pattern.compile("(?<=user/)([0-9]+)");

    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    public JwtAuthorizationFilter(UserDetailsService userDetailsService, TokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        Matcher userIdMatcher = USER_ID_PATTERN.matcher(request.getRequestURI());

        String userId = null;
        String username = null;
        String jwtToken = null;

        if (userIdMatcher.find()) {
            userId = userIdMatcher.group();
        }

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ") && SecurityContextHolder.getContext().getAuthentication() == null) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = tokenService.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired");
                throw e;
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetailsImpl userDetails = (UserDetailsImpl) this.userDetailsService.loadUserByUsername(username);

            boolean validation;

            if (userId != null) {
                validation = tokenService.validateToken(jwtToken, userId);
            } else {
                validation = tokenService.validateToken(jwtToken);
            }

            if (validation) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}

