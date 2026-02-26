package com.videogamemanager.videogamemanager.controller;

import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/all")
    public List<Game> getAll(){
        return gameService.getAllGames();
    }

    @PostMapping
    public Game saveGame(@RequestBody Game game){
        return gameService.saveGame(game);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        GameService.deleteGame(id);
        return ResponseEntity.ok("Videojuego eliminado con éxito.");
    }
}