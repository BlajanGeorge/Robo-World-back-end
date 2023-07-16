package com.roboworldbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for Login request
 *
 * @author Blajan George
 */
public record LoginRequest(
        @JsonProperty("email")
        String email,
        @JsonProperty("password")
        String password
) {
}
