package com.example.ewidencja.repository;

import com.example.ewidencja.model.JwtRefreshToken;
import com.example.ewidencja.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRefreshTokenRepository extends JpaRepository<JwtRefreshToken, String> {
        void deleteByUser(User user);
}