package com.africa.musicbookingapp.music_booking.service;

import com.africa.musicbookingapp.music_booking.dto.request.PaystackPaymentRequestDto;
import com.africa.musicbookingapp.music_booking.dto.response.PaystackPaymentResponseDto;

public interface ITransactionService {
    PaystackPaymentResponseDto processPayment(PaystackPaymentRequestDto paymentRequest);
}
