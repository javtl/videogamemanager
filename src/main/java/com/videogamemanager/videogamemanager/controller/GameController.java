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
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
@Tag(name = "Videojuegos", description = "Operaciones para gestionar el catálogo de juegos")
public class GameController {

    private final GameService gameService;

    @Operation(summary = "Obtener todos los juegos paginados", description = "Retorna una página de videojuegos con información de paginación.")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    @GetMapping("/all")
    public ResponseEntity<Page<GameDto>> getAll(Pageable pageable) {
        // Le pasamos el pageable que nos da Spring directamente al service
        return ResponseEntity.ok(gameService.getAllGames(pageable));
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

    @GetMapping
    public ResponseEntity<Page<GameDto>> getAllGames(Pageable pageable) {

        return ResponseEntity.ok(gameService.getAllGames(pageable));
    }

    @Operation(summary = "Eliminar un juego por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Juego no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable String id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok("Videojuego eliminado con éxito.");
    }

    @Operation(summary = "Actualizar un juego existente", description = "Busca un juego por ID y actualiza sus campos con la información proporcionada.")
    @PutMapping("/{id}")
    public ResponseEntity<GameDto> updateGame(
            @Parameter(description = "id del juego actualizar") @PathVariable String id,
            @Valid @RequestBody GameDto gameDto){
        return ResponseEntity.ok(gameService.updateGame(id, gameDto));
    }

    @PostMapping("/search")
    @Operation(summary = "Búsqueda avanzada", description = "Filtra por cualquier campo enviado en el body")
    public ResponseEntity<Page<GameDto>> search(@RequestBody GameDto filter, @ParameterObject Pageable pageable){
          return ResponseEntity.ok(gameService.findGamesFiltered(filter, pageable));
    }


}


