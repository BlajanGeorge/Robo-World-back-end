package com.roboworldbackend.service.impl;

import com.roboworldbackend.db.model.FriendRelationship;
import com.roboworldbackend.db.model.User;
import com.roboworldbackend.db.repository.FriendRelationshipRepository;
import com.roboworldbackend.db.repository.FriendRequestRepository;
import com.roboworldbackend.db.repository.UserRepository;
import com.roboworldbackend.model.friend.FriendRequest;
import com.roboworldbackend.model.friend.FriendRequestDecision;
import com.roboworldbackend.model.friend.GetFriendRequestResponse;
import com.roboworldbackend.model.friend.GetFriendResponse;
import com.roboworldbackend.service.api.FriendsService;
import com.roboworldbackend.utils.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Friends service impl
 *
 * @author Blajan George
 */
@RequiredArgsConstructor
@Service
public class FriendsServiceImpl implements FriendsService {
    private final UserRepository userRepository;
    private final FriendRelationshipRepository friendRelationshipRepository;
    private final FriendRequestRepository friendRequestRepository;

    @Override
    public List<GetFriendResponse> getFriends(Integer userId) {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));

        return friendRelationshipRepository
                .getFriendRelationshipByFirstFriendIdOrOtherFriendId(userId)
                .stream()
                .map(f -> {
                    if (!Objects.equals(f.getFirstFriendId(), userId)) {
                        return Mapper.mapToFriend(userRepository.findById(f.getFirstFriendId()).get());
                    } else {
                        return Mapper.mapToFriend(userRepository.findById(f.getOtherFriendId()).get());
                    }
                })
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void removeFriend(Integer userId, Integer friendId) {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));
        userRepository.findById(friendId).orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", friendId)));

        friendRelationshipRepository.removeFriend(friendId);
    }

    @Transactional
    @Override
    public void sendFriendRequest(Integer userId, FriendRequest request) {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));
        User user = userRepository.findById(request.id()).orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", request.id())));

        List<FriendRelationship> relationships = friendRelationshipRepository.getFriendRelationshipByFirstFriendIdOrOtherFriendId(userId);
        Optional<FriendRelationship> relationship = relationships.stream().filter(f -> Objects.equals(f.getFirstFriendId(), request.id()) || Objects.equals(f.getOtherFriendId(), request.id())).findAny();

        if (relationship.isPresent()) {
            throw new IllegalArgumentException("User is already a friend");
        }

        Optional<com.roboworldbackend.db.model.FriendRequest> friendRequest = user.getFriendRequests().stream().filter(r -> Objects.equals(r.getRequesterId(), userId)).findAny();

        if (friendRequest.isPresent()) {
            throw new IllegalArgumentException("There is an already active friend request for this user");
        }

        friendRequestRepository.save(new com.roboworldbackend.db.model.FriendRequest(userId, user));
    }

    @Override
    public List<GetFriendRequestResponse> getFriendRequests(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));

        return user.getFriendRequests().stream().map(request -> {
            User requester = userRepository.findById(request.getRequesterId()).get();
            return Mapper.map(request, requester.getFirstName(), requester.getLastName());
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void friendRequestDecision(Integer userId, FriendRequestDecision decision) {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));
        com.roboworldbackend.db.model.FriendRequest request = friendRequestRepository.findById(decision.id()).orElseThrow(() -> new EntityNotFoundException(String.format("Friend request %s not found", decision.id())));

        if (decision.accepted()) {
            friendRelationshipRepository.save(new FriendRelationship(request.getRequesterId(), userId));
        }

        friendRequestRepository.delete(request);
    }
}
