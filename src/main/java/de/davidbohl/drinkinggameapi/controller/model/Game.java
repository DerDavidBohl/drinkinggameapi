package de.davidbohl.drinkinggameapi.controller.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Game {

    private String id;
    private String title;
    private String description;
    private List<String> tags = new ArrayList<>();

    public Game(String id, String title, String description, List<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tags = tags;
    }

    public Game(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public Game() {
    }
}
