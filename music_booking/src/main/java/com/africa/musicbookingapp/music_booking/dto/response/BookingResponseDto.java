package com.africa.musicbookingapp.music_booking.dto.response;

import com.africa.musicbookingapp.music_booking.domain.model.Artist;
import com.africa.musicbookingapp.music_booking.domain.model.Event;

public class BookingResponseDto {
    private Long id;
    private Artist artist;
    private Event event;
    private double amount;
    private String paymentMethod;
}
