package com.booking.booking.controller;

import com.booking.booking.dto.LoginDTO;
import com.booking.booking.dto.RegisterRequest;
import com.booking.booking.security.JwtUtil;
import com.booking.booking.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtils;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, JwtUtil jwtUtils){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerNewUser(@Valid @RequestBody RegisterRequest registerRequest){
        // Handle MethodArgumentNotValidException

        authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginDTO loginDTO){
        try{

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginDTO.email(),
                    loginDTO.password()
            ));

            final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ResponseEntity.ok().body(jwtUtils.generateToken(userDetails.getUsername()));

        }catch(BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Username or Password not valid");
        }
    }

}
