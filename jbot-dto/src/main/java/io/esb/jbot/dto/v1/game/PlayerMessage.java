package io.esb.jbot.dto.v1.game;

import java.io.Serializable;

public class PlayerMessage implements Serializable {
    private static final long serialVersionUID = 6628095212591337849L;

    private Player player;
    private Channel channel;
    private MessageType type;
    private String text;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public enum MessageType {NEW_GAME, NEXT, SKIP, ANSWER, BID}
}
