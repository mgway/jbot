package io.esb.jbot.game;

import java.util.UUID;

public class Response {
    private Player player;
    private String text;
    private boolean correct;
    private double distance;
    private boolean exact;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public double getDistance() {
        return distance;
    }

    public boolean setDistance(double distance) {
        this.distance = distance;
        if (this.distance > 0.6D) {
            this.correct = true;
        }

        return this.correct;
    }

    public boolean isExact() {
        return exact;
    }

    public void setExact(boolean exact) {
        this.correct = exact;
        this.exact = exact;
    }
}
