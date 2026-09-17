package com.knowflow.ai.auth.controller;

import com.knowflow.ai.auth.dto.LoginRequest;
import com.knowflow.ai.auth.dto.LoginResponse;
import com.knowflow.ai.auth.dto.RegisterResponse;
import com.knowflow.ai.auth.entity.User;
import com.knowflow.ai.auth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class LoginController {

    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", "Invalid username or password"));
        }
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest request) {
        try {
            User user = authService.register(request.getUsername(), request.getPassword());
            if (user == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Collections.singletonMap("message", "Username already exists"));
            }
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new RegisterResponse(user.getId(), user.getUsername()));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", exception.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Collections.singletonMap("message", "Logged out"));
    }
}
