package io.esb.jbot.dto.v1.game;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

public class GameState implements Serializable {
    private static final long serialVersionUID = -202179678086026193L;

    private UUID id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Channel channel;
    private GameType gameType;
    private List<CategoryState> categories;
    private Stack<ClueState> clues;
    private Map<String, PlayerState> players;
    private ClueState currentClue;

    public GameState() {
        this.players = new HashMap<>();
        this.startTime = LocalDateTime.now();
        this.categories = new ArrayList<>();
        this.clues = new Stack<>();
    }

    public synchronized Response attemptAnswer(Player player, String answer) {
        if (currentClue != null) {
            Response response = currentClue.check(answer);
            PlayerState playerState = players.computeIfAbsent(player.getUsername(), uid ->
                    new PlayerState(response.getPlayer())
            );

            if (response.isCorrect()) {
                currentClue.setWinner(response.getPlayer());
                playerState.correctAnswer(currentClue.getValue());
                currentClue = null;
            } else {
                playerState.incorrectAnswer(currentClue.getValue());
            }

            return response;
        }

        throw new IllegalStateException("There is no clue in progress");
    }

    public synchronized ClueState next() {
        if (currentClue != null) {
            try {
                currentClue = clues.pop();
                return currentClue;
            } catch (EmptyStackException ex) {
                // Game is over
                this.endTime = LocalDateTime.now();
            }
        }
        throw new IllegalStateException("There is already a clue in progress");
    }


    public enum GameType { CLASSIC, REPLAY, RANDOM }

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

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public Stack<ClueState> getClues() {
        return clues;
    }

    public void setClues(Stack<ClueState> clues) {
        this.clues = clues;
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

    public Map<String, PlayerState> getPlayers() {
        return players;
    }

    public void setPlayers(Map<String, PlayerState> players) {
        this.players = players;
    }
}
