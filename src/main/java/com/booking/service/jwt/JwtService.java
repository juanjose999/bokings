package com.booking.service.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
@Service
public class JwtService {

    public static final String SECRET = "8F433340F48C9F204C2770973A5B1BC4379593EE841A840D6DF5FC5D46B09E0CF853FC370525FEC8BFE9672BAB3E3CB10671F5808C26BAD946E23194EC8C47E";

    private static final long PLUSTIME = TimeUnit.MINUTES.toMillis(30);
    public String generateJwt(UserDetails userDetails){
        Map<String, String> customerDetails = new HashMap<>();
        customerDetails.put("Hola perro","Hola java");
        return Jwts.builder()
                .claims(customerDetails)
                .subject(userDetails.getUsername())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusMillis(PLUSTIME)))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey(){
        byte[] bytesKey  = Base64.getDecoder().decode(SECRET);
        return Keys.hmacShaKeyFor(bytesKey);
    }

    public String extractUsername(String jwt) {
        Claims claims = Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
        return claims.getSubject();
    }

    public Claims extractAllClaims(String jwt) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(jwt)
                .getPayload();
    }

    public boolean isValidToken(String jwt) {
        return extractAllClaims(jwt).getExpiration().after(Date.from(Instant.now()));
    }
}
