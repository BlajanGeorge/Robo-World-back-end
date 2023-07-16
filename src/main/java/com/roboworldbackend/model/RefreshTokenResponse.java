package com.roboworldbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Refresh token response
 *
 * @author Blajan George
 */
public record RefreshTokenResponse(
        @JsonProperty("token")
        String token
) {
}
