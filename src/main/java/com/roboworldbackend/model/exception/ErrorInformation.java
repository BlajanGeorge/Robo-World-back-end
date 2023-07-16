package com.roboworldbackend.model.exception;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error information dto
 *
 * @author Blajan George
 */
public record ErrorInformation(
        @JsonProperty("message")
        String message,
        @JsonProperty("type")
        String type
) {
}
