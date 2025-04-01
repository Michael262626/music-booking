package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.service.IArtistService;
import com.africa.musicbookingapp.music_booking.service.IBookingService;
import com.africa.musicbookingapp.music_booking.service.IEventService;
import com.africa.musicbookingapp.music_booking.service.ITransactionService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ServiceManager {

    private final IArtistService artistService;
    private final IEventService eventService;
    private final IBookingService bookingService;
    private final ITransactionService transactionService;

    @Autowired
    public ServiceManager(@Lazy IArtistService artistService,
                          @Lazy IEventService eventService,
                          @Lazy IBookingService bookingService, ITransactionService transactionService) {
        this.artistService = artistService;
        this.eventService = eventService;
        this.bookingService = bookingService;
        this.transactionService = transactionService;
    }
}
