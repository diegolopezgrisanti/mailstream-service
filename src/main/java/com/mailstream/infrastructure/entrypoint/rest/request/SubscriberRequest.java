package com.mailstream.infrastructure.entrypoint.rest.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a request to register a new subscriber.")
public class SubscriberRequest {

    @Schema(description = "The email address of the subscriber.", example = "example@mail.com")
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    private String email;
}