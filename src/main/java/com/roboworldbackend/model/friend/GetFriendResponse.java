package com.roboworldbackend.model.friend;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Friend model class
 *
 * @author Blajan George
 */
public record GetFriendResponse(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName
) {
}
