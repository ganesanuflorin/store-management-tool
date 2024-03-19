package com.management.tool.store.controller;

import com.management.tool.store.dto.AuthenticationResponse;
import com.management.tool.store.dto.ResponseDto;
import com.management.tool.store.dto.UserDto;
import com.management.tool.store.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/user")
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody UserDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody UserDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
