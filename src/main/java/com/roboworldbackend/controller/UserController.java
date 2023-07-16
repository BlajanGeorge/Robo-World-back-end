package com.roboworldbackend.controller;

import com.roboworldbackend.model.LoginRequest;
import com.roboworldbackend.model.LoginResponse;
import com.roboworldbackend.model.RefreshTokenRequest;
import com.roboworldbackend.model.RefreshTokenResponse;
import com.roboworldbackend.model.user.ChangePasswordRequest;
import com.roboworldbackend.model.user.CreateUserRequest;
import com.roboworldbackend.model.user.GetUserResponse;
import com.roboworldbackend.model.user.UpdateUserRequest;
import com.roboworldbackend.service.api.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.roboworldbackend.utils.Constants.*;

/**
 * User controller
 *
 * @author Blajan George
 */
@Slf4j
@CrossOrigin
@RestController
public class UserController {

    /**
     * User Service
     */
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create user entry point
     *
     * @param request Create user dto
     * @return {@link ResponseEntity}
     */
    @PostMapping(USER_PATH)
    public ResponseEntity<Void> createUser(@Valid @RequestBody CreateUserRequest request) {
        log.info("Create user request received for email : {}", request.email());
        userService.createUser(request);
        return ResponseEntity.ok().build();
    }

    /**
     * Update user entry point
     *
     * @param request Update user dto
     * @return {@link ResponseEntity}
     */
    @PutMapping(USER_ID_PATH)
    public ResponseEntity<Void> updateUser(@Valid @RequestBody UpdateUserRequest request,
                                           @PathVariable(USER_ID_PARAM) Integer userId) {
        log.info("Update user request received for user id : {}", userId);
        userService.updateUser(request, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * Delete user entry point
     *
     * @return {@link ResponseEntity}
     */
    @DeleteMapping(USER_ID_PATH)
    public ResponseEntity<Void> deleteUser(@PathVariable(USER_ID_PARAM) Integer userId) {
        log.info("Delete user request received for user id : {}", userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }


    /**
     * Get user entry point
     *
     * @return {@link ResponseEntity}
     */
    @GetMapping(USER_ID_PATH)
    public ResponseEntity<GetUserResponse> getUser(@PathVariable(USER_ID_PARAM) Integer userId) {
        log.info("Get user request received for user id : {}", userId);
        GetUserResponse response = userService.getUser(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(USER_ID_PATH + "/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable(USER_ID_PARAM) Integer userId,
                                               @Valid @RequestBody ChangePasswordRequest request) {
        log.info("Change password request received for user {}", userId);
        userService.changePassword(userId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Login method
     *
     * @param loginRequest login request
     * @return {@link ResponseEntity}
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        log.info("Login request received for email : {}.", loginRequest.email());
        LoginResponse response = userService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        log.info("Refresh token request received.");
        RefreshTokenResponse response = userService.refreshToken(request);
        return ResponseEntity.ok(response);
    }

}
