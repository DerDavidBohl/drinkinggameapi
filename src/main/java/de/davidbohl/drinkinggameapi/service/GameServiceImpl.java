package de.davidbohl.drinkinggameapi.service;

import de.davidbohl.drinkinggameapi.controller.model.Game;
import de.davidbohl.drinkinggameapi.repository.GameRepository;
import de.davidbohl.drinkinggameapi.repository.entity.GameEntity;
import de.davidbohl.drinkinggameapi.service.exception.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    RandomnessService randomnessService;

    @Override
    public List<Game> getAllGames(List<String> filterTags) {

        List<GameEntity> gameEntities;

        if (filterTags == null || filterTags.size() < 1) {
            gameEntities = gameRepository.findAll();
        } else {
            gameEntities = gameRepository.findByTagsIn(filterTags);
        }

        return gameEntities.stream()
                .map(this::mapGameEntityToGame)
                .collect(Collectors.toList());
    }

    private Game mapGameEntityToGame(GameEntity gameEntity) {
        return new Game(gameEntity.getId(), gameEntity.getTitle(), gameEntity.getDescription(), gameEntity.getTags());
    }

    private GameEntity mapGameToGameEntity(Game game) {
        return new GameEntity(game.getId(), game.getTitle(), game.getDescription(), game.getTags());
    }

    @Override
    public Game createNewGame(Game game) {
        game.setId(null);
        return mapGameEntityToGame(gameRepository.insert(mapGameToGameEntity(game)));
    }

    @Override
    public Game updateGame(String gameId, Game game) {

        Optional<GameEntity> foundGame = gameRepository.findById(gameId);
        if (foundGame.isEmpty()) {
            throw new GameNotFoundException(gameId);
        }

        game.setId(foundGame.get().getId());

        return mapGameEntityToGame(gameRepository.save(mapGameToGameEntity(game)));
    }

    @Override
    public Game getGameById(String gameId) {
        Optional<GameEntity> gameEntityOptional = gameRepository.findById(gameId);

        if (gameEntityOptional.isEmpty()) {
            throw new GameNotFoundException(gameId);
        }

        return mapGameEntityToGame(gameEntityOptional.get());
    }

    @Override
    public void deleteGameById(String gameId) {

        if (gameRepository.findById(gameId).isEmpty()) {
            throw new GameNotFoundException(gameId);
        }

        gameRepository.deleteById(gameId);
    }

    @Override
    public Game getRandomGame(List<String> filterTags) {

        List<GameEntity> gameEntities;

        if (filterTags == null || filterTags.size() < 1) {
            gameEntities = gameRepository.findAll();
        } else {
            gameEntities = gameRepository.findByTagsIn(filterTags);
        }
        return mapGameEntityToGame(gameEntities.get(randomnessService.getRandomInt(gameEntities.size())));
    }

    @Override
    public List<String> getAllTags(String searchPhrase) {

        List<String> tags = gameRepository.findAll().stream()
                .flatMap(game -> game.getTags().stream())
                .filter(tag -> (searchPhrase == null || tag.toLowerCase().contains(searchPhrase.toLowerCase())))
                .distinct()
                .collect(Collectors.toList());
        
        return tags;
    }
}
