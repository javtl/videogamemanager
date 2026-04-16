package com.videogamemanager.videogamemanager.utils;

public class AppConstants {

    private AppConstants(){
        throw new IllegalStateException("Utility class");
    }

    public static final String MSG_GAME_DELETE = "Videojuego eliminado con exito";
    public static final String MSG_GAME_NOT_FOUND = "Videojuego no encontrado con exito";

    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String TOTAL_GAMES = "totalGames";

}
