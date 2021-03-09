package de.davidbohl.drinkinggameapi.repository.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class GameEntity {
    @Id
    private String id;

    private String title;

    private String description;

    public GameEntity() {
    }

    public GameEntity(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
