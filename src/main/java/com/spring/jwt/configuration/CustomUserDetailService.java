/*
package com.spring.jwt.configuration;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class CustomUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("rahul")) {

            return new User("Rahul","Dora",new ArrayList<>());
        }else {
            throw new UsernameNotFoundException("User not found");
        }

    }
}
*/
