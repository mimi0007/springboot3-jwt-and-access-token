package com.ownlearning.configs;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    public static final String SECRET_KEY = "dcd092f32c19b9287f38ffad97fc2b12e4fbe9ee31e0afcb370d35f19cc31b23";

    //to generate the jwt token with userDetails only
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    //to generate the jwt token with claims and userDetails
    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails
    ) {

        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername()) // email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) //15 min
                .signWith(getSigninKey(), SignatureAlgorithm.HS256) //the secret key and the algorithm
                .compact();
    }

    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    //////////////////////////

    //to have the username from the jwt token
    public String extractUserNameFromJwt(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject); //which is userEmail set in createToken method
    }

    private <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String jwtToken) {
        Jws<Claims> jwsClaims = Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(jwtToken);

        return jwsClaims.getBody();

    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String userEmailFromJwt = extractUserNameFromJwt(jwtToken);

        return userEmailFromJwt.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpirationFromJwt(jwtToken).before(new Date());
    }

    private Date extractExpirationFromJwt(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }
}
