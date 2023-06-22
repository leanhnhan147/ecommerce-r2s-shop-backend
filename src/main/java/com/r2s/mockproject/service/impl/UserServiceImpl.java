package com.r2s.mockproject.service.impl;

import com.r2s.mockproject.entity.Cart;
import com.r2s.mockproject.entity.User;
import com.r2s.mockproject.repository.RoleRepository;
import com.r2s.mockproject.repository.UserRepository;
import com.r2s.mockproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public User findUserById(long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User addUser(Map<String, Object> newUser) {
        User user = new User();
        user.setUsername(newUser.get("username").toString());
        user.setPassword(this.passwordEncoder.encode(newUser.get("password").toString()));
        user.setFullName(newUser.get("fullName").toString());
        user.setEmail(newUser.get("email").toString());
        user.setPhone(newUser.get("phone").toString());
        user.setDeleted(false);
        user.setRoles(this.roleRepository.findByName("USER"));
        user.setCart(new Cart());
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, Map<String, Object> newUser) {
        User user = this.findUserById(id);
        user.setUsername(newUser.get("username").toString());
        user.setPassword(this.passwordEncoder.encode(newUser.get("password").toString()));
        user.setFullName(newUser.get("fullName").toString());
        user.setEmail(newUser.get("email").toString());
        user.setPhone(newUser.get("phone").toString());
        return this.userRepository.save(user);
    }
}
