package com.booking.booking.controller;

import com.booking.booking.dto.RegisterRequest;
import com.booking.booking.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerNewUser(@Valid @RequestBody RegisterRequest registerRequest){
        // Handle MethodArgumentNotValidException

        authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
