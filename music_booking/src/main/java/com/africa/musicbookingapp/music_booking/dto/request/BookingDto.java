package com.africa.musicbookingapp.music_booking.dto.request;

import lombok.Data;

@Data
public class BookingDto {
    private Long artistId;
    private Long eventId;
    private String paymentMethod;
    private double amount;
}
