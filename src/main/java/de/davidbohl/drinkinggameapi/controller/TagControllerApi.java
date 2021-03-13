package de.davidbohl.drinkinggameapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/api/v1/tags")
public interface TagControllerApi {

    @GetMapping
    List<String> getTags(@RequestParam(required = false) String searchPhrase);

}
