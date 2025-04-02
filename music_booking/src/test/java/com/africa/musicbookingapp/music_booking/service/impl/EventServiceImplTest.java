package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.entities.model.Event;
import com.africa.musicbookingapp.music_booking.dto.request.EventDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceImplTest {

    @Autowired
    private ServiceManager serviceManager;
    @Test
    void getAllEvents() {

    }

    @Test
    void testGetAllEvents() {

    }

    @Test
    void createEvent() {
        EventDto eventDto = getEventDto();
        var response = serviceManager.getEventService().createEvent(eventDto);
        assertNotNull(response);
        assertEquals(response.getName(), eventDto.getName());
        assertEquals(response.getLocation(), eventDto.getLocation());
    }

    private static EventDto getEventDto() {
        Event event = new Event();
        event.setName("Test Event");
        event.setLocation("Test Location");
        event.setEventDate(LocalDateTime.now());
        EventDto eventDto = new EventDto();
        eventDto.setName(event.getName());
        eventDto.setLocation(event.getLocation());
        return eventDto;
    }
}