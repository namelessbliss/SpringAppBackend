package com.nb.backend.config;

import com.nb.backend.security.JwtAuthenticationEntryPoint;
import com.nb.backend.security.JwtAuthenticationProvider;
import com.nb.backend.security.JwtAuthenticationTokenFilter;
import com.nb.backend.security.JwtSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.stereotype.Component;

import java.util.Collections;

@EnableWebSecurity
@Component
/**
 * Sobrescribe metodos para obtener propias configuraciones de securidad,
 * Analiza un JWT cuando es recibido y validarlo
 */
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter() {
        JwtAuthenticationTokenFilter tokenFilter = new JwtAuthenticationTokenFilter();
        tokenFilter.setAuthenticationManager(authenticationManager());
        tokenFilter.setAuthenticationSuccessHandler(new JwtSuccessHandler());

        return tokenFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
