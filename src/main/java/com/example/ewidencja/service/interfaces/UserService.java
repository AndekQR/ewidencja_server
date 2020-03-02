package com.example.ewidencja.service.interfaces;

import com.example.ewidencja.model.Event;
import com.example.ewidencja.model.User;

import java.util.Optional;

public interface UserService {
    User findById(Long id);
    Optional<User> findByEmail(String email);
    void save(User user);
    User update(User user);
    User getUser(String username);
}
