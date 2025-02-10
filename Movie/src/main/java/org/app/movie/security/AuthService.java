package org.app.movie.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface AuthService {

    Optional<Authentication> getAuthentication(HttpServletRequest httpServletRequest);
}
