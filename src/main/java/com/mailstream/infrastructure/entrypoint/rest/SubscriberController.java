package com.mailstream.infrastructure.entrypoint.rest;

import com.mailstream.application.registersubscriber.RegisterSubscriberUseCase;
import com.mailstream.domain.subscriber.Subscriber;
import com.mailstream.infrastructure.entrypoint.rest.request.SubscriberRequest;
import com.mailstream.infrastructure.entrypoint.rest.response.SubscriberResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subscribers")
@Tag(name = "Subscribers", description = "Operations related to managing subscribers")
public class SubscriberController {

    private final RegisterSubscriberUseCase registerSubscriberUseCase;

    public SubscriberController(RegisterSubscriberUseCase registerSubscriberUseCase) {
        this.registerSubscriberUseCase = registerSubscriberUseCase;
    }

    @Operation(summary = "Register a new subscriber")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Subscriber registered successfully",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = SubscriberResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Email already registered",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponse.class)
                            )
                    }
            ),
    })
    @PostMapping
    public ResponseEntity<SubscriberResponse> registerSubscriber(@RequestBody @Valid SubscriberRequest request) {
        Subscriber subscriber = registerSubscriberUseCase.registerSubscriber(request.getEmail());

        SubscriberResponse response = new SubscriberResponse(
                "The email has been successfully registered.",
                subscriber.getEmail(),
                subscriber.getSubscriptionDate()
        );

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}