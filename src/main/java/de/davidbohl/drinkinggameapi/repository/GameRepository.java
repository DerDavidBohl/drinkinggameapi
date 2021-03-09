package de.davidbohl.drinkinggameapi.repository;

import de.davidbohl.drinkinggameapi.repository.entity.GameEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<GameEntity, String> {
}
