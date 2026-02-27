package com.videogamemanager.videogamemanager.services;

import com.videogamemanager.videogamemanager.models.Game;

import java.util.List;


public interface GameService {

    List<Game> getAllGames();

    Game saveGame(Game game);
}

