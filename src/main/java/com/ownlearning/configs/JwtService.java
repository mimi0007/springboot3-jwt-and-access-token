package com.ownlearning.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    public static final String SECRET_KEY = "dcd092f32c19b9287f38ffad97fc2b12e4fbe9ee31e0afcb370d35f19cc31b23";

    public String generateToken(String useremail) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, useremail);
    }

    private String createToken(Map<String, Object> claims, String useremail) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(useremail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) //15 min
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String jwtToken) {
        return null;
    }

    public Claims extractAllClaims(String jwtToken) {
        Jws<Claims> jwsClaims = Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(jwtToken);

        return jwsClaims.getBody();

    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
