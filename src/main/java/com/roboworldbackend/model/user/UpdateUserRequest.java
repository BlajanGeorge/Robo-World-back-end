package com.roboworldbackend.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roboworldbackend.utils.PasswordConstraint;
import jakarta.validation.constraints.NotBlank;

/**
 * Model class for update user request
 *
 * @author Blajan George
 */
public record UpdateUserRequest(
        @JsonProperty("password")
        @PasswordConstraint
        String password,
        @JsonProperty("first_name")
        @NotBlank(message = "First name must not be blank")
        String firstName,
        @JsonProperty("last_name")
        @NotBlank(message = "Last name must not be blank")
        String lastName
) {
}
