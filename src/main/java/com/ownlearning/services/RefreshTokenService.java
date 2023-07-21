package com.ownlearning.services;

import com.ownlearning.models.RefreshToken;

public interface RefreshTokenService {
    RefreshToken createRefreshToken(String userEmail);
}
