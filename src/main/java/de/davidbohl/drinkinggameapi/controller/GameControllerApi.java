package de.davidbohl.drinkinggameapi.controller;

import de.davidbohl.drinkinggameapi.controller.model.Game;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/games")
public interface GameControllerApi {

    @GetMapping
    ResponseEntity<List<Game>> getGames(@RequestParam(required = false) List<String> tags);

    @PostMapping
    ResponseEntity<Game> postGame(@RequestBody Game inputGame);

    @PutMapping("/{gameId}")
    ResponseEntity<Game> putGame(@PathVariable String gameId, @RequestBody Game game);

    @GetMapping("/{gameId}")
    ResponseEntity<Game> getGame(@PathVariable String gameId);

    @GetMapping("/random")
    ResponseEntity<Game> getRandomGame(@RequestParam(required = false) List<String> tags);

    @DeleteMapping("/{gameId}")
    ResponseEntity deleteGame(@PathVariable String gameId);
}
