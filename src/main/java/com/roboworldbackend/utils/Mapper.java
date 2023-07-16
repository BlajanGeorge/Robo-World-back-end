package com.roboworldbackend.utils;

import com.roboworldbackend.db.model.Bot;
import com.roboworldbackend.db.model.FriendRequest;
import com.roboworldbackend.db.model.Role;
import com.roboworldbackend.db.model.User;
import com.roboworldbackend.model.bot.GetBotResponse;
import com.roboworldbackend.model.bot.BotRequest;
import com.roboworldbackend.model.friend.GetFriendRequestResponse;
import com.roboworldbackend.model.friend.GetFriendResponse;
import com.roboworldbackend.model.user.CreateUserRequest;
import com.roboworldbackend.model.user.GetUserResponse;
import lombok.experimental.UtilityClass;

/**
 * Utility class for mapping
 *
 * @author Blajan George
 */
@UtilityClass
public class Mapper {

    public static User map(final CreateUserRequest request) {
        return new User(request.email(), request.password(), request.firstName(), request.lastName(), Role.CUSTOMER);
    }

    public static GetUserResponse map(final User user, final Integer friendsNumber) {
        return new GetUserResponse(user.getId(), user.getRole(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getBots().size(), friendsNumber);
    }

    public static GetBotResponse map(final Bot bot) {
        return new GetBotResponse(bot.getId(), bot.getName(), bot.isSelected());
    }

    public static Bot map(final BotRequest request, User user) {
        return new Bot(request.name(), false, user);
    }

    public static GetFriendResponse mapToFriend(User user) {
        return new GetFriendResponse(user.getId(), user.getFirstName(), user.getLastName());
    }

    public static GetFriendRequestResponse map(FriendRequest request, String firstName, String lastName) {
        return new GetFriendRequestResponse(request.getId(), request.getRequesterId(), firstName, lastName);
    }
}
