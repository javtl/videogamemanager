package com.videogamemanager.videogamemanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.models.dto.GameStatsDto;
import com.videogamemanager.videogamemanager.security.JwtUtils;
import com.videogamemanager.videogamemanager.services.GameService;
import com.videogamemanager.videogamemanager.utils.AppConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    @WebMvcTest(GameController.class)
    @AutoConfigureMockMvc(addFilters = false)
    class GameControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private GameService gameService;

        @MockitoBean
        private JwtUtils jwtUtils;

        @MockitoBean
        private UserDetailsService userDetailsService;

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
        void getAll_ShouldReturnPagedGames() throws Exception {
            when(gameService.getAllGames(any(Pageable.class)))
                    .thenReturn(new PageImpl<>(List.of(gameDto)));

            mockMvc.perform(get("/api/games/all"))
                    .andExpect(status().isOk())
                    // Verificamos la estructura de página de Spring Data
                    .andExpect(jsonPath("$.content[0].title", is("Mario Bros")))
                    .andExpect(jsonPath("$.totalElements", is(1)));
        }

        @Test
        void getStatsByGenre_ShouldReturnStatsList() throws Exception {
            GameStatsDto stats = new GameStatsDto("Adventure", 1L, 10.0, null);

            // Forzamos el retorno con Mockito de forma limpia
            when(gameService.getStatsByGenre()).thenReturn(List.of(stats));

            mockMvc.perform(get("/api/games/stats")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print()) // Esto te dirá el error real en la consola si vuelve a fallar
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
                    .andExpect(jsonPath("$.title", is("Mario Bros")));
        }

        @Test
        void deleteGame_ShouldReturnOk() throws Exception {
            doNothing().when(gameService).deleteGame(anyString());

            mockMvc.perform(delete("/api/games/id-123"))
                    .andExpect(status().isOk())
                    // Comparamos directamente contra la constante, así no hay fallo de tildes
                    .andExpect(content().string(AppConstants.MSG_GAME_DELETE));
        }

        @Test
        void updateGame_ShouldReturnOk() throws Exception {
            when(gameService.updateGame(anyString(), any(GameDto.class))).thenReturn(gameDto);

            mockMvc.perform(put("/api/games/id-123")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(gameDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title", is("Mario Bros")))
                    .andExpect(jsonPath("$.genre", is("Adventure")));
        }

        @Test
        void search_ShouldReturnFilteredPage() throws Exception {
            Page<GameDto> page = new PageImpl<>(List.of(gameDto));
            when(gameService.findGamesFiltered(any(), any())).thenReturn(page);

            mockMvc.perform(post("/api/games/search")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(gameDto)))
                    .andExpect(status().isOk());
        }
    }