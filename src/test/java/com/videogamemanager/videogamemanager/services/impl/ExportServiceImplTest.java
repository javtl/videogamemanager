package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.models.dto.GameAdminDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExportServiceImplTest {

    @InjectMocks
    private ExportServiceImpl exportService;

    private GameAdminDto game1;

    @BeforeEach
    void setUp() {
        // Los 9 parámetros exactos que definiste en tu DTO
        game1 = new GameAdminDto(
                "1",
                "The Witcher 3",
                "RPG",
                2015,
                18,
                true,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    @Test
    void exportToCsv_ShouldWorkCorrectly() {
        ByteArrayInputStream result = exportService.exportToCsv(Collections.singletonList(game1));
        String content = new String(result.readAllBytes(), StandardCharsets.UTF_8);

        assertNotNull(result);
        assertTrue(content.contains("The Witcher 3"));
        assertTrue(content.contains("RPG"));
    }

    @Test
    void exportToCsv_ShouldHandleSpecialCharacters() {
        GameAdminDto specialGame = new GameAdminDto(
                "2", "Mario, Kart", "Racing", 2017, 3, true, true, null, null
        );

        ByteArrayInputStream result = exportService.exportToCsv(Collections.singletonList(specialGame));
        String content = new String(result.readAllBytes(), StandardCharsets.UTF_8);

        // Verifica que la coma forzó las comillas dobles
        assertTrue(content.contains("\"Mario, Kart\""));
    }
}
