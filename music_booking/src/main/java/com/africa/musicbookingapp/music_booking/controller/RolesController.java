package com.africa.musicbookingapp.music_booking.controller;

import com.africa.musicbookingapp.music_booking.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RolesController {
    private final RoleService roleResource;

    @PutMapping("/assign/user/{userId}")
    public ResponseEntity<?> sendVerificationEmail(@PathVariable Long userId, @RequestParam String rolenName) {
        roleResource.assignRole(userId,rolenName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
