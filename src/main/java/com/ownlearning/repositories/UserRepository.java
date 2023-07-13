package com.ownlearning.repositories;

import com.ownlearning.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Long, User> {
    void findByUsername(String username);
}
