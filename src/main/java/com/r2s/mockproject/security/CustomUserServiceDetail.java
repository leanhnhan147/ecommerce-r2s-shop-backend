package com.r2s.mockproject.security;

import java.util.Optional;

import com.r2s.mockproject.entity.User;
import com.r2s.mockproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserServiceDetail implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // can phai sua lai model, username phai unique, va findByName return optional
        Optional<User> user = this.userRepository.findByUsername(username);
        if (user.isPresent()) {
            return new CustomUserDetail(user.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
