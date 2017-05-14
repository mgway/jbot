package io.esb.jbot.game;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class Channel implements Serializable {
    private static final long serialVersionUID = 9051005125585336510L;

    private UUID id;
    private String externalId;
    private String teamId;
    private Provider provider;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Channel channel = (Channel) obj;
        return Objects.equals(externalId, channel.externalId) &&
                Objects.equals(teamId, channel.teamId) &&
                provider == channel.provider;
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalId, teamId, provider);
    }
}
