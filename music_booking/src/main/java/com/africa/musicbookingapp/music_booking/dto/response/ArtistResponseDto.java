package com.africa.musicbookingapp.music_booking.dto.response;

import lombok.Data;

@Data
public class ArtistResponseDto {
    private Long id;
    private String name;
    private String genre;
    private String contactInfo;
}