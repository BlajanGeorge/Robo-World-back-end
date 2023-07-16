package com.roboworldbackend.service.api;

import com.roboworldbackend.model.friend.FriendRequest;
import com.roboworldbackend.model.friend.FriendRequestDecision;
import com.roboworldbackend.model.friend.GetFriendRequestResponse;
import com.roboworldbackend.model.friend.GetFriendResponse;

import java.util.List;

/**
 * Friends service interface
 *
 * @author Blajan George
 */
public interface FriendsService {
    List<GetFriendResponse> getFriends(Integer userId);

    void removeFriend(Integer userId, Integer friendId);

    void sendFriendRequest(Integer userId, FriendRequest request);

    List<GetFriendRequestResponse> getFriendRequests(Integer userId);

    void friendRequestDecision(Integer userId, FriendRequestDecision decision);
}
