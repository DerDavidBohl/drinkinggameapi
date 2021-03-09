package de.davidbohl.drinkinggameapi.service;

import de.davidbohl.drinkinggameapi.controller.model.Game;
import de.davidbohl.drinkinggameapi.repository.GameRepository;
import de.davidbohl.drinkinggameapi.repository.entity.GameEntity;
import de.davidbohl.drinkinggameapi.service.exception.GameNotFoundException;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GameServiceImplTest {

    @MockBean
    GameRepository gameRepositoryMock;

    @MockBean
    RandomnessService randomnessService;

    @Autowired
    GameService gameService;

    @Test
    void GetAllGames_should_return_games_from_repo() {
        List<GameEntity> returnedGameEntityList = new ArrayList<>();

        GameEntity ge1 = new GameEntity();
        ge1.setTitle("Game 1 !!!");
        ge1.setDescription("Das ist Game 1!!!");
        ge1.setId(new ObjectId().toString());
        GameEntity ge2 = new GameEntity();
        ge2.setTitle("Game 2 !!!");
        ge2.setDescription("Das ist Game 2!!!");
        ge2.setId(new ObjectId().toString());

        returnedGameEntityList.add(ge1);
        returnedGameEntityList.add(ge2);

        when(gameRepositoryMock.findAll()).thenReturn(returnedGameEntityList);

        List<Game> games = gameService.getAllGames();

        Assertions.assertEquals(2, games.size());
        Assertions.assertTrue(games.stream().anyMatch(game -> game.getTitle() == ge1.getTitle()));
        Assertions.assertTrue(games.stream().anyMatch(game -> game.getDescription() == ge1.getDescription()));
    }

    @Test
    void CreateNewGame_should_return_Game_with_properties_from_Repository() {
        GameEntity createdGameFromRepository = new GameEntity(new ObjectId().toString(), "Created Game", "My very cool description...");
        Game givenGame = new Game(null, createdGameFromRepository.getTitle(), createdGameFromRepository.getDescription());

        when(gameRepositoryMock.insert(any(GameEntity.class))).thenReturn(createdGameFromRepository);

        Game returnedGame = gameService.createNewGame(givenGame);

        Assertions.assertEquals(createdGameFromRepository.getId(), returnedGame.getId());

    }

    @Test
    void updateGame_should_return_updated_Game_from_Repository() {
        String gameId = new ObjectId().toString();
        GameEntity updatedGameFromRepository = new GameEntity(gameId, "Created Game", "My very cool description...");
        Game givenGame = new Game(gameId, updatedGameFromRepository.getTitle(), updatedGameFromRepository.getDescription());

        when(gameRepositoryMock.save(updatedGameFromRepository)).thenReturn(updatedGameFromRepository);

        when(gameRepositoryMock.findById(gameId)).thenReturn(Optional.of(updatedGameFromRepository));
        Game returnedGame = gameService.updateGame(gameId, givenGame);

        Assertions.assertEquals(updatedGameFromRepository.getId(), returnedGame.getId());
        Assertions.assertEquals(updatedGameFromRepository.getTitle(), returnedGame.getTitle());
        Assertions.assertEquals(updatedGameFromRepository.getDescription(), returnedGame.getDescription());
    }

    @Test
    void updateGame_should_throw_GameNotFoundException_when_repository_cant_find_game() {
        Assertions.assertThrows(GameNotFoundException.class, () -> {
            String gameId = new ObjectId().toString();
            GameEntity updatedGameFromRepository = new GameEntity(gameId, "Created Game", "My very cool description...");
            Game givenGame = new Game(gameId, updatedGameFromRepository.getTitle(), updatedGameFromRepository.getDescription());

            when(gameRepositoryMock.findById(gameId)).thenReturn(Optional.empty());

            gameService.updateGame(gameId, givenGame);
        });
    }

    @Test
    void getGameById_should_return_game_from_Repository() {
        String gameId = new ObjectId().toString();
        GameEntity foundGameFromRepository = new GameEntity(gameId, "Created Game", "My very cool description...");

        when(gameRepositoryMock.findById(gameId)).thenReturn(Optional.of(foundGameFromRepository));

        Game gameById = gameService.getGameById(gameId);

        Assertions.assertEquals(foundGameFromRepository.getId(), gameById.getId());
        Assertions.assertEquals(foundGameFromRepository.getTitle(), gameById.getTitle());
        Assertions.assertEquals(foundGameFromRepository.getDescription(), gameById.getDescription());
    }

    @Test
    void getGameById_should_throw_GameNotFoundException_when_game_can_not_be_found() {
        Assertions.assertThrows(GameNotFoundException.class, () -> {
            String gameId = new ObjectId().toString();

            when(gameRepositoryMock.findById(gameId)).thenReturn(Optional.empty());

            gameService.getGameById(gameId);
        });
    }

    @Test
    void deleteGameById_should_call_delete_in_repository() {
        String gameId = new ObjectId().toString();

        when(gameRepositoryMock.findById(gameId)).thenReturn(Optional.of(new GameEntity()));
        gameService.deleteGameById(gameId);

        verify(gameRepositoryMock, times(1)).deleteById(gameId);
    }

    @Test
    void deleteGameById_should_throw_GameNotFoundException_when_game_can_not_be_found() {
        Assertions.assertThrows(GameNotFoundException.class, () -> {
            String gameId = new ObjectId().toString();

            when(gameRepositoryMock.findById(gameId)).thenReturn(Optional.empty());

            gameService.deleteGameById(gameId);
        });
    }

    @Test
    void getRandomGame_should_return_Game_from_position_provided_by_RandomnessService() {
        List<GameEntity> allGameEntities = new ArrayList<>();

        GameEntity ge1 = new GameEntity(new ObjectId().toString(), "G1", "Game 1");
        GameEntity ge2 = new GameEntity(new ObjectId().toString(), "G2", "Game 2");
        GameEntity ge3 = new GameEntity(new ObjectId().toString(), "G3", "Game 3");

        allGameEntities.add(ge1);
        allGameEntities.add(ge2);
        allGameEntities.add(ge3);

        when(gameRepositoryMock.findAll()).thenReturn(allGameEntities);
        when(randomnessService.getRandomInt(allGameEntities.size())).thenReturn(1);

        Game gameFromService = gameService.getRandomGame();

        Assertions.assertEquals(ge2.getId(), gameFromService.getId());
    }
}
