package com.roboworldbackend.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roboworldbackend.utils.PasswordConstraint;

/**
 * Change password request
 *
 * @author Blajan George
 */
public record ChangePasswordRequest(
        @JsonProperty("current_password")
        String currentPassword,
        @JsonProperty("new_password")
        @PasswordConstraint
        String newPassword
) {
}
