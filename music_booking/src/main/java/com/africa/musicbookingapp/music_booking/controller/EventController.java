package com.africa.musicbookingapp.music_booking.controller;

import com.africa.musicbookingapp.music_booking.dto.request.EventDto;
import com.africa.musicbookingapp.music_booking.dto.response.EventResponseDto;
import com.africa.musicbookingapp.music_booking.service.IInterfaceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    @Autowired
    private IInterfaceManager iInterfaceManager;

    @PostMapping("/create")
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventDto eventDto) {
        var response = iInterfaceManager.getEventService().createEvent(eventDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        var response = iInterfaceManager.getEventService().getAllEvents();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<EventResponseDto>> getPaginatedEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var response = iInterfaceManager.getEventService().getAllEvents(page, size);
        return ResponseEntity.ok(response);
    }
}
