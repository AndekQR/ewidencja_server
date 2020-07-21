package com.example.ewidencja.repository;

import com.example.ewidencja.model.Event;
import com.example.ewidencja.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // email = username
    Optional<User> findByEventsContains(Event events);
}
