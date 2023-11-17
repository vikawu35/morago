package com.habsida.moragoproject.security;

import com.habsida.moragoproject.exception.JwtTokenException;
import com.habsida.moragoproject.model.entity.RefreshToken;
import com.habsida.moragoproject.repository.RefreshTokenRepository;
import com.habsida.moragoproject.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

@Service
public class RefreshTokenGenerator {

    @Value("${jwt.refresh.secret}")
    private String refreshTokenSecret;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Autowired
    public RefreshTokenGenerator(RefreshTokenRepository refreshTokenRepository, UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
    }

    public Optional<RefreshToken> findByToken (String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken (String username) {
        RefreshToken refreshToken = new RefreshToken();
        Optional<RefreshToken> existingRefToken = refreshTokenRepository.findByUser(userService.findByPhone(username));
        if (existingRefToken.isPresent()) {
            refreshToken.setUser(existingRefToken.get().getUser());
            refreshToken.setId(existingRefToken.get().getId());
        } else {
            refreshToken.setUser(userService.findByPhone(username));
        }

        refreshToken.setExpiryDate(ZonedDateTime.now().toInstant().plusMillis(refreshTokenExpiration));
        refreshToken.setToken(generateJwtRefreshToken(username));
        refreshToken = refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new JwtTokenException("Refresh token was expired");
        }
        return token;
    }

    private String generateJwtRefreshToken(String username) {
        Date expireDate = Date.from(ZonedDateTime.now().toInstant().plusMillis(refreshTokenExpiration));
        return Jwts
                .builder()
                .setIssuedAt(new Date())
                .setSubject(username)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256,refreshTokenSecret)
                .compact();
    }
}
