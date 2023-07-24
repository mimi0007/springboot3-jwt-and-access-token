package com.ownlearning.services;

import com.ownlearning.models.RefreshToken;
import com.ownlearning.requests.RefreshTokenRequest;
import com.ownlearning.responses.AuthResponse;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String userEmail);

    AuthResponse getJwtTokenByRefreshToken(RefreshTokenRequest refreshTokenRequest);
}
