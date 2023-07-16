package com.roboworldbackend.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roboworldbackend.db.model.Role;

/**
 * Model class for get user request
 *
 * @author Blajan George
 */
public record GetUserResponse(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("role")
        Role role,
        @JsonProperty("email")
        String email,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("bots_number")
        Integer botsNumber,
        @JsonProperty("friends_number")
        Integer friendsNumber
) {
}
