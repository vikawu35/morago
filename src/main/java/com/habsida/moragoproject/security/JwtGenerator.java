package com.habsida.moragoproject.security;

import com.habsida.moragoproject.exception.JwtTokenException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class JwtGenerator {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long jwtExpiration;


    public String generateJwtToken(UserDetails userDetails) {
        return generateTokenFromUsername(userDetails.getUsername());
    }

    public String generateTokenFromUsername(String username) {
        Date expireDate = new Date();
        Duration duration = Duration.ofMillis(jwtExpiration);
        expireDate.setTime(expireDate.getTime() + duration.toMillis());

        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String getUsernameFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenException("Jwt token is expired");
        } catch (JwtException ex) {
            throw new JwtTokenException("Jwt token is incorrect");
        }
    }
}
