package de.davidbohl.drinkinggameapi.service;

import de.davidbohl.drinkinggameapi.controller.model.Game;

import java.util.List;

public interface GameService {
    List<Game> getAllGames();

    Game createNewGame(Game inputGame);

    Game updateGame(String gameId, Game game);

    Game getGameById(String gameId);

    void deleteGameById(String gameId);

    Game getRandomGame();
}
