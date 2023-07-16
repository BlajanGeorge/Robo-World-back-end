package com.roboworldbackend.utils;

import lombok.experimental.UtilityClass;

/**
 * Utility class for storing constants
 *
 * @author Blajan George
 */
@UtilityClass
public class Constants {
    public static final String USER_PATH = "/users";
    public static final String USER_ID_PARAM = "user_id";
    public static final String USER_ID_PATH = USER_PATH + "/{" + USER_ID_PARAM + "}";
    public static final String BOTS_PATH = USER_ID_PATH + "/bots";
    public static final String FRIENDS_PATH = USER_ID_PATH + "/friends";
    public static final String FRIEND_REQUESTS_PATH = USER_ID_PATH + "/friend-requests";
}
