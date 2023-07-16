package com.roboworldbackend.model.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roboworldbackend.model.user.GetUserResponse;

import java.util.List;

/**
 * Model class for get conversations request
 *
 * @author Blajan George
 */
public record GetConversationsResponse(
        @JsonProperty("user")
        GetUserResponse userResponse,
        @JsonProperty("messages")
        List<MessageResponse> messages
) {
}
