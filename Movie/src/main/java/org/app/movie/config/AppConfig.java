package org.app.movie.config;

import org.app.movie.security.JwtCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
//@Import(GlobalExceptionHandler.class)
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public JwtCredentials jwtCredentials() {
        return new JwtCredentials();
    }
}
