package com.africa.musicbookingapp.music_booking.service;

import com.africa.musicbookingapp.music_booking.dto.request.EventDto;
import com.africa.musicbookingapp.music_booking.dto.response.EventResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IEventService {
    List<EventResponseDto> getAllEvents() ;
    EventResponseDto  createEvent(EventDto eventDto);
    Page<EventResponseDto> getAllEvents(int page, int size);
}
