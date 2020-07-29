package com.example.ewidencja.service.interfaces;

import com.example.ewidencja.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    List<Event> getEventsByUsername(String username);
    Event save(Event event, String username);
    Optional<Event> update(Event event, String username);
    Optional<Event> delete(Long eventId, String username);
    List<Event> deleteAll(String username);
    List<Event> saveAll(List<Event> events, String username);
}
