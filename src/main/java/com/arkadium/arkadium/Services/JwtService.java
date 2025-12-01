package com.arkadium.arkadium.services;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.arkadium.arkadium.Model.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    @Value("${jwt.secret-key}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private Long jwtExpiration;
    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    // Generacion de token
    public String generateToken(final User user){
        return buildToken(user, jwtExpiration);
    }
    // Generacion de refresh token
    public String generateRefreshToken(final User user){
        return buildToken(user, refreshTokenExpiration);
    }

    // Construccion del token
    private String buildToken(final User user, final Long expiration){
        return Jwts.builder()
                .id(user.getId().toString())
                .claims(Map.of("name", user.getNombres()))
                .subject(user.getCorreo())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }
    
    // Obtencion de la secret-key para llave secreta del token
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
