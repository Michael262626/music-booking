package com.africa.musicbookingapp.music_booking.entities.model;

import com.africa.musicbookingapp.music_booking.enums.TransactionStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private String email;

    private double amount;

    private TransactionStatus status;

    @Column(name = "date_created", nullable = false, updatable = false)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;  // Linking the transaction to a booking

    @PrePersist
    public void prePersist() {
        if (dateCreated == null) {
            dateCreated = LocalDateTime.now();
        }
    }
}