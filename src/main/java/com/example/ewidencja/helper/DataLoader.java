package com.example.ewidencja.helper;

import com.example.ewidencja.service.interfaces.AuthorityService;
import com.example.ewidencja.service.interfaces.EventService;
import com.example.ewidencja.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class DataLoader implements CommandLineRunner {

    private AuthorityService authorityService;
    private UserService userService;
    private EventService eventService;
    private Logger logger=LoggerFactory.getLogger(DataLoader.class);

    public DataLoader(AuthorityService authorityService, UserService userService, EventService eventService) {
        this.authorityService=authorityService;
        this.userService=userService;
        this.eventService=eventService;
    }

    @Override
    public void run(String... args) throws Exception {
//        authorityService.saveAuthority(new Authority(AuthorityType.ROLE_USER));
//        authorityService.saveAuthority(new Authority(AuthorityType.ROLE_ADMIN));
//        userService.save(new User("John", "Doe", "daniellegawiec20@gmail.com", true, "admin123", authorityService.createOrGetAuthorities(new AuthorityType[]{AuthorityType.ROLE_USER})));
//        Event event =new Event(LocalDate.now(), "12345", 222.3, 300.2, "");
//        eventService.save(event, "daniellegawiec20@gmail.com");

//        User user=userService.findByEmail("john@mail.com").orElse(null);
//        user.getEvents().forEach((event1)->{
//            logger.info(event1.getExpenses().toString());
//        });

    }
}
