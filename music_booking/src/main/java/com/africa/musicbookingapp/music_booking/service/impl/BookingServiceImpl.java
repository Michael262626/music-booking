package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.enums.TransactionStatus;
import com.africa.musicbookingapp.music_booking.entities.model.Artist;
import com.africa.musicbookingapp.music_booking.entities.model.Booking;
import com.africa.musicbookingapp.music_booking.entities.model.Event;
import com.africa.musicbookingapp.music_booking.entities.model.Transaction;
import com.africa.musicbookingapp.music_booking.dto.request.BookingDto;
import com.africa.musicbookingapp.music_booking.dto.request.PaystackPaymentRequestDto;
import com.africa.musicbookingapp.music_booking.dto.response.BookingResponseDto;
import com.africa.musicbookingapp.music_booking.exception.BookingException;
import com.africa.musicbookingapp.music_booking.exception.ResourceNotFoundException;
import com.africa.musicbookingapp.music_booking.mapper.MusicBookingMapper;
import com.africa.musicbookingapp.music_booking.service.IBookingService;
import com.africa.musicbookingapp.music_booking.entities.RepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static com.africa.musicbookingapp.music_booking.enums.TransactionStatus.SUCCESS;

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
        public BookingResponseDto createBooking(BookingDto bookingDto) {
            GetEventAndArtist response = getGetEventAndArtist(bookingDto);

            Booking booking = getBooking(bookingDto, response);
            Transaction transaction = getTransaction(bookingDto);
            transaction.setDateCreated((LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))));
            repositoryManager.getTransactionRepository().save(transaction);

            PaystackPaymentRequestDto paymentRequest = getPaystackPaymentRequestDto(bookingDto, booking, transaction);

            var authorizationUrl = serviceManager.getTransactionService().processPayment(paymentRequest);

            if (authorizationUrl == null) {
                throw new BookingException("Payment processing failed for booking.");
            }
            booking.setTransactionStatus(SUCCESS);
            Booking savedBooking = repositoryManager.getBookingRepository().save(booking);
            savedBooking.setBookingDate((LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))));
            return mapper.toBookingResponseDto(savedBooking);
        }

    private static PaystackPaymentRequestDto getPaystackPaymentRequestDto(BookingDto bookingDto, Booking booking, Transaction transaction) {
        booking.setTransaction(transaction);
        PaystackPaymentRequestDto paymentRequest = new PaystackPaymentRequestDto();
        paymentRequest.setAmount(bookingDto.getAmount());
        paymentRequest.setEmail(bookingDto.getEmail());
        return paymentRequest;
    }

    private Booking getBooking(BookingDto bookingDto, GetEventAndArtist response) {
        Booking booking = mapper.toBooking(bookingDto);
        booking.setArtist(response.artist());
        booking.setEvent(response.event());
        booking.setBookingDate((LocalDateTime.parse(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME))));
        return booking;
    }

    private GetEventAndArtist getGetEventAndArtist(BookingDto bookingDto) {
        validate(bookingDto);
        var artist = repositoryManager.getArtistRepository()
                .findById(bookingDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist with ID " + bookingDto.getArtistId() + " not found."));

        // Fetch Event
        var event = repositoryManager.getEventRepository()
                .findById(bookingDto.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event with ID " + bookingDto.getEventId() + " not found."));
        GetEventAndArtist response = new GetEventAndArtist(artist, event);
        return response;
    }

    private record GetEventAndArtist(Artist artist, Event event) {
    }

    private static Transaction getTransaction(BookingDto bookingDto) {
            Transaction transaction = new Transaction();
            transaction.setStatus(TransactionStatus.PENDING);
            transaction.setAmount(bookingDto.getAmount());
            transaction.setEmail(bookingDto.getEmail());
            return transaction;
        }

        private void validate(BookingDto bookingDto) {
            Artist artist = repositoryManager.getArtistRepository()
                    .findById(bookingDto.getArtistId())
                    .orElseThrow(() -> new BookingException("Invalid Artist ID: " + bookingDto.getArtistId()));

            Event event = repositoryManager.getEventRepository()
                    .findById(bookingDto.getEventId())
                    .orElseThrow(() -> new ResourceNotFoundException("Invalid Event ID: " + bookingDto.getEventId()));
        }


        @Override
        public BookingResponseDto getBookingById(long id) {
            Booking booking = repositoryManager.getBookingRepository()
                    .findById(id)
                    .orElseThrow(() -> new BookingException("Booking not found with id: " + id));

            return mapper.toBookingResponseDto(booking);
        }
    }
