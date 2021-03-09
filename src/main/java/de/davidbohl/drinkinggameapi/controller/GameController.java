package de.davidbohl.drinkinggameapi.controller;

import de.davidbohl.drinkinggameapi.controller.model.Error;
import de.davidbohl.drinkinggameapi.controller.model.Game;
import de.davidbohl.drinkinggameapi.service.GameService;
import de.davidbohl.drinkinggameapi.service.exception.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
public class GameController implements GameControllerApi {

    @Autowired
    private GameService gameService;

    @GetMapping
    @Override
    public List<Game> getGames() {
        return gameService.getAllGames();
    }

    @PostMapping
    @Override
    public Game postGame(@RequestBody Game game) {
        return gameService.createNewGame(game);
    }

    @PutMapping("/{gameId}")
    @Override
    public Game putGame(@RequestParam String gameId, @RequestBody Game game) {
        return gameService.updateGame(gameId, game);
    }

    @Override
    @GetMapping("/{gameId}")
    public Game getGame(@RequestParam String gameId) {
        return gameService.getGameById(gameId);
    }

    @Override
    @GetMapping("/random")
    public Game getRandomGame() {
        return gameService.getRandomGame();
    }

    @Override
    @DeleteMapping("/{gameId}")
    public void deleteGame(@RequestParam String gameId) {
        gameService.deleteGameById(gameId);
    }



    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<Error> handleGameNotFoundException(GameNotFoundException exception) {
        return new ResponseEntity<>(new Error(404000001, exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
