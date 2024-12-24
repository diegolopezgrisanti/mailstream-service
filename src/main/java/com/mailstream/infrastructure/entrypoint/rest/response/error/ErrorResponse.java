package com.mailstream.infrastructure.entrypoint.rest.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Represents an error response.")
public class ErrorResponse {

    @Schema(description = "A message indicating the error.", example = "Invalid input")
    private String message;
}