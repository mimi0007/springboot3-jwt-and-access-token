package com.ownlearning.controllers;

import com.ownlearning.requests.AuthRequest;
import com.ownlearning.requests.RefreshTokenRequest;
import com.ownlearning.requests.RegisterRequest;
import com.ownlearning.responses.AuthResponse;
import com.ownlearning.responses.RegisterResponse;
import com.ownlearning.services.AuthService;
import com.ownlearning.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {

        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {

        return ResponseEntity.ok(authService.login(authRequest));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {

        return ResponseEntity.ok(refreshTokenService.getJwtTokenByRefreshToken(refreshTokenRequest));
    }


}
