package org.app.movie.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class AuthRequestFilter extends OncePerRequestFilter {
    private final List<JwtAuthService> authServices;

    public AuthRequestFilter(List<JwtAuthService> authServices) {
        this.authServices = authServices;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        Optional<Authentication> authOptional = Optional.empty();
        for (AuthService authService : authServices){
            authOptional =authService.getAuthentication(request);

        }
        authOptional.ifPresent(auth-> {
            SecurityContextHolder.getContext().setAuthentication(auth);

        });
        filterChain.doFilter(request,response);
    }
}
