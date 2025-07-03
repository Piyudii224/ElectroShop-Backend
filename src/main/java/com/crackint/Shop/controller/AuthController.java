package com.crackint.Shop.controller;

import com.crackint.Shop.dto.AuthRequest;
import com.crackint.Shop.dto.AuthResponse;
import com.crackint.Shop.model.User;
import com.crackint.Shop.repository.UserRepository;
import com.crackint.Shop.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    // ✅ Register a new user and return JWT + userId
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (repo.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        User savedUser = repo.save(user);

        String token = jwtUtil.generateToken(savedUser.getUsername());

        return ResponseEntity.ok(new AuthResponse(token, savedUser.getId()));
    }

    // ✅ Login and return token + userId
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(),
                    authRequest.getPassword()
                )
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        String token = jwtUtil.generateToken(authRequest.getUsername());

        Optional<User> optionalUser = repo.findByUsername(authRequest.getUsername());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            return ResponseEntity.ok(new AuthResponse(token, user.getId()));
        } else {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    // ✅ Get logged-in user details from JWT token
    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUser(@RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            String username = jwtUtil.extractUsername(token);

            User user = repo.findByUsername(username)
                            .orElseThrow(() -> new RuntimeException("User not found"));

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token or user not found");
        }
    }
}
