package com.example.ewidencja.controller;

import com.example.ewidencja.model.Event;
import com.example.ewidencja.service.interfaces.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private EventService eventService;

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
}
