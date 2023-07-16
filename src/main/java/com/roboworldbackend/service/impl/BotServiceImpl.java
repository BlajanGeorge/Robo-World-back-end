package com.roboworldbackend.service.impl;

import com.roboworldbackend.db.model.Bot;
import com.roboworldbackend.db.model.User;
import com.roboworldbackend.db.repository.BotRepository;
import com.roboworldbackend.db.repository.UserRepository;
import com.roboworldbackend.model.bot.GetBotResponse;
import com.roboworldbackend.model.bot.BotRequest;
import com.roboworldbackend.service.api.BotService;
import com.roboworldbackend.utils.Mapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Bot service impl
 *
 * @author Blajan George
 */
@RequiredArgsConstructor
@Service
public class BotServiceImpl implements BotService {
    private final BotRepository botRepository;
    private final UserRepository userRepository;

    @Override
    public List<GetBotResponse> getBots(Integer userId) {
        return botRepository.getBotByUserId(userId).stream().map(Mapper::map).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteBot(Integer userId, Integer botId) {
        Bot bot = botRepository.findById(botId).orElseThrow(() -> new EntityNotFoundException(String.format("Bot %s not found", botId)));

        if (!Objects.equals(bot.getUser().getId(), userId)) {
            throw new IllegalArgumentException(String.format("Bot %s is not registered on user %s", botId, userId));
        }

        botRepository.delete(bot);
    }

    @Transactional
    @Override
    public void selectBot(Integer userId, Integer botId) {
        Bot bot = botRepository.findById(botId).orElseThrow(() -> new EntityNotFoundException(String.format("Bot %s not found", botId)));

        if (!Objects.equals(bot.getUser().getId(), userId)) {
            throw new IllegalArgumentException(String.format("Bot %s is not registered on user %s", botId, userId));
        }

        botRepository.updateCurrentSelectedBotToUnselected();
        botRepository.selectBot(botId);
    }

    @Transactional
    @Override
    public GetBotResponse updateBot(Integer userId, Integer botId, BotRequest request) {
        Bot bot = botRepository.findById(botId).orElseThrow(() -> new EntityNotFoundException(String.format("Bot %s not found", botId)));

        if (!Objects.equals(bot.getUser().getId(), userId)) {
            throw new IllegalArgumentException(String.format("Bot %s is not registered on user %s", botId, userId));
        }

        bot.setName(request.name());

        return Mapper.map(botRepository.save(bot));
    }

    @Override
    public GetBotResponse createBot(Integer userId, BotRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException(String.format("User %s not found", userId)));

        return Mapper.map(botRepository.save(Mapper.map(request, user)));
    }
}
