package com.africa.musicbookingapp.music_booking.entities.repository;

import com.africa.musicbookingapp.music_booking.entities.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
