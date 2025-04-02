package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.entities.model.Artist;
import com.africa.musicbookingapp.music_booking.entities.model.Event;
import com.africa.musicbookingapp.music_booking.dto.request.BookingDto;
import com.africa.musicbookingapp.music_booking.exception.BookingException;
import com.africa.musicbookingapp.music_booking.entities.RepositoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static com.africa.musicbookingapp.music_booking.enums.TransactionStatus.SUCCESS;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookingServiceImplTest {

    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private RepositoryManager repositoryManager;

    private Event testEvent;

    @BeforeEach
    public void setUp() {
        // Create and save test Artist
        Artist testArtist = new Artist();
        testArtist.setUsername("Test Artist");
        testArtist.setGenre("Pop");
        testArtist.setContactInfo("dikandumichael@gmail.com");

        testArtist = repositoryManager.getArtistRepository().save(testArtist);

        // Create and save test Event with the Artist set
        testEvent = new Event();
        testEvent.setName("Test Event");
        testEvent.setLocation("Test Venue");
        testEvent.setEventDate(java.time.LocalDateTime.now().plusDays(1));

        testEvent.setArtist(testArtist);

        testEvent = repositoryManager.getEventRepository().save(testEvent);
    }


    @Test
    void testCreateBookingFailure_InvalidArtistId() {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setArtistId(999L); // Invalid artist ID (not present in the database)
        bookingDto.setEventId(testEvent.getId());
        bookingDto.setAmount(100.0);

        // Test that booking creation throws an exception for invalid artist ID
        assertThrows(BookingException.class, () -> serviceManager.getBookingService().createBooking(bookingDto));
    }

    @Test
    void testCreateBookingSuccess() {
        // Create a valid BookingDto with appropriate data
        BookingDto bookingDto = new BookingDto();
        bookingDto.setArtistId(testEvent.getArtist().getId());
        bookingDto.setEventId(testEvent.getId());
        bookingDto.setEmail("dikandumichael@gmail.com");
        bookingDto.setAmount(200.0);

        var createdBooking = serviceManager.getBookingService().createBooking(bookingDto);

        assertNotNull(createdBooking);
        assertEquals(200.0, createdBooking.getAmount());
        assertEquals(SUCCESS, createdBooking.getTransactionStatus());
    }

    @Test
    void testToGetAllBookings() {
        var booking = serviceManager.getBookingService().getAllBookings();
        assertNotNull(booking);
    }

}
