package com.africa.musicbookingapp.music_booking.repository;

import com.africa.musicbookingapp.music_booking.domain.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Object> findByName(String name);
}


