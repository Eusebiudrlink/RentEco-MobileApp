package com.example.serverrenteco.Validators;

import com.example.serverrenteco.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenValidator {
    @Autowired
    private JwtConfig jwtConfig;

    public TokenValidator() {
    }

    public boolean validateToken(String token) {

        try{
            //System.out.println(token);
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getJwtSecret()) // cheia secretă utilizată pentru semnare (dacă este semnat)
                    .parseClaimsJws(token)
                    .getBody();
            Date expiration = claims.getExpiration();
            Date now= new Date();
            if(expiration.before(now)){
                return false;
            }
            return true;
        }catch(JwtException e){
            System.out.println("Invalid token");
            System.out.println(e.getMessage());
            return false;
        }
    }
}
