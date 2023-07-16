package com.roboworldbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

/**
 * Refresh token request
 *
 * @author Blajan George
 */
public record RefreshTokenRequest(
        @NotBlank(message = "Token must not be blank.")
        @JsonProperty("token")
        String token,
        @NotBlank(message = "Refresh token must not be blank.")
        @JsonProperty("refresh_token")
        String refreshToken,
        @NotBlank(message = "Email must not be blank.")
        @JsonProperty("email")
        String email
) {
}

