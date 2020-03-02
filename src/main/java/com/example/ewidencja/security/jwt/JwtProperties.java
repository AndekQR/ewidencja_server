package com.example.ewidencja.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="jwt")
public class JwtProperties {

    private String secretKey;
    private Integer validityInMs;

    public JwtProperties() {
        secretKey = "secret";
        validityInMs = 3600000;
    }

    public JwtProperties(String secretKey, Integer validityInMs) {
        this.secretKey = secretKey;
        this.validityInMs = validityInMs;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey=secretKey;
    }

    public Integer getValidityInMs() {
        return validityInMs;
    }

    public void setValidityInMs(Integer validityInMs) {
        this.validityInMs=validityInMs;
    }
}
