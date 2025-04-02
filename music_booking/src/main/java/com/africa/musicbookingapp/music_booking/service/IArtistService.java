package com.africa.musicbookingapp.music_booking.service;

import com.africa.musicbookingapp.music_booking.dto.request.ArtistDto;
import com.africa.musicbookingapp.music_booking.dto.request.LoginRequest;
import com.africa.musicbookingapp.music_booking.dto.response.ArtistResponseDto;
import com.africa.musicbookingapp.music_booking.dto.response.LoginResponse;
import com.africa.musicbookingapp.music_booking.entities.model.NewUserRecord;
import org.keycloak.admin.client.resource.UserResource;

import java.util.List;

public interface IArtistService {
    List<ArtistResponseDto> getAllArtists();
    LoginResponse login (String username, String password);
    ArtistResponseDto createArtist(NewUserRecord newUserRecord);
    void forgetPassword(LoginRequest loginRequest);
    void updateArtist(Long id, ArtistDto updatedUser);
    void verificationEmail(String userId);
    void deleteArtist(Long id);
    UserResource getUser(Long userId);
    ArtistResponseDto getArtistById(Long id);
}
