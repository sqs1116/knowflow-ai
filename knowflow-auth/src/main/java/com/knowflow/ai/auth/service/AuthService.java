package com.knowflow.ai.auth.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.knowflow.ai.auth.entity.User;
import com.knowflow.ai.auth.mapper.UserMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;

    public AuthService(UserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       JwtTokenService jwtTokenService) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenService = jwtTokenService;
    }

    public String login(String username, String rawPassword) {
        if (username == null || username.trim().isEmpty()
                || rawPassword == null || rawPassword.isEmpty()) {
            return null;
        }

        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, username.trim()));

        if (user == null || !Integer.valueOf(1).equals(user.getStatus())
                || !passwordEncoder.matches(rawPassword, user.getPassword())) {
            return null;
        }

        return jwtTokenService.generate(user);
    }

    @Transactional
    public User register(String username, String rawPassword) {
        String normalizedUsername = normalizeAndValidate(username, rawPassword);

        Long count = userMapper.selectCount(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername, normalizedUsername));
        if (count != null && count > 0) {
            return null;
        }

        User user = new User();
        user.setUsername(normalizedUsername);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());

        try {
            userMapper.insert(user);
            return user;
        } catch (DuplicateKeyException exception) {
            return null;
        }
    }

    private String normalizeAndValidate(String username, String rawPassword) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username must not be blank");
        }
        String normalizedUsername = username.trim();
        if (normalizedUsername.length() > 64) {
            throw new IllegalArgumentException("Username must not exceed 64 characters");
        }
        if (rawPassword == null || rawPassword.length() < 6 || rawPassword.length() > 72) {
            throw new IllegalArgumentException("Password length must be between 6 and 72 characters");
        }
        return normalizedUsername;
    }
}
