package io.esb.jbot.game;

import io.esb.jbot.util.WhiteSimilarity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;


public class ClueState implements Serializable {
    private static final long serialVersionUID = 3068762619591621205L;

    private UUID id;
    private String question;
    private int value;
    private boolean dailyDouble;
    private String[] solutions;
    private String[] media;
    private Player winner;

    public Response check(String provided) {
        Response response = new Response();

        for (String solution: solutions) {
            if (solution.equalsIgnoreCase(provided)) {
                response.setExact(true);
                break;
            }
            if (response.setDistance(WhiteSimilarity.compareStrings(provided, solution))) {
                break;
            }
        }

        return response;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isDailyDouble() {
        return dailyDouble;
    }

    public void setDailyDouble(boolean dailyDouble) {
        this.dailyDouble = dailyDouble;
    }

    public String[] getSolutions() {
        return solutions;
    }

    public void setSolutions(String[] solutions) {
        this.solutions = solutions;
    }

    public String[] getMedia() {
        return media;
    }

    public void setMedia(String[] media) {
        this.media = media;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
