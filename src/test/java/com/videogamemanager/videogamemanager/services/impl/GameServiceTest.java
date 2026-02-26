package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.repository.GameRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Inicializa Mockito automáticamente
class GameServiceTest {

    @Mock
    private GameRepository repository; // Simulamos el repositorio

    @InjectMocks
    private GameServiceImpl gameService; // Inyecta el repo simulado en el servicio

    @Test
    void testGetAllGames() {
        // GIVEN
        Game game = new Game();
        game.setTitle("Cyberpunk 2077");
        when(repository.findAll()).thenReturn(List.of(game));

        // WHEN
        List<Game> result = gameService.getAllGames();

        // THEN
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Cyberpunk 2077", result.get(0).getTitle());
        verify(repository, times(1)).findAll(); // Verifica que se llamó al repo
    }

    @Test
    void testSaveGame_Success() {
        // GIVEN
        Game game = new Game();
        game.setTitle("Minecraft");
        when(repository.save(game)).thenReturn(game);

        // WHEN
        Game savedGame = gameService.saveGame(game);

        // THEN
        assertNotNull(savedGame);
        assertEquals("Minecraft", savedGame.getTitle());
    }

    @Test
    void testSaveGame_ThrowsException_WhenGameIsNull() {
        // WHEN & THEN: Verificamos que lance la excepción que programaste
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            gameService.saveGame(null);
        });

        assertEquals("El juego no puede venir vacio.", exception.getMessage());
        // Verificamos que el repositorio NUNCA se llegó a llamar
        verify(repository, never()).save(any());
    }
}


