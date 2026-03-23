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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
        gameDto.setReleaseYear(1985);
        gameDto.setAge(3);
        gameDto.setCompleted(true);
    }

    @Test
    void getAll_ShouldReturnOk() throws Exception {
        when(gameService.getAllGames(pageable)).thenReturn(List.of(gameDto));

        mockMvc.perform(get("/api/games/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Mario Bros"));
    }

    @Test
    void saveGame_ShouldReturnCreated() throws Exception {
        when(gameService.saveGame(any())).thenReturn(gameDto);

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Mario Bros"));
    }

    @Test
    void deleteGame_ShouldReturnOk() throws Exception {
        doNothing().when(gameService).deleteGame("1");

        mockMvc.perform(delete("/api/games/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Videojuego eliminado con éxito."));
    }

    @Test
    void getByTitle_ShouldReturnList() throws Exception {
        when(gameService.findByTitle(anyString())).thenReturn(List.of(gameDto));

        mockMvc.perform(get("/api/games/title")
                        .param("title", "Mario"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Mario Bros")); // El valor real que devuelve el DTO
    }

    @Test
    void getByGenre_ShouldReturnList() throws Exception {
        when(gameService.findByGenre("Adventure")).thenReturn(List.of(gameDto));

        mockMvc.perform(get("/api/games/genre/Adventure"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].genre").value("Adventure"));
    }

    @Test
    void updateGame_ShouldReturnOk() throws Exception {
        // 1. Configuramos el comportamiento del mock
        // Cuando llamen al servicio con cualquier ID y cualquier DTO, devolvemos nuestro gameDto de prueba
        when(gameService.updateGame(anyString(), any(GameDto.class))).thenReturn(gameDto);

        // 2. Ejecutamos la petición PUT
        mockMvc.perform(put("/api/games/1") // El ID en la URL
                        .contentType(MediaType.APPLICATION_JSON) // Decimos que enviamos un JSON
                        .content(objectMapper.writeValueAsString(gameDto))) // Convertimos el objeto a JSON
                .andExpect(status().isOk()) // Esperamos un 200 OK
                .andExpect(jsonPath("$.title").value("Mario Bros")); // Verificamos que el JSON de vuelta es correcto
    }

    @Test
    void saveGame_ShouldReturnBadRequest_WhenTitleIsTooShort() throws Exception {
        gameDto.setTitle("A"); // Solo 1 caracter, fallará @Size(min=2)

        mockMvc.perform(post("/api/games")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(gameDto)))
                .andExpect(status().isBadRequest()); // Esperamos un 400
    }
}