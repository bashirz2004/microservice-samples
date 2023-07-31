package com.zamani.configurations.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtil jwtTokenUtil;

    public CustomJwtAuthenticationFilter(JwtUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            // JWT Token is in the form "Bearer token". Remove Bearer word and
            // get  only the Token
            String jwtToken = extractJwtFromRequest(request);

            if (StringUtils.hasText(jwtToken) && jwtTokenUtil.validateToken(jwtToken)) {
                UserDetails userDetails = new User(
                        jwtTokenUtil.getUsernameFromToken(jwtToken),
                        "",
                        jwtTokenUtil.getAuthoritiesFromToken(jwtToken));

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            } else {
                SecurityContextHolder.clearContext();
            }
        } catch (ExpiredJwtException ex) {
            request.setAttribute("exception", ex);
        } catch (BadCredentialsException ex) {
            request.setAttribute("exception", ex);
        }
        chain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                if ("Authorization".equalsIgnoreCase(cookie.getName()))
                    bearerToken = cookie.getValue();
            }
            if (StringUtils.hasText(bearerToken) && (bearerToken.toLowerCase().startsWith("bearer-") || bearerToken.toLowerCase().startsWith("bearer "))) {
                return bearerToken.substring(7);
            }
        }

        if (request.getHeader("Authorization") != null && (request.getHeader("Authorization").startsWith("bearer") || request.getHeader("Authorization").startsWith("bearer-"))) {
            return request.getHeader("Authorization").substring(7);
        }

        return null;
    }

}
