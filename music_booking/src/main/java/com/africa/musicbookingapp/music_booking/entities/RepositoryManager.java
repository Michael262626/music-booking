package com.africa.musicbookingapp.music_booking.entities;

import com.africa.musicbookingapp.music_booking.entities.repository.ArtistRepository;
import com.africa.musicbookingapp.music_booking.entities.repository.BookingRepository;
import com.africa.musicbookingapp.music_booking.entities.repository.EventRepository;
import com.africa.musicbookingapp.music_booking.entities.repository.TransactionRepository;
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
        private final TransactionRepository transactionRepository;

        @Autowired
        public RepositoryManager(@Lazy ArtistRepository artistRepository,
                                 @Lazy EventRepository eventRepository,
                                 @Lazy BookingRepository bookingRepository, TransactionRepository transactionRepository) {
            this.artistRepository = artistRepository;
            this.eventRepository = eventRepository;
            this.bookingRepository = bookingRepository;
            this.transactionRepository = transactionRepository;
        }
}
