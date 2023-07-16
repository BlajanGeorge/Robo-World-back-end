package com.roboworldbackend.controller;

import com.roboworldbackend.model.friend.FriendRequest;
import com.roboworldbackend.model.friend.FriendRequestDecision;
import com.roboworldbackend.model.friend.GetFriendRequestResponse;
import com.roboworldbackend.model.friend.GetFriendResponse;
import com.roboworldbackend.service.api.FriendsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.roboworldbackend.utils.Constants.FRIENDS_PATH;
import static com.roboworldbackend.utils.Constants.FRIEND_REQUESTS_PATH;

/**
 * Friends controller
 *
 * @author Blajan George
 */
@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
public class FriendsController {
    private final FriendsService friendsService;

    @GetMapping(FRIENDS_PATH)
    public ResponseEntity<List<GetFriendResponse>> getFriends(@PathVariable("user_id") Integer userId) {
        log.info("Get friends request received for user {}", userId);
        return new ResponseEntity<>(friendsService.getFriends(userId), HttpStatus.OK);
    }

    @DeleteMapping(FRIENDS_PATH + "/{friend_id}")
    public ResponseEntity<Void> removeFriend(@PathVariable("user_id") Integer userId,
                                             @PathVariable("friend_id") Integer friendId) {
        log.info("Get remove friend {} for user {}", friendId, userId);
        friendsService.removeFriend(userId, friendId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(FRIENDS_PATH)
    public ResponseEntity<Void> sendFriendRequest(@PathVariable("user_id") Integer userId,
                                                  @RequestBody FriendRequest request) {
        log.info("Friend request received from user {} to user {}", userId, request);
        friendsService.sendFriendRequest(userId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(FRIEND_REQUESTS_PATH)
    public ResponseEntity<List<GetFriendRequestResponse>> getFriendRequests(@PathVariable("user_id") Integer userId) {
        log.info("Get friend requests request received for user {}", userId);
        return new ResponseEntity<>(friendsService.getFriendRequests(userId), HttpStatus.OK);
    }

    @PostMapping(FRIEND_REQUESTS_PATH + "/decision")
    public ResponseEntity<Void> friendRequestDecision(@PathVariable("user_id") Integer userId,
                                                      @RequestBody FriendRequestDecision decision) {
        log.info("Get decision for friend request {}, user {}", decision.id(), userId);
        friendsService.friendRequestDecision(userId, decision);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
