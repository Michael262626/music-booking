package com.africa.musicbookingapp.music_booking.entities.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    private String firstname;
    private String lastname;
    private String genre;
    private String email;
    private String password;

    private String contactInfo;
    private String role;
    private String keyCloakId;
    @Column(name = "date_created", nullable = false, updatable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    private LocalDateTime dateCreated;

    @PrePersist
    public void prePersist() {
        if (dateCreated == null) {
            dateCreated = LocalDateTime.now();
        }
    }


}
