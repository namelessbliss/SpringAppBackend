package com.nb.backend.security;

import com.nb.backend.constants.Constants;
import com.nb.backend.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtGenerator {

    public String generate(JwtUser jwtUser) {
        Claims claims = Jwts.claims()
                .setSubject(jwtUser.getUsername());
        claims.put(Constants.USER_ID, jwtUser.getId() + "");

        //Construye JWT
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, Constants.SECRET_KEY)
                .compact();
    }
}
