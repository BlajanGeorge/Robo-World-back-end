package com.roboworldbackend.model.friend;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Get friend request response
 *
 * @author Blajan George
 */
public record GetFriendRequestResponse(
        @JsonProperty("request_id")
        Integer requestId,
        @JsonProperty("requester_id")
        Integer requesterId,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName
) {
}
