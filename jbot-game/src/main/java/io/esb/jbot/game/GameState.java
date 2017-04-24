package io.esb.jbot.game;

import info.debatty.java.stringsimilarity.JaroWinkler;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class GameState implements Serializable {
    private static final long serialVersionUID = -202179678086026193L;

    private UUID id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String team;
    private String channel;
    private List<CategoryState> categories;
    private Map<String, PlayerState> players;
    private ClueState currentClue;

    public GameState() {
        this.players = new HashMap<>();
        this.startTime = LocalDateTime.now();
    }

    public synchronized void attemptAnswer(Response response) {
        if (currentClue != null) {
            currentClue.checkResponse(response);
            PlayerState player = players.computeIfAbsent(response.getPlayer().getUsername(), uid ->
                    new PlayerState(response.getPlayer())
            );

            if (response.isCorrect()) {
                currentClue.setWinner(response.getPlayer());
                player.correctAnswer(currentClue.getValue());
                currentClue = null;
            } else {
                player.incorrectAnswer(currentClue.getValue());
            }
        }
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<CategoryState> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryState> categories) {
        this.categories = categories;
    }

    public ClueState getCurrentClue() {
        return currentClue;
    }

    public void setCurrentClue(ClueState currentClue) {
        this.currentClue = currentClue;
    }
}
