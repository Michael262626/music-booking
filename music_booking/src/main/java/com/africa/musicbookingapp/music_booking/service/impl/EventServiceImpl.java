package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.domain.model.Event;
import com.africa.musicbookingapp.music_booking.dto.request.EventDto;
import com.africa.musicbookingapp.music_booking.dto.response.EventResponseDto;
import com.africa.musicbookingapp.music_booking.exception.ResourceNotFoundException;
import com.africa.musicbookingapp.music_booking.mapper.MusicBookingMapper;
import com.africa.musicbookingapp.music_booking.service.IEventService;
import com.africa.musicbookingapp.music_booking.service.RepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements IEventService {
    private final RepositoryManager repositoryManager;
    private final MusicBookingMapper mapper;

    @Autowired
    public EventServiceImpl(@Lazy RepositoryManager repositoryManager, MusicBookingMapper mapper) {
        this.repositoryManager = repositoryManager;
        this.mapper = mapper;
    }

    @Override
    public List<EventResponseDto> getAllEvents() {
        return repositoryManager.getEventRepository()
                .findAll()
                .stream()
                .map(mapper::toEventResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EventResponseDto> getAllEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repositoryManager.getEventRepository()
                .findAll(pageable)
                .map(mapper::toEventResponseDto);
    }

    @Override
    public EventResponseDto  createEvent(EventDto eventDto) {
        repositoryManager.getEventRepository()
                .findByName(eventDto.getName())
                .ifPresent(event -> {
                    throw new ResourceNotFoundException("Event with name " + eventDto.getName() + " already exists.");
                });

        Event event = mapper.toEvent(eventDto);
        Event savedEvent = repositoryManager.getEventRepository().save(event);
        return mapper.toEventResponseDto(savedEvent);
    }
}
