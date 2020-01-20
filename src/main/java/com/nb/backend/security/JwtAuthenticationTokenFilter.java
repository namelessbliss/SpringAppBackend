package com.nb.backend.security;

import com.nb.backend.constants.Constants;
import com.nb.backend.model.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {

    protected JwtAuthenticationTokenFilter() {
        super("/api/**");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException, ServletException {

        String header = httpServletRequest.getHeader(Constants.AUTHORIZATION_HEADER);
        if (header == null || header.startsWith(Constants.BEARER_TOKEN)) {
            throw new RuntimeException("JWT es incorrecto");
        }

        String authenticationToken = header.substring(7); //7 es el numero de caracteres de "Bearer "
        JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken);

        return getAuthenticationManager().authenticate(token);
    }
}
