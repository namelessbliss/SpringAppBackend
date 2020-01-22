package com.nb.backend.security;

import com.nb.backend.constants.Constants;
import com.nb.backend.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

@Component
public class JwtValidator {

    public JwtUser validate(String token) {
        JwtUser jwtUser = null;

        try {
            Claims body = Jwts.parser()
                    .setSigningKey(Constants.SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();

            jwtUser = new JwtUser();
            jwtUser.setUsername(body.getSubject());
            jwtUser.setId(Long.parseLong((String) body.get(Constants.USER_ID)));
        } catch (Exception e) {
            System.out.println(e);
        }

        return jwtUser;
    }
}
