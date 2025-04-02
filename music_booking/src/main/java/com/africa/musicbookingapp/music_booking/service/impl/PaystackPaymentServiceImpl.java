package com.africa.musicbookingapp.music_booking.service.impl;

import com.africa.musicbookingapp.music_booking.configuration.PaystackConfig;
import com.africa.musicbookingapp.music_booking.dto.request.PaystackPaymentRequestDto;
import com.africa.musicbookingapp.music_booking.dto.response.PaystackPaymentResponseDto;
import com.africa.musicbookingapp.music_booking.service.ITransactionService;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;

import static com.africa.musicbookingapp.music_booking.enums.TransactionStatus.*;

@Service
@Slf4j
public class PaystackPaymentServiceImpl implements ITransactionService {

    private final String PAYSTACK_SECRET_KEY;
    private final String INTIALIZED_URL;

    public PaystackPaymentServiceImpl(PaystackConfig paystackConfig) {
        this.PAYSTACK_SECRET_KEY = paystackConfig.getSecretKey();
        this.INTIALIZED_URL = paystackConfig.getInitializeurl();
    }

    public PaystackPaymentResponseDto processPayment(PaystackPaymentRequestDto paymentRequest) {
        PaystackPaymentResponseDto responseDto = new PaystackPaymentResponseDto();
        try {

            HttpEntity<String> entity = getStringHttpEntity(paymentRequest);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<JsonNode> response = restTemplate.exchange(INTIALIZED_URL, HttpMethod.POST, entity, JsonNode.class);

            // Log the response
            if (response.getStatusCode().is2xxSuccessful()) {
                JsonNode data = response.getBody().get("data");
                String authorizationUrl = data.get("authorization_url").asText();
                responseDto.setAuthorizationUrl(authorizationUrl);
                responseDto.setStatus(SUCCESS);
                log.info("Payment initialized successfully. Authorization URL: {}", authorizationUrl);
            } else {
                responseDto.setStatus(FAILED);
                log.error("Failed to initialize payment. Response: {}", response.getBody());
            }

        } catch (Exception e) {
            responseDto.setStatus(ERROR);
            log.error("Payment processing failed. Amount: {}, Email: {}, Error: {}", paymentRequest.getAmount(), paymentRequest.getEmail(), e.getMessage(), e);
        }
        return responseDto;
    }

    private HttpEntity<String> getStringHttpEntity(PaystackPaymentRequestDto paymentRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + PAYSTACK_SECRET_KEY);

        // Set the payment details
        String jsonBody = String.format("{\"amount\": %d, \"email\": \"%s\", \"currency\": \"NGN\"}",
                (long) (paymentRequest.getAmount() * 100), paymentRequest.getEmail()); // Paystack requires amount in kobo (1 NGN = 100 kobo)

        // Make the request to initialize payment
        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
        return entity;
    }
}
