package de.davidbohl.drinkinggameapi.controller;

import de.davidbohl.drinkinggameapi.controller.model.Error;
import de.davidbohl.drinkinggameapi.controller.model.Game;
import de.davidbohl.drinkinggameapi.service.GameService;
import de.davidbohl.drinkinggameapi.service.exception.GameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
public class GameController implements GameControllerApi {

    @Autowired
    private GameService gameService;

    @Override
    public ResponseEntity<List<Game>> getGames(List<String> tags) {
        return ResponseEntity.ok(gameService.getAllGames(tags));
    }

    @Override
    public ResponseEntity<Game> postGame(Game game) {
        return new ResponseEntity(gameService.createNewGame(game), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Game> putGame(String gameId, Game game) {
        return ResponseEntity.ok(gameService.updateGame(gameId, game));
    }

    @Override
    public ResponseEntity<Game> getGame(String gameId) {
        return ResponseEntity.ok(gameService.getGameById(gameId));
    }

    @Override
    public ResponseEntity<Game> getRandomGame(List<String> tags) {
        return ResponseEntity.ok(gameService.getRandomGame(tags));
    }

    @Override
    public ResponseEntity deleteGame(String gameId) {
        gameService.deleteGameById(gameId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }



    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<Error> handleGameNotFoundException(GameNotFoundException exception) {
        return new ResponseEntity<>(new Error(404000001, exception.getMessage()), HttpStatus.NOT_FOUND);
    }
}
