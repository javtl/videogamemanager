package com.videogamemanager.videogamemanager.services.impl;


import com.videogamemanager.videogamemanager.mapper.GameMapper;
import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GameRepository repository;
    @Mock
    private GameMapper mapper;

    @InjectMocks
    private GameServiceImpl gameService;

    private Game game;
    private GameDto gameDto;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setId("1");
        game.setTitle("Zelda");
        game.setActive(true);

        gameDto = new GameDto();
        gameDto.setTitle("Zelda");

        pageable = PageRequest.of(0, 10);
    }

    @Test
    void getAllGames_ShouldReturnPage() {
        // En tu impl usas repository.findAll(pageable)
        Page<Game> gamePage = new PageImpl<>(List.of(game));
        when(repository.findAll(any(Pageable.class))).thenReturn(gamePage);
        when(mapper.toDTO(any())).thenReturn(gameDto);

        Page<GameDto> result = gameService.getAllGames(pageable);

        assertFalse(result.isEmpty());
        verify(repository).findAll(any(Pageable.class));
    }

    @Test
    void saveGame_Success() {
        when(mapper.toEntity(any())).thenReturn(game);
        when(repository.save(any())).thenReturn(game);
        when(mapper.toDTO(any())).thenReturn(gameDto);

        GameDto saved = gameService.saveGame(gameDto);

        assertNotNull(saved);
        verify(repository).save(any());
    }

    @Test
    void updateGame_Success() {
        when(repository.findById("1")).thenReturn(Optional.of(game));
        when(repository.save(any())).thenReturn(game);
        when(mapper.toDTO(any())).thenReturn(gameDto);

        GameDto updated = gameService.updateGame("1", gameDto);

        assertNotNull(updated);
        verify(mapper).updateEntityFromDto(any(), any());
        verify(repository).save(any());
    }

    @Test
    void deleteGame_Success() {
        // Tu impl hace un borrado lógico (busca, cambia active y guarda)
        when(repository.findById("1")).thenReturn(Optional.of(game));
        when(repository.save(any())).thenReturn(game);

        gameService.deleteGame("1");

        assertFalse(game.isActive()); // Verificamos que cambió a false
        verify(repository).save(game);
    }

    @Test
    void findGamesFiltered_ShouldReturnPage() {
        // Tu impl usa repository.findAll(Example, Pageable)
        Page<Game> gamePage = new PageImpl<>(List.of(game));

        when(mapper.toEntity(any())).thenReturn(game);
        when(repository.findAll(any(Example.class), any(Pageable.class))).thenReturn(gamePage);
        when(mapper.toDTO(any())).thenReturn(gameDto);

        Page<GameDto> result = gameService.findGamesFiltered(gameDto, pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        // Verificamos que se llamó al findAll que recibe el Example
        verify(repository).findAll(any(Example.class), any(Pageable.class));
    }
}