package com.example.ewidencja.service;

import com.example.ewidencja.model.Event;
import com.example.ewidencja.model.User;
import com.example.ewidencja.repository.EventRepository;
import com.example.ewidencja.service.interfaces.EventService;
import com.example.ewidencja.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private UserService userService;
    private EventRepository eventRepository;

    public EventServiceImpl(UserService userService, EventRepository eventRepository) {
        this.userService=userService;
        this.eventRepository=eventRepository;
    }

    public List<Event> getEventsByUsername(String username) {
//        return eventRepository.findByUser(user).orElse(Collections.emptyList());
        User user=this.userService.getUser(username);
        return user.getEvents();
    }


    public Event save(Event event, String username) {
        User user=this.userService.getUser(username);

        event.setUser(user);
        user.getEvents().add(event);

        return eventRepository.save(event);
    }

    @Override
    public Optional<Event> update(Event event, String username) {
        if (event.getId() != null) {
            Optional<Event> fromdb=eventRepository.findById(event.getId());
            if (fromdb.isPresent() && fromdb.get().getUser().getUsername().equals(username)) {
                Event dbEvent=fromdb.get();
                dbEvent.setAccoutingNumber(event.getAccoutingNumber());
                dbEvent.setComment(event.getComment());
                dbEvent.setDate(event.getDate());
                dbEvent.setExpenses(event.getExpenses());
                dbEvent.setIncome(event.getIncome());
                return Optional.of(eventRepository.save(dbEvent));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Event> delete(Long eventId, String username) {
        Optional<Event> event=eventRepository.findById(eventId);
        if (event.isPresent() && event.get().getUser().getUsername().equals(username)) {
            eventRepository.delete(event.get());
            return event;
        }
        return Optional.empty();
    }

    @Override
    public List<Event> deleteAll(String username) {
        return eventRepository.deleteAllByUser(userService.getUser(username));
    }

    @Override
    public List<Event> saveAll(final List<Event> events, String username) {
        List<Event> myEvents=events;
        User user=userService.getUser(username);
        myEvents=myEvents.stream().peek(event -> {
            event.setUser(user);
            user.getEvents().add(event);
        }).collect(Collectors.toList());

        return eventRepository.saveAll(myEvents);

    }
}
