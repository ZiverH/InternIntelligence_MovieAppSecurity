package org.app.movie.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.app.movie.dto.request.LoginRequestDto;
import org.app.movie.dto.request.UserDto;
import org.app.movie.service.UserService;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
@Tag(name="User", description = "User API. Contains all operations that can be performed with user")
@Order(1)
public class UserController {

    private final UserService userService;


    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserDto userDto) {
        userService.register(userDto);
        return ResponseEntity.created(URI.create("user/")).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody @Valid LoginRequestDto requestDto) {

        return ResponseEntity.ok().body(userService.login(requestDto));
    }
}
