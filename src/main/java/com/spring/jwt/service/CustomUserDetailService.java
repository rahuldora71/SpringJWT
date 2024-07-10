package com.spring.jwt.service;

import com.spring.jwt.entities.User;
import com.spring.jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // load user by database
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }
}
