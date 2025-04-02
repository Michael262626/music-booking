package com.africa.musicbookingapp.music_booking.mapper;

import com.africa.musicbookingapp.music_booking.dto.request.BookingDto;
import com.africa.musicbookingapp.music_booking.dto.request.EventDto;
import com.africa.musicbookingapp.music_booking.dto.request.PaystackPaymentRequestDto;
import com.africa.musicbookingapp.music_booking.dto.response.ArtistResponseDto;
import com.africa.musicbookingapp.music_booking.dto.response.BookingResponseDto;
import com.africa.musicbookingapp.music_booking.dto.response.EventResponseDto;
import com.africa.musicbookingapp.music_booking.dto.response.PaystackPaymentResponseDto;
import com.africa.musicbookingapp.music_booking.entities.model.Artist;
import com.africa.musicbookingapp.music_booking.entities.model.Booking;
import com.africa.musicbookingapp.music_booking.entities.model.Event;
import com.africa.musicbookingapp.music_booking.entities.model.NewUserRecord;
import com.africa.musicbookingapp.music_booking.entities.model.Transaction;
import javax.annotation.processing.Generated;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-02T17:24:12+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class MusicBookingMapperImpl implements MusicBookingMapper {

    @Override
    public ArtistResponseDto toArtistResponseDto(Artist artist) {
        if ( artist == null ) {
            return null;
        }

        ArtistResponseDto artistResponseDto = new ArtistResponseDto();

        artistResponseDto.setId( artist.getId() );
        artistResponseDto.setUsername( artist.getUsername() );
        artistResponseDto.setDateCreated( artist.getDateCreated() );

        return artistResponseDto;
    }

    @Override
    public Event toEvent(EventDto dto) {
        if ( dto == null ) {
            return null;
        }

        Event event = new Event();

        event.setName( dto.getName() );
        event.setLocation( dto.getLocation() );

        return event;
    }

    @Override
    public EventResponseDto toEventResponseDto(Event event) {
        if ( event == null ) {
            return null;
        }

        EventResponseDto eventResponseDto = new EventResponseDto();

        eventResponseDto.setId( event.getId() );
        eventResponseDto.setName( event.getName() );
        eventResponseDto.setLocation( event.getLocation() );
        eventResponseDto.setEventDate( event.getEventDate() );

        return eventResponseDto;
    }

    @Override
    public Booking toBooking(BookingDto dto) {
        if ( dto == null ) {
            return null;
        }

        Booking booking = new Booking();

        booking.setAmount( dto.getAmount() );
        booking.setEmail( dto.getEmail() );

        return booking;
    }

    @Override
    public BookingResponseDto toBookingResponseDto(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingResponseDto bookingResponseDto = new BookingResponseDto();

        bookingResponseDto.setId( booking.getId() );
        bookingResponseDto.setArtist( booking.getArtist() );
        bookingResponseDto.setEvent( booking.getEvent() );
        bookingResponseDto.setAmount( booking.getAmount() );
        bookingResponseDto.setTransactionStatus( booking.getTransactionStatus() );

        return bookingResponseDto;
    }

    @Override
    public Transaction toTransaction(PaystackPaymentRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setEmail( dto.getEmail() );
        transaction.setAmount( dto.getAmount() );

        return transaction;
    }

    @Override
    public PaystackPaymentResponseDto toTransactionResponseDto(Transaction transaction) {
        if ( transaction == null ) {
            return null;
        }

        PaystackPaymentResponseDto paystackPaymentResponseDto = new PaystackPaymentResponseDto();

        paystackPaymentResponseDto.setStatus( transaction.getStatus() );
        paystackPaymentResponseDto.setAmount( transaction.getAmount() );
        paystackPaymentResponseDto.setDateCreated( transaction.getDateCreated() );

        return paystackPaymentResponseDto;
    }

    @Override
    public Artist toArtist(NewUserRecord newUserRecord, UserRepresentation userRepresentation) {
        if ( newUserRecord == null && userRepresentation == null ) {
            return null;
        }

        Artist artist = new Artist();

        if ( newUserRecord != null ) {
            artist.setFirstname( newUserRecord.firstName() );
            artist.setLastname( newUserRecord.lastName() );
            artist.setPassword( newUserRecord.password() );
        }
        if ( userRepresentation != null ) {
            artist.setUsername( userRepresentation.getUsername() );
            artist.setEmail( userRepresentation.getEmail() );
            if ( userRepresentation.getId() != null ) {
                artist.setId( Long.parseLong( userRepresentation.getId() ) );
            }
        }

        return artist;
    }
}
