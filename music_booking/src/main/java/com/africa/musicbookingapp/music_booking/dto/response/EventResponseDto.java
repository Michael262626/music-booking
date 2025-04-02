package com.africa.musicbookingapp.music_booking.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventResponseDto {
    private Long id;
    private Long artistId;
    private String name;
    private String location;
    private LocalDateTime eventDate;
}
