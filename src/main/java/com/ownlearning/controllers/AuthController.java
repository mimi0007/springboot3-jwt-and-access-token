package com.ownlearning.controllers;

import com.ownlearning.requests.AuthRequest;
import com.ownlearning.requests.RegisterRequest;
import com.ownlearning.responses.AuthResponse;
import com.ownlearning.responses.RegisterResponse;
import com.ownlearning.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authManager;

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUserEmail(),
                        authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(authService.login(authRequest));
        } else {
            throw new UsernameNotFoundException("User is not valid!");
        }

    }


}
