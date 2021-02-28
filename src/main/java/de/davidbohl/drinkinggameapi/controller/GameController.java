package de.davidbohl.drinkinggameapi.controller;

import de.davidbohl.drinkinggameapi.controller.model.Game;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {

    @GetMapping
    public List<Game> getGames() {
        List<Game> games = new ArrayList<>();
        Game game = new Game();
        game.setTitle("Beerpong");
        game.setDescription("Spielt eine Runde Beerpong");

        games.add(game);

        return games;
    }
}
