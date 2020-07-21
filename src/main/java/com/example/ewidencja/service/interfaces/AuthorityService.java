package com.example.ewidencja.service.interfaces;

import com.example.ewidencja.helper.AuthorityType;
import com.example.ewidencja.model.Authority;

import java.util.List;

public interface AuthorityService {
    Authority findByType(AuthorityType authorityType);
    void saveAuthority(Authority authority);
    Authority deleteAuthority(Authority authority);
    List<Authority> createOrGetAuthorities(AuthorityType[] types);
}
