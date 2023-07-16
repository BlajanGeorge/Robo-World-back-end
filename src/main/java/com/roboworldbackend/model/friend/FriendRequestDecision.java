package com.roboworldbackend.model.friend;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Friend request decision model
 *
 * @author Blajan George
 */
public record FriendRequestDecision(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("accepted")
        boolean accepted
) {
}
