package org.app.movie.service;

import lombok.RequiredArgsConstructor;
import org.app.movie.dto.request.LoginRequestDto;
import org.app.movie.dto.request.UserDto;
import org.app.movie.exception.NotFoundException;
import org.app.movie.model.User;
import org.app.movie.model.UserRole;
import org.app.movie.model.enums.Role;
import org.app.movie.repository.RoleRepository;
import org.app.movie.repository.UserRepository;
import org.app.movie.security.BaseJwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final BaseJwtService baseJwtService;


    public void register(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email is already in use");
        }

        UserRole userRole = roleRepository.findByRole(Role.ADMIN)
                .orElseThrow(() -> new IllegalStateException("ADMIN role not found"));

        User user = User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .role(userRole)
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build();

        userRepository.save(user);
    }

    public Map<String,String> login(LoginRequestDto requestDto) {

        User user = userRepository.findByEmail(requestDto.getEmail()).orElseThrow(()->new NotFoundException(User.class.getSimpleName()));
        boolean matches = passwordEncoder.matches(requestDto.getPassword(), user.getPassword());
        if(!matches) {
            throw new NotFoundException(User.class.getSimpleName());
        }
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken",baseJwtService.accessToken(user));
        tokens.put("refreshToken",baseJwtService.refreshToken(user));

        return tokens;

    }
}
