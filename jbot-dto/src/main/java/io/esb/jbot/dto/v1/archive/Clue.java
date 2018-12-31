package io.esb.jbot.dto.v1.archive;


import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Clue implements Serializable {
    private static final long serialVersionUID = 7467786038217045389L;

    private UUID id;
    private UUID categoryId;
    private String prompt;
    private int position;
    private int value;
    private boolean dailyDouble;
    private List<Solution> solutions;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }
}
