package com.africa.musicbookingapp.music_booking.dto.response;

import com.africa.musicbookingapp.music_booking.enums.TransactionStatus;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class PaystackPaymentResponseDto {
    private TransactionStatus status;
    private String message;
    private String reference;
    private double amount;
    private String authorizationUrl;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using= LocalDateTimeSerializer.class)
    private LocalDateTime dateCreated;
}
