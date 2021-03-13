package de.davidbohl.drinkinggameapi.controller;

import de.davidbohl.drinkinggameapi.service.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class TagControllerTest {

    @MockBean
    GameService gameServiceMock;

    @Autowired
    TagController tagController;

    @Test
    void getTags_should_return_all_tags_from_gameService() {

        checkIfGetAllTagsReturnsSameListSizeAsListFromService(null);
    }

    @Test
    void getTags_should_return_all_tags_from_gameService_with_searchPhrase() {

        checkIfGetAllTagsReturnsSameListSizeAsListFromService("Tag");
    }

    private void checkIfGetAllTagsReturnsSameListSizeAsListFromService(String searchPhrase) {
        List<String> tagsFromService = new ArrayList<>();
        tagsFromService.add("Tag 1");
        tagsFromService.add("Tag 2");
        tagsFromService.add("Tag 3");
        tagsFromService.add("Tag 4");

        when(gameServiceMock.getAllTags(searchPhrase)).thenReturn(tagsFromService);

        List<String> tags = tagController.getTags(searchPhrase);

        Assertions.assertEquals(tagsFromService.size(), tags.size());
    }
}
