package com.africa.musicbookingapp.music_booking;

import com.africa.musicbookingapp.music_booking.configuration.PaystackConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(PaystackConfig.class)
public class MusicBookingApplication {
	public static void main(String[] args) {
		SpringApplication.run(MusicBookingApplication.class, args);
	}
}
