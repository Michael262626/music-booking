package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.service.ITransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements ITransactionService {

    @Override
    public boolean processPayment(double amount, String paymentMethod) {
        // Simulate payment processing (integrate with Paystack, Stripe, or Flutterwave)
        System.out.println("Processing payment of $" + amount + " using " + paymentMethod);

        // Simulate successful payment
        return true;
    }
    @Override
    public boolean processRefund(double amount) {
        System.out.println("Processing refund of $" + amount);
        return true;
    }
}
