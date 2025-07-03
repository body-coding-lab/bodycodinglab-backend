package com.bcl.fitmate.backend.provider;

import com.bcl.fitmate.backend.common.constants.ResponseMessage;
import com.bcl.fitmate.backend.common.enums.user.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final Key key;
    private final long jwtExpirationMs;
    private final long jwtResetPasswordExpirationMs;

    public long getExpirationMs() {
        return jwtExpirationMs;
    }

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long jwtExpirationMs,
            @Value("${jwt.reset-password-expiration-ms}") long jwtResetPasswordExpirationMs
    ) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtExpirationMs = jwtExpirationMs;
        this.jwtResetPasswordExpirationMs = jwtResetPasswordExpirationMs;
    }

    // UserPrincipal 구현 후, 수정 예정
    public String generateJwtToken(Long userId, UserRole role) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateResetPasswordJwtToken(String email) {
        return Jwts.builder()
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtResetPasswordExpirationMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String removeBearer(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException(ResponseMessage.INVALID_TOKEN);
        }

        return token.substring("Bearer ".length());
    }

    public boolean isValidJwtToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromJwtToken(String token) {
        Claims claims = getClaims(token);
        return claims.get("email", String.class);
    }

    public Claims getClaims(String token) {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        return jwtParser.parseClaimsJws(token).getBody();
    }
}
