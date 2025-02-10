package org.app.movie.config;

import lombok.RequiredArgsConstructor;
import org.app.movie.security.AuthRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthRequestFilter authRequestFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**").permitAll()
        );

        http.authorizeHttpRequests(auth -> auth.requestMatchers(
                "user/login",
                "user/register"
        ).permitAll());


        //GENRE
        http.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.POST,"genre").hasAuthority("ADMIN"));
        http.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.DELETE,"genre/{id}").hasAuthority("ADMIN"));
        http.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.PUT,"genre/{id}").hasAuthority("ADMIN"));

        //MOVIE
        http.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.POST,"movie").hasAuthority("ADMIN"));
        http.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.DELETE,"movie/{id}").hasAuthority("ADMIN"));
        http.authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.PUT,"movie/{id}").hasAuthority("ADMIN"));

        http.authorizeHttpRequests(auth->auth.anyRequest().authenticated());
        http.addFilterBefore(authRequestFilter, UsernamePasswordAuthenticationFilter.class);





        return http.build();
    }
}
