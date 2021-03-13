package de.davidbohl.drinkinggameapi.controller;

import de.davidbohl.drinkinggameapi.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController implements TagControllerApi {

    @Autowired
    GameService gameService;

    @Override
    public List<String> getTags(String searchPhrase) {
        return gameService.getAllTags(searchPhrase);
    }
}
