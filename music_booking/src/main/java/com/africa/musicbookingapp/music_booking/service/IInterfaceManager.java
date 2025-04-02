package com.africa.musicbookingapp.music_booking.service;

public interface IInterfaceManager {
    IBookingService getBookingService();
    IArtistService getArtistService();
    IEventService getEventService();
    ITransactionService getTransactionService();
}
