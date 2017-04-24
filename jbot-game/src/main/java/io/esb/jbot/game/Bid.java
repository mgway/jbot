package io.esb.jbot.game;

import java.io.Serializable;

public class Bid implements Serializable {
    private static final long serialVersionUID = -6035266872608578868L;

    private Player player;
    private int value;
    private boolean chosen;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }
}
