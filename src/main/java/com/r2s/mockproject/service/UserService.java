package com.r2s.mockproject.service;

import com.r2s.mockproject.entity.User;


import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    List<User> getAllUser();

    User findUserById(long id);

    Optional<User> findByUsername(String username);

    User addUser(Map<String, Object> newUser);

    User updateUser(Long id, Map<String, Object> newUser);
}
