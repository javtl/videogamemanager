package com.videogamemanager.videogamemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.models.dto.GameStatsDto;
import com.videogamemanager.videogamemanager.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
@AutoConfigureMockMvc(addFilters = false)
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
        gameDto.setTitle("Mario Bros");
        gameDto.setGenre("Adventure");
        gameDto.setReleaseYear(1985);
        gameDto.setAge(3);
        gameDto.setCompleted(true);
    }

    // --- TESTS DE ÉXITO (HAPPY PATH) ---

    @Test
    void getAll_ShouldReturnOk() throws Exception {
        when(gameService.getAllGames(any(Pageable.class))).thenReturn(new PageImpl<>(List.of(gameDto)));

        mockMvc.perform(get("/api/games/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Mario Bros"));
    }

    @Test
    void saveGame_ShouldReturnCreated() throws Exception {
        when(gameService.saveGame(any(GameDto.class))).thenReturn(gameDto);

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Mario Bros"));
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
                .andExpect(jsonPath("$.title").value("Mario Bros"));
    }

    @Test
    void search_ShouldReturnPagedResults() throws Exception {
        when(gameService.findGamesFiltered(any(GameDto.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(gameDto)));

        mockMvc.perform(post("/api/games/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Mario Bros"));
    }

    @Test
    void getStats_ShouldReturnStatsList() throws Exception {
        GameStatsDto stats = new GameStatsDto();
        stats.setGenre("Adventure");
        stats.setTotalGames(1L);

        // Corregido: La ruta en el Controller es /api/games/stats, no /stats/genre
        when(gameService.getStatsByGenre()).thenReturn(List.of(stats));

        mockMvc.perform(get("/api/games/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].genre").value("Adventure"));
    }

    // --- TESTS DE COBERTURA DE ERRORES (PARA SUBIR EL % EN SONAR) ---

    @Test
    void saveGame_ShouldReturnBadRequest_WhenTitleIsNull() throws Exception {
        gameDto.setTitle(null); // Provocamos fallo de @Valid

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andExpect(status().isBadRequest());

        // Verificamos que el servicio ni siquiera se llega a llamar
        verifyNoInteractions(gameService);
    }

    @Test
    void deleteGame_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        // Simulamos que el service lanza una excepción personalizada de "No encontrado"
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND))
                .when(gameService).deleteGame("invalid-id");

        mockMvc.perform(delete("/api/games/invalid-id"))
                .andExpect(status().isNotFound());
    }
}