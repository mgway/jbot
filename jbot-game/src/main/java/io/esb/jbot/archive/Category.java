package io.esb.jbot.archive;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Category implements Serializable {
    private static final long serialVersionUID = -5520167877820346818L;

    private UUID id;
    private String title;
    private String hint;
    private LocalDate aired;
    private List<Clue> clues;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public LocalDate getAired() {
        return aired;
    }

    public void setAired(LocalDate aired) {
        this.aired = aired;
    }

    public List<Clue> getClues() {
        return clues;
    }

    public void setClues(List<Clue> clues) {
        this.clues = clues;
    }
}
