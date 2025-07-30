package com.plazoleta.users.users.infrastructure.security.utils;

import com.plazoleta.users.users.domain.model.UserModel;
import com.plazoleta.users.users.infrastructure.security.config.JwtConfig;
import com.plazoleta.users.users.infrastructure.security.models.JwtClaimsModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;
    private Key secretKey;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @PostConstruct
    private void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtConfig.getSecret().getBytes());
    }

    public String generateToken(UserModel user) {
        JwtClaimsModel claims = new JwtClaimsModel(user.getId(), user.getRole().getName());

        return Jwts.builder()
                .claim(SecurityConstants.ID_CLAIM, claims.getId())
                .claim(SecurityConstants.ROLE_CLAIM, claims.getRole())
                .setSubject(user.getId().toString())
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate())
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractRole(String token) {
        return extractClaims(token).get(SecurityConstants.ROLE_CLAIM, String.class);
    }

    public Long extractId(String token) {
        return extractClaims(token).get(SecurityConstants.ID_CLAIM, Long.class);
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtConfig.getExpirationMillis());
    }
}
