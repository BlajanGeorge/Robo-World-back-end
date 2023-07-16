package com.roboworldbackend.model.bot;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class for get bot request
 *
 * @author Blajan George
 */
public record GetBotResponse(
        @JsonProperty("id")
        Integer id,
        @JsonProperty("name")
        String name,
        @JsonProperty("selected")
        boolean selected
) {
}
