package com.africa.musicbookingapp.music_booking.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI musicBookingOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Music Booking API")
                        .version("1.0")
                        .description("API for managing artists, events, and bookings"));
    }
}
