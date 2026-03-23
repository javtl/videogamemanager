
package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.exceptions.InvalidGameException;
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

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setId("1");
        game.setTitle("Zelda");

        gameDto = new GameDto();
        gameDto.setTitle("Zelda");
    }

    @Test
    void getAllGames_ShouldReturnList() {
        when(repository.findAll()).thenReturn(List.of(game));
        when(mapper.toDTO(any())).thenReturn(gameDto);

        List<GameDto> result = gameService.getAllGames(pageable);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void saveGame_Success() {
        when(mapper.toEntity(any())).thenReturn(game);
        when(repository.save(any())).thenReturn(game);
        when(mapper.toDTO(any())).thenReturn(gameDto);

        GameDto saved = gameService.saveGame(gameDto);

        assertNotNull(saved);
        assertEquals("Zelda", saved.getTitle());
        verify(repository).save(any());
    }

    // ELIMINADO: saveGame_ThrowsException_WhenTitleEmpty
    // ¿Por qué? Porque esa responsabilidad ahora es del Controlador y @Valid.

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
    void updateGame_ThrowsException_NotFound() {
        when(repository.findById("2")).thenReturn(Optional.empty());

        assertThrows(InvalidGameException.class, () -> gameService.updateGame("2", gameDto));
    }

    @Test
    void deleteGame_Success() {
        when(repository.existsById("1")).thenReturn(true);

        gameService.deleteGame("1");

        verify(repository).deleteById("1");
    }

    @Test
    void deleteGame_ThrowsException_NotFound() {
        when(repository.existsById("2")).thenReturn(false);

        assertThrows(InvalidGameException.class, () -> gameService.deleteGame("2"));
        verify(repository, never()).deleteById(anyString());
    }

    @Test
    void findByGenre_ShouldReturnList() {
        when(repository.findByGenreIgnoreCase("Aventura")).thenReturn(List.of(game));
        when(mapper.toDTO(any())).thenReturn(gameDto);

        List<GameDto> result = gameService.findByGenre("Aventura");

        assertFalse(result.isEmpty());
        verify(repository).findByGenreIgnoreCase("Aventura");
    }

    @Test
    void findByTitle_ShouldReturnList() {
        when(repository.findByTitleContainingIgnoreCase("Zelda")).thenReturn(List.of(game));
        when(mapper.toDTO(any())).thenReturn(gameDto);

        List<GameDto> result = gameService.findByTitle("Zelda");

        assertFalse(result.isEmpty());
        verify(repository).findByTitleContainingIgnoreCase("Zelda");
    }
}