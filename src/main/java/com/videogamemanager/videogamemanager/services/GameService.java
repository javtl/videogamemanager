package com.videogamemanager.videogamemanager.services;

import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.models.dto.GameDto;

import java.util.List;


public interface GameService {

    void deleteGame(String id);

    List<GameDto> getAllGames();

    GameDto saveGame(GameDto game);

    GameDto updateGame(String id, GameDto game);

    List<Game> findByGenre(String genre);
    List<Game> findByTitle(String title);
}

