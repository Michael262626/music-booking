package com.africa.musicbookingapp.music_booking.entities.repository;

import com.africa.musicbookingapp.music_booking.entities.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Object> findByEmail(String email);
}


