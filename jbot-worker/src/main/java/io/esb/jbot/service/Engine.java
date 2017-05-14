package io.esb.jbot.service;


import io.esb.jbot.dao.GameRepository;
import io.esb.jbot.game.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Engine {
    private static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);

    private GameRepository gameRepository;

    private Map<Channel, GameState> games;

    @Autowired
    public Engine(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.games = new HashMap<>();
    }

    public GameState newGame(Channel channel, GameState.GameType gameType) {
        GameState game = gameRepository.buildGame(gameType);
        game.setChannel(channel);
        games.put(channel, game);

        LOGGER.debug("Created a new {} game for {}:{}:{}", gameType,
                channel.getProvider(), channel.getTeamId(), channel.getExternalId());
        return game;
    }

    public ClueState next(Channel channel) {
        GameState game = games.get(channel);
        return game.next();
    }

    public Response attemptAnswer(Channel channel, Player player, String text) {
        GameState game = games.get(channel);
        return game.attemptAnswer(player, text);
    }
}
