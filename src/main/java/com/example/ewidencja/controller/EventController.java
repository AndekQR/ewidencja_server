package com.example.ewidencja.controller;

import com.example.ewidencja.model.Event;
import com.example.ewidencja.service.interfaces.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService=eventService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Event>> getAll(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        List<Event> events = eventService.getEventsByUsername(username);
        return ResponseEntity.ok(events);
    }

    @PostMapping("/new")
    public ResponseEntity<Event> newEvent(@AuthenticationPrincipal UserDetails userDetails,
                                           @RequestBody Event event) {
        String username = userDetails.getUsername();
        return ResponseEntity.ok(eventService.save(event, username));
    }

    @PutMapping("/update")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Event> update=eventService.update(event, userDetails.getUsername());
        if(update.isPresent()){
            return ResponseEntity.ok(update.get());
        }
        throw new IllegalArgumentException("Update error");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Event> deleteEvent(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Event> delete=eventService.delete(id, userDetails.getUsername());
        if (delete.isPresent()){
            return ResponseEntity.ok(delete.get());
        }
        throw new IllegalArgumentException("Event not found");
    }

    @Transactional
    @PostMapping("/saveAll")
    public ResponseEntity<List<Event>> saveAll(@RequestBody List<Event> events, @AuthenticationPrincipal UserDetails userDetails) {
        //jeżeli IllegalArgumentExcetion będzie obsłużone w CustomExceptionHandler
        List<Event> savedEvents=eventService.saveAll(events, userDetails.getUsername());
        return ResponseEntity.ok(savedEvents);

    }

    @Transactional
    @DeleteMapping("/deleteAll")
    public ResponseEntity<List<Event>> deleteAll(@AuthenticationPrincipal UserDetails userDetails) {
        List<Event> events=eventService.deleteAll(userDetails.getUsername());
        return ResponseEntity.ok(events);
    }
}
