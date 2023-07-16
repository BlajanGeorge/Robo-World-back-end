package com.roboworldbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roboworldbackend.db.model.Role;

/**
 * Model class for Login response
 *
 * @author Blajan George
 */
public record LoginResponse(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("role")
        Role role,
        @JsonProperty("token")
        String token,
        @JsonProperty("refresh_token")
        String refreshToken,
        @JsonProperty("selected_bot_name")
        String botName
) {
}
