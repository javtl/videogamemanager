package com.videogamemanager.videogamemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.services.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @MockitoBean
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllGames() throws Exception{
        Game game = new Game();
        game.setId("1");
        game.setTitle("Uncharted");
        game.setGenre("Action");
        game.setReleaseYear(2000);
        game.setAge(6);
        game.setCompleted(false);

        when(gameService.getAllGames()).thenReturn(List.of(game));

        mockMvc.perform(get("/api/games/all"))
                .andExpect(status().isOk());

    }

    @Test
    void testSaveGames() throws Exception{
        Game game = new Game();
        game.setId("1");
        game.setTitle("Uncharted");
        game.setGenre("Action");
        game.setReleaseYear(2000);
        game.setAge(6);
        game.setCompleted(false);

        when(gameService.saveGame(any(Game.class))).thenReturn(game);

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(game)))
                .andExpect(status().isOk());

    }
}
