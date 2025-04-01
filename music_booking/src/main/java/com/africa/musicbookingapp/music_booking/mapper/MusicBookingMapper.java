package com.africa.musicbookingapp.music_booking.mapper;

import com.africa.musicbookingapp.music_booking.domain.model.Artist;
import com.africa.musicbookingapp.music_booking.domain.model.Booking;
import com.africa.musicbookingapp.music_booking.domain.model.Event;
import com.africa.musicbookingapp.music_booking.dto.request.ArtistDto;
import com.africa.musicbookingapp.music_booking.dto.request.BookingDto;
import com.africa.musicbookingapp.music_booking.dto.request.EventDto;
import com.africa.musicbookingapp.music_booking.dto.response.ArtistResponseDto;
import com.africa.musicbookingapp.music_booking.dto.response.BookingResponseDto;
import com.africa.musicbookingapp.music_booking.dto.response.EventResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MusicBookingMapper {
    Artist toArtist(ArtistDto dto);

    ArtistResponseDto toArtistResponseDto(Artist artist);

    Event toEvent(EventDto dto);

    EventResponseDto toEventResponseDto(Event event);

    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "event", ignore = true)
    @Mapping(target = "paymentMethod", source = "bookingDto.paymentMethod")
    Booking toBooking(BookingDto dto);

    BookingResponseDto toBookingResponseDto(Booking booking);
}