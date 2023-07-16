package com.roboworldbackend.model.bot;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Update bot request
 *
 * @author Blajan George
 */
public record BotRequest(
        @JsonProperty("name")
        String name
) {
}
