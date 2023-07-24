package com.ownlearning.services.impl;

import com.ownlearning.configs.JwtService;
import com.ownlearning.models.RefreshToken;
import com.ownlearning.repositories.RefreshTokenRepository;
import com.ownlearning.repositories.UserRepository;
import com.ownlearning.requests.RefreshTokenRequest;
import com.ownlearning.responses.AuthResponse;
import com.ownlearning.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private static final long EXPIRATION_TIME = 60 * 15; //15 min

    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtService jwtService;

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

    @Override
    public AuthResponse getJwtTokenByRefreshToken(RefreshTokenRequest refreshTokenRequest) {

        RefreshToken refreshToken = findTokenFromDb(refreshTokenRequest.getRefreshToken()).orElseThrow();
        UserDetails userDetails = isTokenValid(refreshToken).getUser();
        String newJwtToken = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .status(HttpStatusCode.valueOf(200).value())
                .jwtToken(newJwtToken)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .message("New jwt token is generated successfully!")
                .build();
    }

    private RefreshToken isTokenValid(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("This token was expired! Make a new login request");
        }

        return refreshToken;
    }

    private Optional<RefreshToken> findTokenFromDb(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }
}
