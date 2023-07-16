package com.roboworldbackend.service.impl;

import com.roboworldbackend.db.model.Conversation;
import com.roboworldbackend.db.model.Message;
import com.roboworldbackend.db.model.User;
import com.roboworldbackend.db.repository.ConversationRepository;
import com.roboworldbackend.db.repository.FriendRelationshipRepository;
import com.roboworldbackend.db.repository.MessageRepository;
import com.roboworldbackend.db.repository.UserRepository;
import com.roboworldbackend.model.conversation.GetConversationsResponse;
import com.roboworldbackend.model.conversation.MessageResponse;
import com.roboworldbackend.service.api.ConversationService;
import com.roboworldbackend.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Conversation service impl
 *
 * @author Blajan George
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ConversationServiceImpl implements ConversationService {
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final FriendRelationshipRepository friendRelationshipRepository;
    private final MessageRepository messageRepository;

    @Transactional
    @Override
    public void createMessage(MessageResponse message) {
        Optional<Conversation> maybeConversation = conversationRepository.getConversation(message.subject(), message.destination());

        if (maybeConversation.isPresent()) {
            log.info("A conversation already exist between user {} and {}, will attach the message", message.subject(), message.destination());
            Conversation conversation = maybeConversation.get();
            Message dbMessage = new Message(message.subject(), message.destination(), message.content(), message.timestamp(), conversation);
            messageRepository.save(dbMessage);
        } else {
            log.info("Conversation not found between user {} and {}, will create a new one", message.subject(), message.destination());
            Conversation conversation = conversationRepository.save(new Conversation(message.subject(), message.destination(), Collections.emptyList()));
            Message dbMessage = new Message(message.subject(), message.destination(), message.content(), message.timestamp(), conversation);
            messageRepository.save(dbMessage);
        }
    }

    @Override
    public List<GetConversationsResponse> getUserConversations(Integer userId) {
        List<Conversation> conversations = conversationRepository.getUserConversations(userId);

        return conversations.stream().map(conv -> {
                    User user;
                    if (!Objects.equals(conv.getUser1(), userId)) {
                        user = userRepository.findById(conv.getUser1()).get();
                    } else {
                        user = userRepository.findById(conv.getUser2()).get();
                    }

                    return new GetConversationsResponse(
                            Mapper.map(user, friendRelationshipRepository.getNumberOfFriends(user.getId())),
                            conv.getMessages().stream().map(message -> new MessageResponse(message.getContent(), message.getTimestamp(), message.getSubject(), message.getDestination())).collect(Collectors.toList())
                    );
                }
        ).collect(Collectors.toList());
    }
}
