package com.example.ticketingcatalog.infrastructure.adapters.in.web;

import com.example.ticketingcatalog.domain.model.UserModel;
import com.example.ticketingcatalog.infrastructure.security.JwtUtil;
import com.example.ticketingcatalog.infrastructure.service.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(UserDetailsServiceImpl userService,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody RegisterRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            return "El username ya existe";
        }

        UserModel user = new UserModel();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Collections.singleton(request.getRole()));

        userService.saveUser(user);
        return "Usuario registrado correctamente";
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody AuthRequest request) {
        UserModel user = userService.loadUserByUsernameModel(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRoles());
        return new AuthResponse(token, user.getUsername(), user.getRoles().iterator().next());
    }
}
