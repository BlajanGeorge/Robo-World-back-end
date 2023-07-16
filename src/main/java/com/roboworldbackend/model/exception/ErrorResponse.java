package com.roboworldbackend.model.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error response dto
 *
 * @author Blajan George
 */
public record ErrorResponse(
        @JsonProperty("error")
        ErrorInformation error
) {
}
