package com.africa.musicbookingapp.music_booking.configuration.securityConfig;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JWTAuthConverter jwtAuthConverter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                        HttpMethod.POST, "/public/**", "/api/v1/users/**", "api/v1/booking/**", "/api/v1/event/**")
                .requestMatchers(HttpMethod.GET, "/public/**", "api/v1/users/**", "/api/v1/event/**", "/api/v1/booking/**")
                .requestMatchers(HttpMethod.PUT, "/public/**", "api/v1/users/{id}/send-verification-email", "/users/forgot-password", "/api/v1/users/**")
                .requestMatchers(HttpMethod.DELETE, "/public/**", "api/v1/users/delete/{id}")
                .requestMatchers(HttpMethod.OPTIONS, "/**")
                .requestMatchers("/v3/api-docs/**", "/configuration/**", "/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/api-docs/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**", "/api/v1/users/**").permitAll() // Publicly accessible routes
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(jwtAuthConverter)
                        )
                )
                // Stateless session management (necessary for JWT-based authentication)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}