package com.africa.musicbookingapp.music_booking.repository;

import com.africa.musicbookingapp.music_booking.domain.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> { }
