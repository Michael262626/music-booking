package com.africa.musicbookingapp.music_booking.service;

public interface ITransactionService {
    boolean processPayment(double amount, String paymentMethod);
    boolean processRefund(double amount);
}
