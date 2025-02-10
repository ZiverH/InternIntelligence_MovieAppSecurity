package org.app.movie.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.app.movie.model.UserRole;
import org.app.movie.model.enums.Role;
import org.app.movie.repository.RoleRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleInitializer {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        for (Role role : Role.values()) {
            roleRepository.findByRole(role).orElseGet(() -> {
                UserRole userRole = new UserRole();
                userRole.setRole(role);
                return roleRepository.save(userRole);
            });
        }
    }
}
