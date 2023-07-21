package com.ownlearning.services.impl;

import com.ownlearning.configs.JwtService;
import com.ownlearning.models.Role;
import com.ownlearning.models.User;
import com.ownlearning.repositories.UserRepository;
import com.ownlearning.requests.AuthRequest;
import com.ownlearning.requests.RegisterRequest;
import com.ownlearning.responses.AuthResponse;
import com.ownlearning.responses.RegisterResponse;
import com.ownlearning.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authManager;

    private final JwtService jwtService;

    @Override
    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .name(registerRequest.getName())
                .userEmail(registerRequest.getUserEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        if (isUserExisted(user)) {
            return RegisterResponse.builder()
                    .message("User already exist!")
                    .build();
        } else {
            userRepository.save(user);
        }

        return RegisterResponse.builder()
                .status(HttpStatusCode.valueOf(200).value())
                .message("Registration Successful")
                .build();

    }

    private boolean isUserExisted(User user) {
        var userFromDb = userRepository.findByUserEmail(user.getUserEmail());

        return userFromDb.isPresent();
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        doAuthenticate(authRequest);

        var user = userRepository.findByUserEmail(authRequest.getUserEmail())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);

        return AuthResponse.builder()
                .status(HttpStatusCode.valueOf(200).value())
                .jwtToken(jwtToken)
                .message("Login Successful")
                .build();
    }

    //this will authenticate whether the login credentials is in db or not
    private void doAuthenticate(AuthRequest authRequest) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getUserEmail(),
                            authRequest.getPassword())
            );
        } catch (BadCredentialsException exception) {
            throw new RuntimeException("Invalid user email or password!");
        }
    }
}
