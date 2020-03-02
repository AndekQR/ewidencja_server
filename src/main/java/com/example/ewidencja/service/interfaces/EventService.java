package com.example.ewidencja.service.interfaces;

import com.example.ewidencja.model.Event;

import java.util.List;

public interface EventService {
    List<Event> getEventsByUsername(String username);
    Event save(Event event, String username);
}
