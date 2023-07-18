package com.ownlearning.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;

@Service
public class JwtService {

    public static final String SECRET_KEY = "and0cmVmcmVzaHRva2Vu";

    public String extractUsername(String jwtToken) {
        return null;
    }

    private Claims extractAllClaims(String jwtToken) {
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
