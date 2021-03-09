package de.davidbohl.drinkinggameapi.controller;

import de.davidbohl.drinkinggameapi.controller.model.Game;

import java.util.List;

public interface GameControllerApi {
    List<Game> getGames();

    Game postGame(Game inputGame);

    Game putGame(String gameId, Game game);

    Game getGame(String gameId);

    Game getRandomGame();

    void deleteGame(String gameId);
}
