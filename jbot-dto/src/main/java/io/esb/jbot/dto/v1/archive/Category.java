package io.esb.jbot.dto.v1.archive;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Category implements Serializable {
    private static final long serialVersionUID = -5520167877820346818L;

    private UUID id;
    private UUID gameId;
    private String title;
    private String hint;
    private List<Clue> clues;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public List<Clue> getClues() {
        return clues;
    }

    public void setClues(List<Clue> clues) {
        this.clues = clues;
    }
}
