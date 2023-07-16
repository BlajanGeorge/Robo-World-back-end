package com.roboworldbackend.controller;

import com.roboworldbackend.db.model.Message;
import com.roboworldbackend.model.conversation.GetConversationsResponse;
import com.roboworldbackend.model.conversation.MessageResponse;
import com.roboworldbackend.service.api.ConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static com.roboworldbackend.utils.Constants.USER_ID_PARAM;
import static com.roboworldbackend.utils.Constants.USER_ID_PATH;

/**
 * Conversation controller
 *
 * @author Blajan George
 */
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
@Controller
public class ConversationController {
    private final ConversationService conversationService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    public void send(MessageResponse message) {
        log.info("Received a message from user {} to user {}", message.subject(), message.destination());
        conversationService.createMessage(message);
        simpMessagingTemplate.convertAndSend("/topic/user/" + message.destination(), message);
    }

    @PostMapping("/test/message")
    public ResponseEntity<Void> dummySend(@RequestBody String message) {
        send(new MessageResponse(message, Date.from(Instant.now()), 2, 1));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(USER_ID_PATH + "/conversations")
    public ResponseEntity<List<GetConversationsResponse>> getUserConversations(@PathVariable(USER_ID_PARAM) Integer userId) {
        log.info("Get conversations request received for user {}", userId);
        return new ResponseEntity<>(conversationService.getUserConversations(userId), HttpStatus.OK);
    }
}
