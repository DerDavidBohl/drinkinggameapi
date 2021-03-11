package de.davidbohl.drinkinggameapi.repository;

import de.davidbohl.drinkinggameapi.repository.entity.GameEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;


public interface GameRepository extends MongoRepository<GameEntity, String> {
    List<GameEntity> findByTagsIn(List<String> tags);
}
