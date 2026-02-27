package com.videogamemanager.videogamemanager.controller;

import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/all")
    public List<Game> getAll(){
        return gameService.getAllGames();
    }

    @PostMapping
    public Game saveGame(@RequestBody Game game){
        return gameService.saveGame(game);
    }
}
