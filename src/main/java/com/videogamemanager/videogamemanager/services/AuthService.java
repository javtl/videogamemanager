package com.videogamemanager.videogamemanager.services;

import com.videogamemanager.videogamemanager.models.User;
import com.videogamemanager.videogamemanager.models.Role;
import com.videogamemanager.videogamemanager.repository.UserRepository;
import com.videogamemanager.videogamemanager.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // Por defecto, nuevos registros son ROLE_USER
        if (user.getRole() == null) user.setRole(Role.ROLE_USER);
        return userRepository.save(user);
    }

    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        return jwtUtils.generateToken((User) authentication.getPrincipal());
    }
}