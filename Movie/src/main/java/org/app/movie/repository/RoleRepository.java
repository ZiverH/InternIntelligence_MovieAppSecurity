package org.app.movie.repository;

import org.app.movie.model.User;
import org.app.movie.model.UserRole;
import org.app.movie.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Integer> {

    @Query("SELECT u FROM UserRole u WHERE u.role=:role")
    Optional<UserRole> findByRole(Role role);
}
