package org.app.movie.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.app.movie.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;


@Component
@Slf4j
@RequiredArgsConstructor
public final class BaseJwtService {

    private SecretKey key;

    @Value("${spring.security.jwt-secret-key}")
    private String jwtSecretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes;
        keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }

    public Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }

    public String accessToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("name",user.getName())
                .claim("surname",user.getSurname())
                .claim("role",user.getRole().getRole().toString())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(Duration.ofMinutes(5))))
                .header()
                .add("tokenType", "access")
                .and()
                .signWith(key)
                .compact();
    }

    public String refreshToken(User user) {
        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("name",user.getName())
                .claim("surname",user.getSurname())
                .claim("role",user.getRole().getRole().toString())
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(Duration.ofMinutes(10))))
                .header()
                .add("tokenType", "refresh")
                .and()
                .signWith(key)
                .compact();
    }
}

