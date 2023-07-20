package com.ownlearning.services;

import com.ownlearning.requests.AuthRequest;
import com.ownlearning.requests.RegisterRequest;
import com.ownlearning.responses.AuthResponse;
import com.ownlearning.responses.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);

    AuthResponse login(AuthRequest authRequest);
}
