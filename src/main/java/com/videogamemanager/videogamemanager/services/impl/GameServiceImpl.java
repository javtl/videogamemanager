package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.exceptions.InvalidGameException;
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
        if (game == null || game.getTitle() == null || game.getTitle().isEmpty()) {
            throw new InvalidGameException("El juego no puede venir vacío o sin título.");
        }
        return repository.save(game);
    }
        // con @Override me da ERROR bombilla roja
    public void deleteGame(String id) {
        if (!repository.existsById(id)) {
            throw new InvalidGameException("No se encontró el juego con ID: " + id);
        }
        repository.deleteById(id);
    }
}
