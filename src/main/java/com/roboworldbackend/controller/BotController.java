package com.roboworldbackend.controller;

import com.roboworldbackend.model.bot.GetBotResponse;
import com.roboworldbackend.model.bot.BotRequest;
import com.roboworldbackend.service.api.BotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.roboworldbackend.utils.Constants.BOTS_PATH;

/**
 * Bot controller
 *
 * @author Blajan George
 */
@Slf4j
@CrossOrigin
@RequiredArgsConstructor
@RestController
public class BotController {
    private final BotService botService;

    @GetMapping(BOTS_PATH)
    public ResponseEntity<List<GetBotResponse>> getBots(@PathVariable("user_id") Integer userId) {
        log.info("Get bots request received for user {}", userId);
        return new ResponseEntity<>(botService.getBots(userId), HttpStatus.OK);
    }

    @DeleteMapping(BOTS_PATH + "/{bot_id}")
    public ResponseEntity<Void> deleteBot(@PathVariable("bot_id") Integer botId,
                                          @PathVariable("user_id") Integer userId) {
        log.info("Delete bot {} request received for user {}", botId, userId);
        botService.deleteBot(userId, botId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(BOTS_PATH + "/{bot_id}/select")
    public ResponseEntity<Void> selectBot(@PathVariable("bot_id") Integer botId,
                                          @PathVariable("user_id") Integer userId) {
        log.info("Select bot {} request received for user {}", botId, userId);
        botService.selectBot(userId, botId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(BOTS_PATH + "/{bot_id}")
    public ResponseEntity<GetBotResponse> updateBot(@PathVariable("bot_id") Integer botId,
                                                    @PathVariable("user_id") Integer userId,
                                                    @RequestBody BotRequest request) {
        log.info("Update bot {} request received for user {}", botId, userId);
        return new ResponseEntity<>(botService.updateBot(userId, botId, request), HttpStatus.OK);
    }

    @PostMapping(BOTS_PATH)
    public ResponseEntity<GetBotResponse> createBot(@PathVariable("user_id") Integer userId,
                                                    @RequestBody BotRequest request) {
        log.info("Create bot request received for user {}", userId);
        return new ResponseEntity<>(botService.createBot(userId, request), HttpStatus.OK);
    }
}
