package com.africa.musicbookingapp.music_booking.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EventResponseDto {
    private Long id;
    private String name;
    private String location;
    private LocalDateTime eventDate;
}
