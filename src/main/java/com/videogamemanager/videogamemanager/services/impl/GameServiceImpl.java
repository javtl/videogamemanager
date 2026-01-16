package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.repository.GameRepository;
import com.videogamemanager.videogamemanager.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository repository;

    @Override
    public List<Game> getAllGames() {
        return repository.findAll();
    }

    @Override
    public Game saveGame(Game game) {
        if (game == null){
            throw new RuntimeException("El juego no puede venir vacio.");
        }
        return repository.save(game);
}
}
