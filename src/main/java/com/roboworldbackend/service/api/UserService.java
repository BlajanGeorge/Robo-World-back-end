package com.roboworldbackend.service.api;

import com.roboworldbackend.model.LoginRequest;
import com.roboworldbackend.model.LoginResponse;
import com.roboworldbackend.model.RefreshTokenRequest;
import com.roboworldbackend.model.RefreshTokenResponse;
import com.roboworldbackend.model.user.ChangePasswordRequest;
import com.roboworldbackend.model.user.CreateUserRequest;
import com.roboworldbackend.model.user.GetUserResponse;
import com.roboworldbackend.model.user.UpdateUserRequest;

/**
 * User Service specification
 *
 * @author Blajan George
 */
public interface UserService {

    /**
     * Method to create client user
     *
     * @param createUserRequest create user dto
     */
    void createUser(CreateUserRequest createUserRequest);


    /**
     * Method to update user
     *
     * @param updateUserRequest update user dto
     * @param userId            user id
     */
    void updateUser(UpdateUserRequest updateUserRequest, Integer userId);

    /**
     * Method to delete user
     *
     * @param userId user id
     */
    void deleteUser(Integer userId);

    /**
     * Method to get user
     *
     * @param userId user id
     * @return {@link GetUserResponse}
     */
    GetUserResponse getUser(Integer userId);

    /**
     * Login method
     *
     * @param request Login request
     * @return {@link LoginResponse}
     */
    LoginResponse login(LoginRequest request);

    /**
     * Method to refresh token
     *
     * @param request Refresh token request
     * @return {@link RefreshTokenResponse}
     */
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void changePassword(Integer userId, ChangePasswordRequest request);
}
