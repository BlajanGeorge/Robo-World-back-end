package com.roboworldbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service for token management
 *
 * @author Blajan George
 */
@Component
public class TokenService {
    @Value("${spring.security.jwt.time-to-live}")
    private Integer timeToLive;

    @Value("${spring.security.jwt.refresh-token.time-to-live}")
    private Integer refreshTokenTimeToLive;

    @Value("${spring.security.jwt.secret}")
    private String secret;

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Integer getUserId(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("user_id", Integer.class);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userDetails.getUserId());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public String generateRefreshToken(UserDetailsImpl userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("user_id", userDetails.getUserId());
        return doGenerateRefreshToken(claims, userDetails.getUsername());
    }

    private String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenTimeToLive))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + timeToLive))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token) && validateSignature(token);
    }

    public Boolean validateToken(String token, String requestUserId) {
        Integer userId = getUserId(token);

        if (userId == null) {
            return false;
        }

        return !isTokenExpired(token) && userId.equals(Integer.parseInt(requestUserId)) && validateSignature(token);
    }

    public boolean validateSignature(String token) {
        String[] chunks = token.split("\\.");

        String jwtWithoutSignature = chunks[0] + "." + chunks[1];
        String base64UrlEncodedSignature = chunks[2];

        SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(SignatureAlgorithm.HS512, secretKeySpec);

        return validator.isValid(jwtWithoutSignature, base64UrlEncodedSignature);
    }
}
