package io.esb.jbot.dto.v1.game;

import java.io.Serializable;
import java.util.UUID;

public class Player implements Serializable {
    private static final long serialVersionUID = 7643522508542061364L;

    private UUID id;
    private Provider provider;
    private String username;
    private String team;
    private String displayName;

    public Player() {
    }

    public Player(Provider provider, String team, String username) {
        this.provider = provider;
        this.team = team;
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
