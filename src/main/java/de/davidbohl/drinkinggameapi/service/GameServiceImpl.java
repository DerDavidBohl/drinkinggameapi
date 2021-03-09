package de.davidbohl.drinkinggameapi.service;

import de.davidbohl.drinkinggameapi.controller.model.Game;
import de.davidbohl.drinkinggameapi.repository.GameRepository;
import de.davidbohl.drinkinggameapi.repository.entity.GameEntity;
import de.davidbohl.drinkinggameapi.service.exception.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    GameRepository gameRepository;

    @Autowired
    RandomnessService randomnessService;

    @Override
    public List<Game> getAllGames() {
        return gameRepository.findAll().stream()
                .map(this::mapGameEntityToGame)
                .collect(Collectors.toList());
    }

    private Game mapGameEntityToGame(GameEntity gameEntity) {
        return new Game(gameEntity.getId(), gameEntity.getTitle(), gameEntity.getDescription());
    }

    private GameEntity mapGameToGameEntity(Game game) {
        return new GameEntity(game.getId(), game.getTitle(), game.getDescription());
    }

    @Override
    public Game createNewGame(Game game) {
        game.setId(null);
        return mapGameEntityToGame(gameRepository.insert(mapGameToGameEntity(game)));
    }

    @Override
    public Game updateGame(String gameId, Game game) {

        Optional<GameEntity> foundGame = gameRepository.findById(gameId);
        if(foundGame.isEmpty()) {
            throw new GameNotFoundException(gameId.toString());
        }

        game.setId(foundGame.get().getId());

        return mapGameEntityToGame(gameRepository.save(mapGameToGameEntity(game)));
    }

    @Override
    public Game getGameById(String gameId) {
        Optional<GameEntity> gameEntityOptional = gameRepository.findById(gameId);

        if(gameEntityOptional.isEmpty()) {
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
    public Game getRandomGame() {
        List<GameEntity> allGames = gameRepository.findAll();
        return mapGameEntityToGame(allGames.get(randomnessService.getRandomInt(allGames.size())));
    }
}
