package com.roboworldbackend.service.api;

import com.roboworldbackend.model.conversation.GetConversationsResponse;
import com.roboworldbackend.model.conversation.MessageResponse;

import java.util.List;

/**
 * Bot service interface
 *
 * @author Blajan George
 */
public interface ConversationService {
    void createMessage(MessageResponse message);
    List<GetConversationsResponse> getUserConversations(Integer userId);
}
