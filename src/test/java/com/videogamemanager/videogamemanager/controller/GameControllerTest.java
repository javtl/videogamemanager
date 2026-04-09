/* package com.videogamemanager.videogamemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.models.dto.GameStatsDto;
import com.videogamemanager.videogamemanager.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.containsString;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean // API oficial para Spring Boot 3.4+
    private GameService gameService;

    @Autowired
    private ObjectMapper objectMapper;

    private GameDto gameDto;

    @BeforeEach
    void setUp() {
        gameDto = new GameDto();
        gameDto.setTitle("Mario Bros");
        gameDto.setGenre("Adventure");
        gameDto.setReleaseYear(1985);
        gameDto.setAge(3);
        gameDto.setCompleted(true);
    }

    @Test
    void getAll_ShouldReturnOk() throws Exception {
        when(gameService.getAllGames(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(gameDto)));

        mockMvc.perform(get("/api/games/all"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Mario Bros")));
    }

    @Test
    void getStatsByGenre_ShouldReturnStatsList() throws Exception {
        // 1. Creamos el objeto con datos explícitos
        GameStatsDto stats = new GameStatsDto();
        stats.setGenre("Adventure");
        stats.setTotalGames(1L);
        stats.setAverageAge(10.0);

        // 2. Mockeo ultra-seguro
        // Usamos Mockito.doReturn para evitar problemas de tipos genéricos
        Mockito.doReturn(List.of(stats)).when(gameService).getStatsByGenre();

        // 3. Ejecución
        mockMvc.perform(get("/api/games/stats/genre")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) // <--- MIRA LA CONSOLA AQUÍ
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].genre").value("Adventure"));
    }

    @Test
    void saveGame_ShouldReturnCreated() throws Exception {
        when(gameService.saveGame(any(GameDto.class))).thenReturn(gameDto);

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("Mario Bros")));
    }

    @Test
    void deleteGame_ShouldReturnOk() throws Exception {
        doNothing().when(gameService).deleteGame(anyString());

        mockMvc.perform(delete("/api/games/id-123"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Videojuego eliminado con éxito")));
    }

    @Test
    void updateGame_ShouldReturnOk() throws Exception {
        when(gameService.updateGame(anyString(), any(GameDto.class))).thenReturn(gameDto);

        mockMvc.perform(put("/api/games/id-123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Mario Bros")));
    }
}


 */