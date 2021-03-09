package de.davidbohl.drinkinggameapi.controller.model;

import lombok.Data;

@Data
public class Game {
    private String id;
    private String title;
    private String description;

    public Game() {
    }

    public Game(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
