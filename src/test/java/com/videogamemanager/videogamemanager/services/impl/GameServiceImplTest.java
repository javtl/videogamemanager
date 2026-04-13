package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.mapper.GameMapper;
import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.models.dto.GameStatsDto;
import com.videogamemanager.videogamemanager.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*; // <--- Esta es la que te falta para el verify

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // <--- Esto evita el error PotentialStubbingProblem
class GameServiceImplTest {

    @Mock
    private GameRepository repository;

    @Mock
    private GameMapper mapper;

    @Mock
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private GameServiceImpl gameService;

    private Game game;
    private GameDto gameDto;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setId("1");
        game.setTitle("Zelda");
        game.setActive(true);

        gameDto = new GameDto();
        gameDto.setTitle("Zelda");
    }

    @Test
    void getStatsByGenre_ShouldReturnStats() {
        // Datos de prueba
        GameStatsDto stats = new GameStatsDto();
        stats.setGenre("Adventure");

        AggregationResults<GameStatsDto> results = new AggregationResults<>(
                List.of(stats),
                new org.bson.Document()
        );

        // USAMOS doReturn para saltarnos la validación estricta de tipos
        Mockito.doReturn(results).when(mongoTemplate).aggregate(
                any(Aggregation.class),
                anyString(),
                any(Class.class)
        );

        List<GameStatsDto> result = gameService.getStatsByGenre();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Adventure", result.get(0).getGenre());
    }

    @Test
    void getAllGames_ShouldReturnPage() {
        Page<Game> page = new PageImpl<>(List.of(game));
        Mockito.when(repository.findAll(any(Pageable.class))).thenReturn(page);
        Mockito.when(mapper.toDTO(any())).thenReturn(gameDto);

        Page<GameDto> result = gameService.getAllGames(PageRequest.of(0, 10));

        assertNotNull(result);
        verify(repository).findAll(any(Pageable.class));
    }

    @Test
    void saveGame_Success() {
        Mockito.when(mapper.toEntity(any())).thenReturn(game);
        Mockito.when(repository.save(any())).thenReturn(game);
        Mockito.when(mapper.toDTO(any())).thenReturn(gameDto);

        GameDto saved = gameService.saveGame(gameDto);

        assertNotNull(saved);
        assertEquals("Zelda", saved.getTitle());
    }

    @Test
    void updateGame_Success() {
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.of(game));
        Mockito.when(repository.save(any())).thenReturn(game);
        Mockito.when(mapper.toDTO(any())).thenReturn(gameDto);

        GameDto updated = gameService.updateGame("1", gameDto);

        assertNotNull(updated);
        verify(repository).save(any());
    }

    @Test
    void deleteGame_Success() {
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.of(game));
        Mockito.when(repository.save(any())).thenReturn(game);

        gameService.deleteGame("1");

        assertFalse(game.isActive());
    }

    @Test
    void findGamesFiltered_ShouldReturnPage() {
        Page<Game> page = new PageImpl<>(List.of(game));
        Mockito.when(mapper.toEntity(any())).thenReturn(game);
        Mockito.when(repository.findAll(any(Example.class), any(Pageable.class))).thenReturn(page);
        Mockito.when(mapper.toDTO(any())).thenReturn(gameDto);

        Page<GameDto> result = gameService.findGamesFiltered(gameDto, PageRequest.of(0, 10));

        assertNotNull(result);
    }
}