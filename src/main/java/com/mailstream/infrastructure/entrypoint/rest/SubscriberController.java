package com.mailstream.infrastructure.entrypoint.rest;

import com.mailstream.application.registersubscriber.RegisterSubscriberUseCase;
import com.mailstream.domain.subscriber.Subscriber;
import com.mailstream.infrastructure.entrypoint.rest.request.SubscriberRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    private final RegisterSubscriberUseCase registerSubscriberUseCase;

    public SubscriberController(RegisterSubscriberUseCase registerSubscriberUseCase) {
        this.registerSubscriberUseCase = registerSubscriberUseCase;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> registerSubscriber(@RequestBody @Valid SubscriberRequest request) {
        registerSubscriberUseCase.registerSubscriber(request.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("message", "The email " + request.getEmail() + " has been successfully registered.");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}