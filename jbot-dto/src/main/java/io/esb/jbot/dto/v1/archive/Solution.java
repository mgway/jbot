package io.esb.jbot.dto.v1.archive;

import java.io.Serializable;
import java.util.UUID;

public class Solution implements Serializable {

    private UUID id;
    private UUID clueId;
    private String solution;
    private Matcher matcher = Matcher.FUZZY;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClueId() {
        return clueId;
    }

    public void setClueId(UUID clueId) {
        this.clueId = clueId;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Matcher getMatcher() {
        return matcher;
    }

    public void setMatcher(Matcher matcher) {
        this.matcher = matcher;
    }

    public enum Matcher { FUZZY, EXACT, NUMERIC }
}
