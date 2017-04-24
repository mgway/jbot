package io.esb.jbot.game;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class CategoryState implements Serializable {
    private static final long serialVersionUID = 5284887458275339623L;

    private String name;
    private LocalDate aired;
    private List<ClueState> clues;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getAired() {
        return aired;
    }

    public void setAired(LocalDate aired) {
        this.aired = aired;
    }

    public List<ClueState> getClues() {
        return clues;
    }

    public void setClues(List<ClueState> clues) {
        this.clues = clues;
    }
}
