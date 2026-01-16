package com.videogamemanager.videogamemanager.controller;

import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {


    private GameService gameService;

    @GetMapping
    public List<Game> getAll(){
        return gameService.getAllGames();
    }

    @PostMapping
    public Game saveGame(@RequestBody Game game){
        return gameService.saveGame(game);
    }
}
