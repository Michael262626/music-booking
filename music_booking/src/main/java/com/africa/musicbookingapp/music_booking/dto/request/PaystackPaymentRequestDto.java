package com.africa.musicbookingapp.music_booking.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaystackPaymentRequestDto {
    private String email;
    private double amount;
    private String currency;
    private String paymentMethod;
}
