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
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-01T04:09:17+0100",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
)
@Component
public class MusicBookingMapperImpl implements MusicBookingMapper {

    @Override
    public Artist toArtist(ArtistDto dto) {
        if ( dto == null ) {
            return null;
        }

        Artist artist = new Artist();

        artist.setName( dto.getName() );
        artist.setGenre( dto.getGenre() );
        artist.setContactInfo( dto.getContactInfo() );

        return artist;
    }

    @Override
    public ArtistResponseDto toArtistResponseDto(Artist artist) {
        if ( artist == null ) {
            return null;
        }

        ArtistResponseDto artistResponseDto = new ArtistResponseDto();

        artistResponseDto.setId( artist.getId() );
        artistResponseDto.setName( artist.getName() );
        artistResponseDto.setGenre( artist.getGenre() );
        artistResponseDto.setContactInfo( artist.getContactInfo() );

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
        event.setEventDate( dto.getEventDate() );

        return event;
    }

    @Override
    public EventResponseDto toEventResponseDto(Event event) {
        if ( event == null ) {
            return null;
        }

        EventResponseDto eventResponseDto = new EventResponseDto();

        return eventResponseDto;
    }

    @Override
    public Booking toBooking(BookingDto dto) {
        if ( dto == null ) {
            return null;
        }

        Booking booking = new Booking();

        return booking;
    }

    @Override
    public BookingResponseDto toBookingResponseDto(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        BookingResponseDto bookingResponseDto = new BookingResponseDto();

        return bookingResponseDto;
    }
}
