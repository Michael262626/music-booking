package com.africa.musicbookingapp.music_booking.entities.model;

import com.africa.musicbookingapp.music_booking.enums.TransactionStatus;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Artist artist;

    private double amount;
    private String email;

    @ManyToOne
    private Transaction transaction;

    private TransactionStatus transactionStatus;

    @ManyToOne
    private Event event;

    private String paymentReference;

    @Column(name = "booking_date", nullable = false, updatable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    private LocalDateTime bookingDate;

    @PrePersist
    public void prePersist() {
        if (bookingDate == null) {
            bookingDate = LocalDateTime.now();
        }
    }
}