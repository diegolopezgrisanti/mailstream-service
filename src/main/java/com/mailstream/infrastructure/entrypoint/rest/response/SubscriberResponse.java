package com.mailstream.infrastructure.entrypoint.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriberResponse {

    @Schema(
            description = "A message indicating the success of the operation.",
            example = "The email has been successfully registered."
    )
    private String message;

    @Schema(
            description = "The email address of the subscriber.",
            example = "example@mail.com"
    )
    private String email;

    @Schema(
            description = "The date and time when the subscription was registered.",
            example = "2024-12-21T12:34:56"
    )
    private LocalDateTime subscriptionDate;
}