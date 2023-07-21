package com.ownlearning.repositories;

import com.ownlearning.models.RefreshToken;
import com.ownlearning.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findTokenByUser(User user);
}
