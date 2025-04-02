package com.africa.musicbookingapp.music_booking.controller;

import com.africa.musicbookingapp.music_booking.dto.request.BookingDto;
import com.africa.musicbookingapp.music_booking.dto.response.BookingResponseDto;
import com.africa.musicbookingapp.music_booking.service.IInterfaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {
    @Autowired
    private IInterfaceManager iInterfaceManager;

    @PostMapping("/createBooking")
    public ResponseEntity<?> createBooking(@RequestBody BookingDto bookingDto) {
        var response =  iInterfaceManager.getBookingService().createBooking(bookingDto);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/getAllBooking")
    public ResponseEntity<List<?>> getAllBookings() {
        var response = iInterfaceManager.getBookingService().getAllBookings();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getBookById{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable Long bookingId) {
        var response = iInterfaceManager.getBookingService().getBookingById(bookingId);
        return ResponseEntity.ok().body(response);
    }
}
