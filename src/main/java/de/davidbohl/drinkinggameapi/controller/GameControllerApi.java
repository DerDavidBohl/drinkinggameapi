package de.davidbohl.drinkinggameapi.controller;

import de.davidbohl.drinkinggameapi.controller.model.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface GameControllerApi {

    @GetMapping
    ResponseEntity<List<Game>> getGames(@RequestParam(required = false) List<String> tags);

    @PostMapping
    ResponseEntity<Game> postGame(@RequestBody Game inputGame);

    @PutMapping("/{gameId}")
    ResponseEntity<Game> putGame(@RequestParam String gameId, @RequestBody Game game);

    @GetMapping("/{gameId")
    ResponseEntity<Game> getGame(@RequestParam String gameId);

    @GetMapping("/random")
    ResponseEntity<Game> getRandomGame(@RequestParam(required = false) List<String> tags);

    @DeleteMapping("/{gameId}")
    ResponseEntity deleteGame(@RequestParam String gameId);
}
