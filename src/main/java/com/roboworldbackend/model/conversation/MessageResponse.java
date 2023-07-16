package com.roboworldbackend.model.conversation;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Model class for get messages request
 *
 * @author Blajan George
 */
public record MessageResponse(
        @JsonProperty("content")
        String content,
        @JsonProperty("timestamp")
        Date timestamp,
        @JsonProperty("subject")
        Integer subject,
        @JsonProperty("destination")
        Integer destination
) {
}
