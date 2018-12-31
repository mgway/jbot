package io.esb.jbot.dto.v1.archive;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Game implements Serializable {
    private static final long serialVersionUID = 1385489233263035703L;

    private UUID id;
    private int showNumber;
    private List<Category> categories;
    private LocalDate aired;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getAired() {
        return aired;
    }

    public void setAired(LocalDate aired) {
        this.aired = aired;
    }
}
