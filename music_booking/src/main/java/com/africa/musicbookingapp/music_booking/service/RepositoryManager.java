package com.africa.musicbookingapp.music_booking.service;

import com.africa.musicbookingapp.music_booking.repository.ArtistRepository;
import com.africa.musicbookingapp.music_booking.repository.BookingRepository;
import com.africa.musicbookingapp.music_booking.repository.EventRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RepositoryManager {
        private final ArtistRepository artistRepository;
        private final EventRepository eventRepository;
        private final BookingRepository bookingRepository;

        @Autowired
        public RepositoryManager(@Lazy ArtistRepository artistRepository,
                                 @Lazy EventRepository eventRepository,
                                 @Lazy BookingRepository bookingRepository) {
            this.artistRepository = artistRepository;
            this.eventRepository = eventRepository;
            this.bookingRepository = bookingRepository;
        }
}
