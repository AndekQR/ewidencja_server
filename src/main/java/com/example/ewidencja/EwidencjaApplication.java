package com.example.ewidencja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages="com.example.ewidencja.repository")
public class EwidencjaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EwidencjaApplication.class, args);
    }

}
