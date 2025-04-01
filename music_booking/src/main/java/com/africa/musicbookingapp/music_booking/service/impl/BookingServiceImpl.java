package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.domain.model.Artist;
import com.africa.musicbookingapp.music_booking.domain.model.Booking;
import com.africa.musicbookingapp.music_booking.domain.model.Event;
import com.africa.musicbookingapp.music_booking.dto.request.BookingDto;
import com.africa.musicbookingapp.music_booking.dto.response.BookingResponseDto;
import com.africa.musicbookingapp.music_booking.exception.ResourceNotFoundException;
import com.africa.musicbookingapp.music_booking.mapper.MusicBookingMapper;
import com.africa.musicbookingapp.music_booking.service.IBookingService;
import com.africa.musicbookingapp.music_booking.service.RepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.africa.musicbookingapp.music_booking.domain.enums.TransactionStatus.CANCELLED;
import static com.africa.musicbookingapp.music_booking.domain.enums.TransactionStatus.CONFIRMED;

@Service
public class BookingServiceImpl implements IBookingService {
    private final RepositoryManager repositoryManager;
    private final ServiceManager serviceManager;
    private final MusicBookingMapper mapper;


    @Autowired
    public BookingServiceImpl(@Lazy RepositoryManager repositoryManager, ServiceManager serviceManager, MusicBookingMapper mapper) {
        this.repositoryManager = repositoryManager;
        this.serviceManager = serviceManager;
        this.mapper = mapper;
    }
    @Override
    public List<BookingResponseDto> getAllBookings() {
        List<Booking> bookings = repositoryManager.getBookingRepository().findAll();
        return bookings.stream()
                .map(mapper::toBookingResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponseDto  createBooking(BookingDto bookingDto) {
        Artist artist = repositoryManager.getArtistRepository()
                .findById(bookingDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Artist ID: " + bookingDto.getArtistId()));

        Event event = repositoryManager.getEventRepository()
                .findById(bookingDto.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Event ID: " + bookingDto.getEventId()));

        Booking booking = mapper.toBooking(bookingDto);
        boolean paymentSuccessful = serviceManager.getTransactionService().processPayment(bookingDto.getAmount(), bookingDto.getPaymentMethod());
        if (!paymentSuccessful) {
            throw new RuntimeException("Payment processing failed for booking.");
        }
        booking.setTransactionStatus(CONFIRMED);

        Booking savedBooking = repositoryManager.getBookingRepository().save(booking);
        return mapper.toBookingResponseDto(savedBooking);
    }
    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = repositoryManager.getBookingRepository()
                .findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        if (CANCELLED.equals(booking.getTransactionStatus())) {
            throw new RuntimeException("Booking is already cancelled.");
        }
        boolean refundProcessed = serviceManager.getTransactionService().processRefund(booking.getAmount());
        if (!refundProcessed) {
            throw new RuntimeException("Refund processing failed.");
        }
        booking.setTransactionStatus(CANCELLED);
        repositoryManager.getBookingRepository().save(booking);
    }

}
