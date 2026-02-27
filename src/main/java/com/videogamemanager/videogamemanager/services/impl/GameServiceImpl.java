package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.repository.GameRepository;
import com.videogamemanager.videogamemanager.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {


    private final GameRepository repository;

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
