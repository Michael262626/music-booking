package com.africa.musicbookingapp.music_booking.entities.repository;

import com.africa.musicbookingapp.music_booking.entities.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByName(String name);
}


