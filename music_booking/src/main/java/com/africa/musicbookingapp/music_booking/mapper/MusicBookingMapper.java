package com.africa.musicbookingapp.music_booking.mapper;

import com.africa.musicbookingapp.music_booking.entities.model.*;
import com.africa.musicbookingapp.music_booking.dto.request.ArtistDto;
import com.africa.musicbookingapp.music_booking.dto.request.BookingDto;
import com.africa.musicbookingapp.music_booking.dto.request.EventDto;
import com.africa.musicbookingapp.music_booking.dto.request.PaystackPaymentRequestDto;
import com.africa.musicbookingapp.music_booking.dto.response.ArtistResponseDto;
import com.africa.musicbookingapp.music_booking.dto.response.BookingResponseDto;
import com.africa.musicbookingapp.music_booking.dto.response.EventResponseDto;
import com.africa.musicbookingapp.music_booking.dto.response.PaystackPaymentResponseDto;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MusicBookingMapper {
    ArtistResponseDto toArtistResponseDto(Artist artist);

    Event toEvent(EventDto dto);

    EventResponseDto toEventResponseDto(Event event);

    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "transaction", ignore = true)
    Booking toBooking(BookingDto dto);

    BookingResponseDto toBookingResponseDto(Booking booking);

    Transaction toTransaction(PaystackPaymentRequestDto dto);
    PaystackPaymentResponseDto toTransactionResponseDto(Transaction transaction);

    @Mapping(target = "firstname", source = "newUserRecord.firstName")
    @Mapping(target = "lastname", source = "newUserRecord.lastName")
    @Mapping(target = "username", source = "userRepresentation.username")  // Explicit mapping
    @Mapping(target = "email", source = "userRepresentation.email")
    Artist toArtist(NewUserRecord newUserRecord, UserRepresentation userRepresentation);}