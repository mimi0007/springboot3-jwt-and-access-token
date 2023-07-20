package com.ownlearning.configs;

import com.ownlearning.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class CustomUserDetailsService implements UserDetailsService {

    //use autowire in this case as a bean of this service is created in security config and raising a constructor issue
    @Autowired
    private UserRepository userRepository;

    //will help to know whether the user is present in db or not
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userRepository.findByUserEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
