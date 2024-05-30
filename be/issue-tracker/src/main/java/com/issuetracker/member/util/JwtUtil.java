package com.issuetracker.member.util;

import com.issuetracker.global.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

public class JwtUtil {
    public static final long REFRESH_EXPIRATION_TIME = 86400000; // 1일
    public static final long ACCESS_EXPIRATION_TIME = 3600000; // 1시간
    @Value("${spring.jwt.refresh-key}")
    private String refreshSecretKey;
    @Value("${spring.jwt.access-key}")
    private String accessSecretKey;


    public String createAccessToken(String memberId) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("memberId", memberId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(accessSecretKey.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(String memberId) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("memberId", memberId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(refreshSecretKey.getBytes()), SignatureAlgorithm.HS512)
                .compact();
    }


    public String extractJwtToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }


    public Claims validateAccessToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(accessSecretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims validateRefreshToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(refreshSecretKey.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException();
        }
    }

    public String extractMemberId(Claims claims) {
        return claims.get("memberId").toString();
    }
}
