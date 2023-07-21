package com.ownlearning.services.impl;

import com.ownlearning.models.RefreshToken;
import com.ownlearning.repositories.RefreshTokenRepository;
import com.ownlearning.repositories.UserRepository;
import com.ownlearning.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final long EXPIRATION_TIME = 60 * 15; //15 min

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken createRefreshToken(String userEmail) {
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(1000 * EXPIRATION_TIME))
                .user(userRepository.findByUserEmail(userEmail).get())
                .build();

        refreshTokenRepository.save(refreshToken);

        return refreshToken;
    }
}
