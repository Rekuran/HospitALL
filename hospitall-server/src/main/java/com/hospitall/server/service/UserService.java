package com.hospitall.server.service;

import com.hospitall.server.model.User;
import com.hospitall.server.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean register(String username, String password) {
        if (userRepository.existsByUsername(username)) return false;
        String hash = passwordEncoder.encode(password);
        userRepository.save(new User(username, hash, "USER"));
        return true;
    }

    public boolean authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .map(u -> passwordEncoder.matches(password, u.getPasswordHash()))
                .orElse(false);
    }
}
