package de.davidbohl.drinkinggameapi.repository.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document("game")
public class GameEntity {
    @Id
    private String id;

    private String title;

    private String description;

    private List<String> tags = new ArrayList<>();

    public GameEntity() {
    }

    public GameEntity(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public GameEntity(String id, String title, String description, List<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tags = tags;
    }
}
