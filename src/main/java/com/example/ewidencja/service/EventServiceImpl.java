package com.example.ewidencja.service;

import com.example.ewidencja.model.Event;
import com.example.ewidencja.model.User;
import com.example.ewidencja.repository.EventRepository;
import com.example.ewidencja.service.interfaces.EventService;
import com.example.ewidencja.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    private UserService userService;
    private EventRepository eventRepository;

    public EventServiceImpl(UserService userService, EventRepository eventRepository) {
        this.userService=userService;
        this.eventRepository=eventRepository;
    }

    public List<Event> getEventsByUsername(String username) {
        User user = this.userService.getUser(username);
//        return eventRepository.findByUser(user).orElse(Collections.emptyList());
        return user.getEvents();
    }


    public Event save(Event event, String username) {
        User user = this.userService.getUser(username);

        event.setUser(user);
        user.getEvents().add(event);

        return eventRepository.save(event);
    }
}
