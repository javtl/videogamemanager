package com.videogamemanager.videogamemanager.controller;

import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
@Tag(name = "Videojuegos", description = "Operaciones para gestionar el catálogo de juegos")
public class GameController {

    private final GameService gameService;

    @Operation(summary = "Obtener todos los juegos", description = "Retorna una lista con todos los videojuegos registrados en la base de datos.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @GetMapping("/all")
    public ResponseEntity<List<GameDto>> getAll(){
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @Operation(summary = "Registrar un nuevo juego", description = "Crea un nuevo videojuego. El título es obligatorio.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Juego creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<GameDto> saveGame(@Valid @RequestBody GameDto gameDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.saveGame(gameDto));
    }

    @Operation(summary = "Eliminar un juego por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@Parameter(description = "ID único del juego a eliminar") @PathVariable String id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok("Videojuego eliminado con éxito.");
    }

    @Operation(summary = "Actualizar un juego existente", description = "Busca un juego por ID y actualiza sus campos con la información proporcionada.")
    @PutMapping("/{id}")
    public ResponseEntity<GameDto> updateGame(
            @Parameter(description = "ID del juego a actualizar") @PathVariable String id,
            @Valid @RequestBody GameDto gameDto){
        return ResponseEntity.ok(gameService.updateGame(id, gameDto));
    }

    @Operation(summary = "Buscar juegos por género", description = "Filtra los juegos que coincidan exactamente con el género indicado (ignora mayúsculas/minúsculas).")
    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<GameDto>> getByGenre(@PathVariable String genre){
        return ResponseEntity.ok(gameService.findByGenre(genre));
    }

    @Operation(summary = "Buscar por título", description = "Busca juegos cuyo título contenga la cadena de texto proporcionada.")
    @GetMapping("/title")
    public ResponseEntity<List<GameDto>> getByTitle(
            @Parameter(description = "Texto contenido en el título") @RequestParam String title){
        return ResponseEntity.ok(gameService.findByTitle(title));
    }
}


