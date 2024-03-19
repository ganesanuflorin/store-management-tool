package com.management.tool.store.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class UserController {

    @GetMapping(value = "/admin")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Hello from admin");
    }
}
