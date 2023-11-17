package com.habsida.moragoproject.security;

import com.habsida.moragoproject.exception.JwtTokenException;
import com.habsida.moragoproject.repository.PasswordResetRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class PasswordResetTokenGenerator {

    @Value("${password.reset.expiration}")
    private Long passwordResetExpiration;
    @Value("${password.reset.secret}")
    private String passwordResetSecret;

    private final PasswordResetRepository passwordResetRepository;

    public PasswordResetTokenGenerator(PasswordResetRepository passwordResetRepository) {
        this.passwordResetRepository = passwordResetRepository;
    }

    public String generatePasswordResetToken (String phone) {
        Date expireDate = Date.from(ZonedDateTime.now().plusMinutes(passwordResetExpiration).toInstant());

        return Jwts
                .builder()
                .setSubject(phone)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .claim("type", "password_reset")
                .signWith(SignatureAlgorithm.HS256, passwordResetSecret)
                .compact();
    }

    public Boolean validToken (String token) {
        return passwordResetRepository.existsByToken(token);
    }

    public Boolean checkExpiration (String token) {
        if (getExpirationDate(token).toInstant().compareTo(Instant.now()) < 0) {
            throw new JwtTokenException("Password Reset token was expired");
        }
        return true;
    }

    public Date getExpirationDate (String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(passwordResetSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    public String getUsernameFromToken (String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(passwordResetSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
}
