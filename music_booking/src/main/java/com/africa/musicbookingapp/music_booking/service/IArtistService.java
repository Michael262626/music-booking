package com.africa.musicbookingapp.music_booking.service;

import com.africa.musicbookingapp.music_booking.domain.model.Artist;
import com.africa.musicbookingapp.music_booking.dto.request.ArtistDto;
import com.africa.musicbookingapp.music_booking.dto.response.ArtistResponseDto;

import java.util.List;

public interface IArtistService {
    List<ArtistResponseDto> getAllArtists();
    ArtistResponseDto createArtist(ArtistDto dto);
    ArtistResponseDto updateArtist(Long id, ArtistDto dto);
    void deleteArtist(Long id);
    ArtistResponseDto getArtistById(Long id);
}
