package com.zamani.configurations.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
public class JwtUtil {

    private final CustomUserDetailsService customUserDetailsService;

    public JwtUtil(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    private String secret;
    private int jwtExpirationInMs;

    @Value("${spring.security.jwt.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Value("${spring.security.jwt.expirationDateInMs}")
    public void setJwtExpirationInMs(int jwtExpirationInMs) {
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username-claim",userDetails.getUsername());
        claims.put("authorities-claim",userDetails.getAuthorities());
        claims.put("custom-claim","hiiiiii");
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }

    public boolean validateToken(String authToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            claimsJws.getBody();
            return true;
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", ex);
        } catch (ExpiredJwtException ex) {
            throw new BadCredentialsException("EXPIRED_CREDENTIALS", ex);
        }
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public List<SimpleGrantedAuthority> getAuthoritiesFromToken(String token) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (GrantedAuthority authority : customUserDetailsService.loadUserByUsername(getUsernameFromToken(token)).getAuthorities())
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        return authorities;
    }

}
