package com.africa.musicbookingapp.music_booking.entities.repository;

import com.africa.musicbookingapp.music_booking.entities.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> { }
