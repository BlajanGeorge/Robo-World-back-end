package com.roboworldbackend.service.impl;

import com.roboworldbackend.db.model.Bot;
import com.roboworldbackend.db.model.FriendRelationship;
import com.roboworldbackend.db.model.User;
import com.roboworldbackend.db.repository.FriendRelationshipRepository;
import com.roboworldbackend.db.repository.UserRepository;
import com.roboworldbackend.exception.InvalidJWTException;
import com.roboworldbackend.model.LoginRequest;
import com.roboworldbackend.model.LoginResponse;
import com.roboworldbackend.model.RefreshTokenRequest;
import com.roboworldbackend.model.RefreshTokenResponse;
import com.roboworldbackend.model.user.ChangePasswordRequest;
import com.roboworldbackend.model.user.CreateUserRequest;
import com.roboworldbackend.model.user.GetUserResponse;
import com.roboworldbackend.model.user.UpdateUserRequest;
import com.roboworldbackend.security.TokenService;
import com.roboworldbackend.security.UserDetailsImpl;
import com.roboworldbackend.service.api.UserService;
import com.roboworldbackend.utils.Mapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * User Service implementation
 *
 * @author Blajan George
 */
@Service
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND_ERROR = "User with id %s not found.";
    /**
     * User repository for data manipulation
     */
    private final UserRepository userRepository;
    private final FriendRelationshipRepository friendRelationshipRepository;
    /**
     * Password hashing component
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Authentication Manager interface provided by Spring security
     */
    private final AuthenticationManager authenticationManager;
    /**
     * Inject our custom User Details Service Impl
     */
    private final UserDetailsService userDetailsService;

    /**
     * Service to implement JWT operations
     */
    private final TokenService tokenService;


    public UserServiceImpl(final UserRepository userRepository,
                           final PasswordEncoder passwordEncoder,
                           final AuthenticationManager authenticationManager,
                           final UserDetailsService userDetailsService,
                           final TokenService tokenService,
                           final FriendRelationshipRepository friendRelationshipRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.friendRelationshipRepository = friendRelationshipRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void createUser(CreateUserRequest createUserRequest) {
        userRepository.save(Mapper.map(new CreateUserRequest(
                createUserRequest.email(),
                passwordEncoder.encode(createUserRequest.password()),
                createUserRequest.firstName(),
                createUserRequest.lastName())));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updateUser(UpdateUserRequest updateUserRequest, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_ERROR, userId)));
        user.setPassword(passwordEncoder.encode(updateUserRequest.password()));
        userRepository.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_ERROR, userId)));
        userRepository.delete(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GetUserResponse getUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_ERROR, userId)));
        return Mapper.map(user, friendRelationshipRepository.getNumberOfFriends(user.getId()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(request.email());
        final String jwt = tokenService.generateToken(userDetails);
        final String refreshJwt = tokenService.generateRefreshToken(userDetails);

        final Optional<Bot> maybeBot = userDetails.getBots().stream().filter(Bot::isSelected).findAny();

        return new LoginResponse(
                userDetails.getUserId(),
                userDetails.getUserRole(),
                jwt,
                refreshJwt,
                maybeBot.map(Bot::getName).orElse(null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
        if (!tokenService.validateSignature(request.token()) || !tokenService.validateToken(request.refreshToken())) {
            throw new InvalidJWTException("Invalid token or refresh token.");
        }

        final UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(request.email());
        return new RefreshTokenResponse(tokenService.generateToken(userDetails));
    }

    @Transactional
    @Override
    public void changePassword(Integer userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format(USER_NOT_FOUND_ERROR, userId)));

        if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Wrong current password");
        }

        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
    }
}
