package com.example.ewidencja.repository;

import com.example.ewidencja.helper.AuthorityType;
import com.example.ewidencja.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthorityType(AuthorityType authorityType);
}
