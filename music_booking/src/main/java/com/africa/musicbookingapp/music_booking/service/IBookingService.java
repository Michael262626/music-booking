package com.africa.musicbookingapp.music_booking.service;

import com.africa.musicbookingapp.music_booking.dto.request.BookingDto;
import com.africa.musicbookingapp.music_booking.dto.response.BookingResponseDto;

import java.util.List;

public interface IBookingService {
    List<BookingResponseDto> getAllBookings();
    BookingResponseDto createBooking(BookingDto bookingDto);
    BookingResponseDto getBookingById(long id);
}
