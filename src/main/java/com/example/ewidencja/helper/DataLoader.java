package com.example.ewidencja.helper;

import com.example.ewidencja.model.Authority;
import com.example.ewidencja.model.Event;
import com.example.ewidencja.model.User;
import com.example.ewidencja.service.interfaces.AuthorityService;
import com.example.ewidencja.service.interfaces.EventService;
import com.example.ewidencja.service.interfaces.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;

@Component
public class DataLoader implements CommandLineRunner {

    private AuthorityService authorityService;
    private UserService userService;
    private EventService eventService;

    public DataLoader(AuthorityService authorityService, UserService userService, EventService eventService) {
        this.authorityService=authorityService;
        this.userService=userService;
        this.eventService=eventService;
    }

    @Override
    public void run(String... args) throws Exception {
//        authorityService.saveAuthority(new Authority(AuthorityType.ROLE_USER));
//        authorityService.saveAuthority(new Authority(AuthorityType.ROLE_ADMIN));
//        userService.save(new User("John", "Doe", "john@mail.com", true, "admin123", authorityService.createOrGetAuthorities(new AuthorityType[]{AuthorityType.ROLE_USER})));
//        Event event =new Event(LocalDate.now(), "12345", 222.3, 300.2, "");
//        eventService.save(event, "john@mail.com");
    }
}
