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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

        GameEntity ge1 = getGameEntity("Game 1!!!", "Das ist Game 1!!!", createTagList("test", "Bla", "third"));
        GameEntity ge2 = getGameEntity("Game 2!!!", "Das ist Game 2!!!", createTagList("test", "Bla", "third"));

        returnedGameEntityList.add(ge1);
        returnedGameEntityList.add(ge2);

        when(gameRepositoryMock.findAll()).thenReturn(returnedGameEntityList);

        List<Game> games = gameService.getAllGames(null);

        assertEquals(2, games.size());
        assertTrue(games.stream().anyMatch(game -> game.getTitle() == ge1.getTitle()));
        assertTrue(games.stream().anyMatch(game -> game.getDescription() == ge1.getDescription()));
    }

    @Test
    void CreateNewGame_should_return_Game_with_properties_from_Repository() {
        GameEntity createdGameFromRepository = getGameEntity("Created Game", "My very cool description...", createTagList("test", "Bla", "third"));
        Game givenGame = new Game(null, createdGameFromRepository.getTitle(), createdGameFromRepository.getDescription());

        when(gameRepositoryMock.insert(any(GameEntity.class))).thenReturn(createdGameFromRepository);

        Game returnedGame = gameService.createNewGame(givenGame);

        assertEquals(createdGameFromRepository.getId(), returnedGame.getId());

    }

    private GameEntity getGameEntity(String title, String description, List<String> tagList) {
        return new GameEntity(new ObjectId().toString(), title, description, tagList);
    }

    @Test
    void updateGame_should_return_updated_Game_from_Repository() {
        String gameId = new ObjectId().toString();
        GameEntity updatedGameFromRepository = getGameEntity("Created Game", "My very cool description...", createTagList("test", "Bla", "third"));
        Game givenGame = new Game(gameId, updatedGameFromRepository.getTitle(), updatedGameFromRepository.getDescription(), createTagList("test", "Bla", "third"));

        when(gameRepositoryMock.save(updatedGameFromRepository)).thenReturn(updatedGameFromRepository);

        when(gameRepositoryMock.findById(gameId)).thenReturn(Optional.of(updatedGameFromRepository));
        Game returnedGame = gameService.updateGame(gameId, givenGame);

        assertEquals(updatedGameFromRepository.getId(), returnedGame.getId());
        assertEquals(updatedGameFromRepository.getTitle(), returnedGame.getTitle());
        assertEquals(updatedGameFromRepository.getDescription(), returnedGame.getDescription());
    }

    @Test
    void updateGame_should_throw_GameNotFoundException_when_repository_cant_find_game() {
        Assertions.assertThrows(GameNotFoundException.class, () -> {
            String gameId = new ObjectId().toString();
            GameEntity updatedGameFromRepository = getGameEntity("Created Game", "My very cool description...", createTagList("test", "Bla", "third"));
            Game givenGame = new Game(gameId, updatedGameFromRepository.getTitle(), updatedGameFromRepository.getDescription());

            when(gameRepositoryMock.findById(gameId)).thenReturn(Optional.empty());

            gameService.updateGame(gameId, givenGame);
        });
    }

    @Test
    void getGameById_should_return_game_from_Repository() {
        String gameId = new ObjectId().toString();
        GameEntity foundGameFromRepository = getGameEntity("Created Game", "My very cool description...", createTagList("test", "Bla", "third"));

        when(gameRepositoryMock.findById(gameId)).thenReturn(Optional.of(foundGameFromRepository));

        Game gameById = gameService.getGameById(gameId);

        assertEquals(foundGameFromRepository.getId(), gameById.getId());
        assertEquals(foundGameFromRepository.getTitle(), gameById.getTitle());
        assertEquals(foundGameFromRepository.getDescription(), gameById.getDescription());
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

        when(gameRepositoryMock.findById(gameId)).thenReturn(Optional.of(getGameEntity("Delete me", "please!", createTagList("test", "Bla", "third"))));
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

        GameEntity ge1 = getGameEntity("G1", "Game 1", createTagList("test", "Bla", "third"));
        GameEntity ge2 = getGameEntity("G2", "Game 2", createTagList("test", "Bla", "third"));
        GameEntity ge3 = getGameEntity("G3", "Game 3", createTagList("test", "Bla", "third"));

        allGameEntities.add(ge1);
        allGameEntities.add(ge2);
        allGameEntities.add(ge3);

        when(gameRepositoryMock.findAll()).thenReturn(allGameEntities);
        when(randomnessService.getRandomInt(allGameEntities.size())).thenReturn(1);

        Game gameFromService = gameService.getRandomGame(null);

        assertEquals(ge2.getId(), gameFromService.getId());
    }

    @Test
    void getRandomGame_should_return_Game_from_position_provided_by_RandomnessService_and_filter() {
        List<GameEntity> allGameEntities = new ArrayList<>();

        GameEntity ge1 = getGameEntity("G1", "Game 1", createTagList("test", "Bla", "third"));
        GameEntity ge2 = getGameEntity("G2", "Game 2", createTagList("test", "Bla", "third"));
        GameEntity ge3 = getGameEntity("G3", "Game 3", createTagList("test", "Bla", "third"));

        allGameEntities.add(ge1);
        allGameEntities.add(ge2);
        allGameEntities.add(ge3);

        when(gameRepositoryMock.findByTagsIn(createTagList("test"))).thenReturn(allGameEntities);
        when(randomnessService.getRandomInt(allGameEntities.size())).thenReturn(1);

        Game gameFromService = gameService.getRandomGame(createTagList("test"));

        assertEquals(ge2.getId(), gameFromService.getId());
    }

    @Test
    void getAllGames_should_return_filtered_list_from_repository() {

        List<GameEntity> allGameEntities = new ArrayList<>();

        GameEntity ge1 = getGameEntity("G1", "Game 1", createTagList("test", "Bla", "third"));
        GameEntity ge2 = getGameEntity("G2", "Game 2", createTagList("test", "Bla", "third"));
        GameEntity ge3 = getGameEntity("G3", "Game 3", createTagList("test", "Bla", "third"));

        allGameEntities.add(ge1);
        allGameEntities.add(ge2);
        allGameEntities.add(ge3);

        when(gameRepositoryMock.findByTagsIn(createTagList("test"))).thenReturn(allGameEntities);

        List<Game> answer = gameService.getAllGames(createTagList("test"));

        assertEquals(3, answer.size());
        assertTrue(answer.stream().allMatch(game -> game.getTags().contains("test")));
    }
    private List<String> createTagList(String... tags) {
        return Arrays.asList(tags.clone());
    }
}
