package com.roboworldbackend.service.api;

import com.roboworldbackend.model.bot.GetBotResponse;
import com.roboworldbackend.model.bot.BotRequest;

import java.util.List;

/**
 * Bot service interface
 *
 * @author Blajan George
 */
public interface BotService {
    List<GetBotResponse> getBots(Integer userId);

    void deleteBot(Integer userId, Integer botId);

    void selectBot(Integer userId, Integer botId);

    GetBotResponse updateBot(Integer userId, Integer botId, BotRequest request);

    GetBotResponse createBot(Integer userId, BotRequest request);
}
