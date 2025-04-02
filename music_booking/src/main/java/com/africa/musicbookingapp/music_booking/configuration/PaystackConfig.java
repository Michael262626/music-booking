package com.africa.musicbookingapp.music_booking.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "paystack")
public class PaystackConfig {
    @Value("${paystack.secret-key}")
    private String secretKey;
    @Value("${paystack.api.initialize-url}")
    private String initializeurl;

}