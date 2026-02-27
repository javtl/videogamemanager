package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.exceptions.InvalidGameException;
import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
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
    public List<GameDto> getAllGames() {
        return repository.findAll();
    }

    @Override
    public GameDto saveGame(GameDto game) {
        if (game == null || game.getTitle() == null || game.getTitle().isEmpty()) {
            throw new InvalidGameException("El juego no puede venir vacío o sin título.");
        }
        return repository.save(game);
    }

    @Override
    public GameDto updateGame(String id, GameDto game) {
        return repository.findAllById(id);
    }

    @Override
    public void deleteGame(String id) {
        if (!repository.existsById(id)) {
            throw new InvalidGameException("No se encontró el juego con ID: " + id);
        }
        repository.deleteById(id);
    }
}

