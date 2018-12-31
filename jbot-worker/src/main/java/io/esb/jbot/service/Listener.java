package io.esb.jbot.service;

import io.esb.jbot.dto.v1.game.GameState.GameType;
import io.esb.jbot.dto.v1.game.PlayerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class Listener {

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Autowired
    private Engine engine;

    @JmsListener(destination = "${queue.game.name}")
    public void gameQueueListener(PlayerMessage message) {
        switch (message.getType()) {
            case NEW_GAME:
                engine.newGame(message.getChannel(), parseGameType(message.getText()));
                break;
            default:
                System.out.println("Hi");
        }
    }

    private GameType parseGameType(String type) {
        return GameType.valueOf(type.toUpperCase());
    }
}
