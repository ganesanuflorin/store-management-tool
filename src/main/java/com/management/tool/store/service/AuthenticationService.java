package com.management.tool.store.service;

import com.management.tool.store.dto.AuthenticationResponse;
import com.management.tool.store.dto.ResponseDto;
import com.management.tool.store.dto.UserDto;
import com.management.tool.store.entity.User;
import com.management.tool.store.enums.Role;
import com.management.tool.store.exceptions.UsernameAlreadyUsed;
import com.management.tool.store.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);


    public ResponseDto register(UserDto request) {
        User user = new User();
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyUsed("User already exist");
        }
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);
        logger.info("User with username: " + user.getUsername() + " was created");
        return new ResponseDto("User was created successfully.");
    }

    public AuthenticationResponse authenticate(UserDto request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token = jwtService.generateToken(user);
        logger.info("User with username: " + user.getUsername() + " was login");
        return new AuthenticationResponse(token);
    }
}
