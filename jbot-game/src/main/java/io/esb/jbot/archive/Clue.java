package io.esb.jbot.archive;


import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class Clue implements Serializable {
    private static final long serialVersionUID = 7467786038217045389L;

    private UUID id;
    private String question;
    private int position;
    private int value;
    private boolean dailyDouble;
    private List<String> solutions;

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

    public List<String> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<String> solutions) {
        this.solutions = solutions;
    }
}
