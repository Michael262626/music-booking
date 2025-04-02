package com.africa.musicbookingapp.music_booking.controller;

import com.africa.musicbookingapp.music_booking.dto.request.ArtistDto;
import com.africa.musicbookingapp.music_booking.dto.request.LoginRequest;
import com.africa.musicbookingapp.music_booking.dto.response.ArtistResponseDto;
import com.africa.musicbookingapp.music_booking.dto.response.LoginResponse;
import com.africa.musicbookingapp.music_booking.entities.model.NewUserRecord;
import com.africa.musicbookingapp.music_booking.service.IInterfaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class ArtistController {

    @Autowired
    private IInterfaceManager iInterfaceManager;

    @PostMapping("/register")
    public ResponseEntity<?> createArtist(@RequestBody NewUserRecord newUserRecord) {
        var response = iInterfaceManager.getArtistService().createArtist(newUserRecord);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody String username, String password) {
        LoginResponse response  = iInterfaceManager.getArtistService().login(username, password);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}/send-verification-email")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable Long id) {
        iInterfaceManager.getArtistService().verificationEmail(String.valueOf(id));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArtistResponseDto>> getAllArtists() {
        var response = iInterfaceManager.getArtistService().getAllArtists();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtistResponseDto> getArtistById(@PathVariable Long id) {
        var response = iInterfaceManager.getArtistService().getArtistById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArtist(@PathVariable Long id, @RequestBody ArtistDto artistDto) {
        iInterfaceManager.getArtistService().updateArtist(id, artistDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArtist(@PathVariable Long id) {
        iInterfaceManager.getArtistService().deleteArtist(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/reset-password")
    public ResponseEntity<?> forgotPassword(@RequestBody LoginRequest loginRequest) {
        iInterfaceManager.getArtistService().forgetPassword(loginRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

