package com.example.ewidencja.repository;

import com.example.ewidencja.model.Event;
import com.example.ewidencja.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> findByDate(LocalDate date);
    Optional<Event> findByAccoutingNumber(String accoutingNumber);
    Optional<List<Event>> findAllByDate(LocalDate date);
    Optional<List<Event>> findAllByDateBefore(LocalDate date);
    Optional<List<Event>> findAllByDateAfter(LocalDate date);
    Optional<List<Event>> findByUser(User user);
    List<Event> deleteAllByUser(User user);
}
