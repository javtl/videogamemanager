package com.videogamemanager.videogamemanager.controller;

import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.services.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @GetMapping("/all")
    public ResponseEntity<List<GameDto>> getAll(){
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @PostMapping
    public ResponseEntity<GameDto> saveGame(@RequestBody GameDto gameDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.saveGame(gameDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable String id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok("Videojuego eliminado con éxito.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDto> updateGame(@PathVariable String id, @RequestBody GameDto gameDto){
        return ResponseEntity.ok(gameService.updateGame(id, gameDto));
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<List<GameDto>> getByGenre(@PathVariable String genre){
        return ResponseEntity.ok(gameService.findByGenre(genre));
    }

    @GetMapping("/title")
    public ResponseEntity<List<GameDto>> getByTitle(@RequestParam String title){
        return ResponseEntity.ok(gameService.findByTitle(title));
    }
}


