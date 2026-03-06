package com.videogamemanager.videogamemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    @Autowired
    private ObjectMapper objectMapper;

    private GameDto gameDto;

    @BeforeEach
    void setUp() {
        gameDto = new GameDto();
        gameDto.setTitle("Mario");
        gameDto.setGenre("Plataformas");
    }

    @Test
    void getAll_ShouldReturnOk() throws Exception {
        when(gameService.getAllGames()).thenReturn(List.of(gameDto));

        mockMvc.perform(get("/api/games/all"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].title").value("Mario"));
    }

    @Test
    void saveGame_ShouldReturnCreated() throws Exception {
        when(gameService.saveGame(any())).thenReturn(gameDto);

        mockMvc.perform((org.springframework.test.web.servlet.RequestBuilder) post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.valueOf(objectMapper.writeValueAsString(gameDto))))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.title").value("Mario"));
    }

    @Test
    void deleteGame_ShouldReturnOk() throws Exception {
        doNothing().when(gameService).deleteGame("1");

        mockMvc.perform(delete("/api/games/1"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Videojuego eliminado con éxito."));
    }

    @Test
    void getByTitle_ShouldReturnList() throws Exception {
        when(gameService.findByTitle("Mario")).thenReturn(List.of(gameDto));

        mockMvc.perform(get("/api/games/title").param("title", "Mario"))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$[0].title").value("Mario"));
    }
}
