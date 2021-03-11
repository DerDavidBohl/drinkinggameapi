package de.davidbohl.drinkinggameapi.controller;

import de.davidbohl.drinkinggameapi.controller.model.Game;
import de.davidbohl.drinkinggameapi.service.GameService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
public class GameControllerTest {

    public static final String GAME1_TITLE = "Test Game 1!";
    public static final String GAME1_DESCRIPTION = "This is Test Game 1.";
    public static final String GAME2_DESCRIPTION = "This is Test Game 2.";
    public static final String GAME2_TITLE = "Test Game 2!";

    @MockBean
    GameService gameServiceMock;

    @Autowired
    GameController gameController;

    @Test
    void GetGames_should_return_all_games_from_GameService() {
        List<Game> returnedGames = new ArrayList<>();
        Game g1 = new Game();
        g1.setDescription(GAME1_DESCRIPTION);
        g1.setTitle(GAME1_TITLE);
        returnedGames.add(g1);
        Game g2 = new Game();
        g2.setDescription(GAME2_DESCRIPTION);
        g2.setTitle(GAME2_TITLE);
        returnedGames.add(g2);

        when(gameServiceMock.getAllGames(null)).thenReturn(returnedGames);

        List<Game> games = gameController.getGames(null).getBody();

        Assertions.assertEquals(2, games.size());
        Assertions.assertEquals(GAME1_TITLE, games.get(0).getTitle());
        Assertions.assertEquals(GAME1_DESCRIPTION, games.get(0).getDescription());
        Assertions.assertEquals(GAME2_TITLE, games.get(1).getTitle());
        Assertions.assertEquals(GAME2_DESCRIPTION, games.get(1).getDescription());
    }

    @Test
    void PostGame_should_return_created_game() {
        Game inputGame = new Game(null, "Neues Spiel", "Das ist mein neues Spiel");
        Game createdGame = new Game(new ObjectId().toString(), inputGame.getTitle(), inputGame.getDescription());

        when(gameServiceMock.createNewGame(inputGame)).thenReturn(createdGame);
        Game answer = gameController.postGame(inputGame).getBody();

        Assertions.assertEquals(createdGame, answer);
    }

    @Test
    void putGame_should_return_updated_game() {
        String gameId = new ObjectId().toString();
        Game inputGame = new Game(gameId, "Neues Spiel", "Das ist mein neues Spiel");
        Game updatedGame = new Game(gameId, inputGame.getTitle(), inputGame.getDescription());

        when(gameServiceMock.updateGame(gameId, inputGame)).thenReturn(updatedGame);
        Game answer = gameController.putGame(gameId, inputGame).getBody();

        Assertions.assertEquals(updatedGame, answer);
    }

    @Test
    void getGameById_should_return_Game_from_Service() {
        String gameId = new ObjectId().toString();

        Game gameFromService = new Game(gameId, "Mein Spiel", "Das ist mein Spiel!");

        when(gameServiceMock.getGameById(gameId)).thenReturn(gameFromService);

        Game answer = gameController.getGame(gameId).getBody();

        Assertions.assertEquals(gameFromService, answer);
    }

    @Test
    void deleteGameById_should_call_delete_from_service() {
        String gameId = new ObjectId().toString();

        gameController.deleteGame(gameId);

        verify(gameServiceMock, times(1)).deleteGameById(gameId);
    }

    @Test
    void getRandomGame_should_call_getRandom_from_service() {

        Game gameFromService = new Game(new ObjectId().toString(), "Mein Spiel", "Das ist mein Spiel!");

        when(gameServiceMock.getRandomGame(null)).thenReturn(gameFromService);

        Game answer = gameController.getRandomGame(null).getBody();

        Assertions.assertEquals(gameFromService, answer);
    }

    @Test
    void getRandomGame_should_call_getRandom_from_service_with_filterTags() {

        List<String> tags = new ArrayList<>();

        tags.add("Test Tag 1");
        tags.add("Test Tag 2");
        tags.add("Test Tag 3");
        tags.add("Test Tag 4");

        Game gameFromService = new Game(new ObjectId().toString(), "Mein Spiel", "Das ist mein Spiel!", tags);

        when(gameServiceMock.getRandomGame(tags)).thenReturn(gameFromService);

        Game answer = gameController.getRandomGame(tags).getBody();

        Assertions.assertEquals(gameFromService, answer);
    }

    @Test
    void getGames_should_return_filtered_list_when_tags_are_given() {

        List<String> filterTags = new ArrayList<>();
        filterTags.add("Spaß");

        List<Game> gamesFromService = new ArrayList<>();
        Game game1 = new Game(new ObjectId().toString(), "Title1", "Spaßiges Spiel", filterTags);
        Game game2 = new Game(new ObjectId().toString(), "Title2", "Spaßiges Spiel", filterTags);
        gamesFromService.add(game1);
        gamesFromService.add(game2);

        when(gameServiceMock.getAllGames(filterTags)).thenReturn(gamesFromService);

        List<Game> answer = gameController.getGames(filterTags).getBody();

        Assertions.assertEquals(2, answer.size());
    }
}
