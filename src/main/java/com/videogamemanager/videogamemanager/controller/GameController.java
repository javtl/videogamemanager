package com.videogamemanager.videogamemanager.controller;

import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/all")
    public List<GameDto> getAll(){
        return gameService.getAllGames();
    }

    @PostMapping
    public GameDto saveGame(@RequestBody GameDto game){
        return gameService.saveGame(game);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGame(@PathVariable String id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok("Videojuego eliminado con éxito.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDto> updateGame(@PathVariable String id, @RequestBody GameDto game){
        return ResponseEntity.ok(gameService.updateGame(id, game));
    }
}


