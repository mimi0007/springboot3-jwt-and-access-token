package com.ownlearning.configs;

import com.ownlearning.repositories.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService {

    private UserRepository userRepository;

    //will help to know whether the user is present in db or not
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User is not found!"));
    }
}
