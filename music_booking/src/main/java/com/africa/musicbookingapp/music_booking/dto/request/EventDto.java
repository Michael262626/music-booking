package com.africa.musicbookingapp.music_booking.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDto {
    private String name;
    private String location;
    private LocalDateTime eventDate;
}
