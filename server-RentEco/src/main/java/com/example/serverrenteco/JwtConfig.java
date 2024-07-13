package com.example.serverrenteco;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;


@Configuration
public class JwtConfig {
    private SecretKey jwtSecret=Keys.secretKeyFor(SignatureAlgorithm.HS512);
    @Value("${jwt.expiration}")
    private int jwtExpiration;

     //@Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }

    public SecretKey getJwtSecret() {
        return jwtSecret;
    }

    public int getJwtExpiration() {
        return jwtExpiration;
    }
}
