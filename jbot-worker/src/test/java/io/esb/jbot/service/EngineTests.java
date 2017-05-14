package io.esb.jbot.service;


import io.esb.jbot.boot.Application;
import io.esb.jbot.game.Channel;
import io.esb.jbot.game.GameState;
import io.esb.jbot.game.PlayerMessage;
import io.esb.jbot.game.Provider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Sql({"classpath:fixture/cleanup.sql", "classpath:fixture/archive.sql"})
public class EngineTests {

    @Autowired
    private Engine engine;

    @Autowired
    private AmqpTemplate template;

    @Test
    public void contextLoads() {
    }

    @Test
    public void newClassicGame() {
        Channel channel = new Channel();
        channel.setExternalId("CHANNEL01");
        channel.setProvider(Provider.SLACK);
        channel.setTeamId("TEAM01");

        GameState gameState = engine.newGame(channel, GameState.GameType.CLASSIC);
        assertNotNull(gameState);
        assertNotNull(gameState.getId());
        assertEquals(GameState.GameType.CLASSIC, gameState.getGameType());
        assertNotNull(gameState.getCategories());
        assertEquals(6, gameState.getCategories().size());
    }
}
