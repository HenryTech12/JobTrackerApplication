package org.jobtracker.app.UserService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.jobtracker.app.UserService.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {

    private String secretKey = null;
    public JwtService() {
        try {
            KeyGenerator keyGenerator  = KeyGenerator.getInstance("HmacSHA256");
            secretKey = Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
        }
        catch(NoSuchAlgorithmException noSuchAlgorithmException) {
            log.error("An Error Ocurred!!!: {}",noSuchAlgorithmException.getMessage());
        }

    }

    public String generateToken(UserDTO userDTO) {
        Map<String,String> claims = new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(userDTO.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() * 1000 + 10))
                .signWith(getKey())
                .compact();
    }

    public SecretKey getKey() {
       return Keys
               .hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    public Claims extractClaim(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build().parseSignedClaims(token)
                .getPayload();
    }

    public <T>T extractClaims(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractClaim(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        return new Date(System.currentTimeMillis()).after(extractClaims(token,Claims::getExpiration));
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return (!isTokenExpired(token) && Objects.equals(extractUsername(token), userDetails.getUsername()));
    }

    public String extractUsername(String token) {
        return extractClaims(token,Claims::getSubject);
    }
}
