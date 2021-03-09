package de.davidbohl.drinkinggameapi.controller;

import de.davidbohl.drinkinggameapi.controller.model.Game;
import de.davidbohl.drinkinggameapi.service.GameService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

        when(gameServiceMock.getAllGames()).thenReturn(returnedGames);

        var games = gameController.getGames();

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
        Game answer = gameController.postGame(inputGame);

        Assertions.assertEquals(createdGame, answer);
    }

    @Test
    void putGame_should_return_updated_game() {
        String gameId = new ObjectId().toString();
        Game inputGame = new Game(gameId, "Neues Spiel", "Das ist mein neues Spiel");
        Game updatedGame = new Game(gameId, inputGame.getTitle(), inputGame.getDescription());

        when(gameServiceMock.updateGame(gameId, inputGame)).thenReturn(updatedGame);
        Game answer = gameController.putGame(gameId, inputGame);

        Assertions.assertEquals(updatedGame, answer);
    }

    @Test
    void getGameById_should_return_Game_from_Service() {
        String gameId = new ObjectId().toString();

        Game gameFromService = new Game(gameId, "Mein Spiel", "Das ist mein Spiel!");

        when(gameServiceMock.getGameById(gameId)).thenReturn(gameFromService);

        Game answer = gameController.getGame(gameId);

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

        when(gameServiceMock.getRandomGame()).thenReturn(gameFromService);

        Game answer = gameController.getRandomGame();

        Assertions.assertEquals(gameFromService, answer);
    }
}
