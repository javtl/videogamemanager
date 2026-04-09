package com.videogamemanager.videogamemanager.services;

import com.videogamemanager.videogamemanager.models.dto.AdminGameDto;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * Interfaz que define los servicios de lógica de negocio para la gestión de videojuegos.
 * <p>
 * Proporciona operaciones CRUD básicas y capacidades de búsqueda avanzada con soporte
 * para paginación nativa.
 */
public interface GameService {

    /**
     * Elimina de forma permanente un videojuego del sistema.
     * * @param id Identificador único (UUID) del juego a eliminar.
     *
     */
    void deleteGame(String id);

    /**
     * Recupera el catálogo completo de videojuegos segmentado por páginas.
     * * @param pageable Configuración de paginación (número de página, tamaño y ordenación).
     * @return Una {@link Page} que contiene los objetos {@link GameDto} solicitados.
     */
    Page<GameDto> getAllGames(Pageable pageable);

    /**
     * Registra un nuevo videojuego en la base de datos.
     * * @param game Objeto DTO con la información del juego a crear.
     * @return El {@link GameDto} persistido, incluyendo campos generados por el sistema (como IDs o fechas).
     */
    GameDto saveGame(GameDto game);

    /**
     * Actualiza la información de un videojuego existente.
     * * @param id   Identificador del juego que se desea modificar.
     * @param game DTO con los nuevos datos a aplicar.
     * @return El objeto {@link GameDto} tras aplicar los cambios en la base de datos.
     */
    GameDto updateGame(String id, GameDto game);

    /**
     * Realiza una búsqueda filtrada de videojuegos basada en criterios dinámicos.
     * * @param filter   Objeto que actúa como "plantilla" para filtrar (ej: por título o género).
     * @param pageable Configuración para el manejo de los resultados paginados.
     * @return Página de resultados que coinciden con los criterios de búsqueda.
     */
    Page<GameDto> findGamesFiltered(GameDto filter, Pageable pageable);

    List<AdminGameDto> getAllGamesWithId();

}
