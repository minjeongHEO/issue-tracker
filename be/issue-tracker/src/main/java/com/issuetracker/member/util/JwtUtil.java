package com.issuetracker.member.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    private static final int REFRESH_EXPIRATION_TIME = 86400000; // 1일
    private static final int ACCESS_EXPIRATION_TIME = 3600000; // 1시간
    private final byte[] refreshSecretKey = "3BwWtXpk2qJBp6ZmagqpmbhImwxkW98fpryd6IbxmaBa0dOcOhM6L15cwlID5Rwu".getBytes();
    private final byte[] accessSecretKey = "VR708fpKdTAAJAXukOMgW7U5p4RXqCNJDB67C4U77SfDNVQdq7F0GcWKVtixj93x".getBytes();

    public String createAccessToken(String memberId) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("memberId", memberId)
                .claim("url", "sadasldjsadlksajdlksajdlsajdlksajdlksadjlaksjdlasdjdoqwdjqoidjqwodijqdoqwj")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(accessSecretKey), SignatureAlgorithm.HS512)
                .compact();
    }

    public String createRefreshToken(String memberId) {
        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .claim("memberId", memberId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
                .signWith(Keys.hmacShaKeyFor(refreshSecretKey), SignatureAlgorithm.HS512)
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
                .setSigningKey(Keys.hmacShaKeyFor(accessSecretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Claims validateRefreshToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(refreshSecretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractMemberId(Claims claims) {
        return claims.get("memberId").toString();
    }
}
