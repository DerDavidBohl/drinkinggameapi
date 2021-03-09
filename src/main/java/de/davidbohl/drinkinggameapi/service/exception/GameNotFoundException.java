package de.davidbohl.drinkinggameapi.service.exception;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String gameId) {
        super("Could not find Game with id " + gameId + ".");
    }
}
