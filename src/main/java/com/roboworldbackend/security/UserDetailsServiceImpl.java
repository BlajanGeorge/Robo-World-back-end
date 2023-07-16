package com.roboworldbackend.security;

import com.roboworldbackend.db.model.User;
import com.roboworldbackend.db.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * User Details Service class implementation of spring security interface
 *
 * @author Blajan George
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws EntityNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(() -> new EntityNotFoundException(String.format("User with email %s not found.", username)));
        return new UserDetailsImpl(user);
    }
}
