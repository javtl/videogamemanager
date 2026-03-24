package com.videogamemanager.videogamemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.services.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl; // Importante para el Page
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        gameDto.setTitle("Mario Bros");
        gameDto.setGenre("Adventure");
        // gameDto.setReleaseYear(1985); // Asegúrate que este campo existe en tu DTO actual
        gameDto.setAge(3); // Nuestro querido Integer
        gameDto.setCompleted(true);
    }

    @Test
    void getAll_ShouldReturnOk() throws Exception {
        // Fix: any() para el parámetro de paginación y PageImpl para el retorno
        when(gameService.getAllGames(any())).thenReturn(new PageImpl<>(List.of(gameDto)));

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
        // En MongoDB el ID suele ser String, asegúrate que deleteGame recibe String
        doNothing().when(gameService).deleteGame(anyString());

        mockMvc.perform(delete("/api/games/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Videojuego eliminado con éxito."));
    }

    @Test
    void getByTitle_ShouldReturnPage() throws Exception {
        // 1. Configuramos el mock para el nuevo método unificado
        // 'any(GameDto.class)' es el filtro y 'any(Pageable.class)' es la paginación
        when(gameService.findGamesFiltered(any(GameDto.class), any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new org.springframework.data.domain.PageImpl<>(List.of(gameDto)));

        // 2. Ejecutamos la petición (suponiendo que tu controller aún tiene esta ruta)
        mockMvc.perform(get("/api/games/title")
                        .param("title", "Mario"))
                .andExpect(status().isOk())
                // IMPORTANTE: Al ser Page, los datos van dentro de "content"
                .andExpect(jsonPath("$.content[0].title").value("Mario Bros"));
    }

    @Test
    void updateGame_ShouldReturnOk() throws Exception {
        when(gameService.updateGame(anyString(), any(GameDto.class))).thenReturn(gameDto);

        mockMvc.perform(put("/api/games/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Mario Bros"));
    }
}