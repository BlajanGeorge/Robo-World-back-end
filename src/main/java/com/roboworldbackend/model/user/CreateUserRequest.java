package com.roboworldbackend.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roboworldbackend.utils.EmailConstraint;
import com.roboworldbackend.utils.PasswordConstraint;
import jakarta.validation.constraints.NotBlank;

/**
 * Model class for create user request
 *
 * @author Blajan George
 */
public record CreateUserRequest(
        @JsonProperty("email")
        @EmailConstraint
        String email,
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
