package io.esb.jbot.game;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryState implements Serializable {
    private static final long serialVersionUID = 5284887458275339623L;

    private UUID id;
    private String name;
    private String hint;
    private LocalDate aired;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
