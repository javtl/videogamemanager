package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.exceptions.InvalidGameException;
import com.videogamemanager.videogamemanager.mapper.GameMapper;
import com.videogamemanager.videogamemanager.models.Game;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.repository.GameRepository;
import com.videogamemanager.videogamemanager.services.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {


    private final GameMapper mapper;
    private final GameRepository repository;

    /**
     * Recupera el catálogo completo de videojuegos de forma paginada.
     *
     * @param pageable Objeto con la configuración de paginación (índice, tamaño y orden).
     * @return Una página de objetos {@link GameDto} con la información procesada.
     */

    @Override
    public Page<GameDto> getAllGames(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDTO);
    }

    @Override
    public GameDto saveGame(GameDto gameDto) {
        log.info("Guardando nuevo videojuego: {}", gameDto.getTitle());
        return mapper.toDTO(repository.save(mapper.toEntity(gameDto)));
    }

    @Override
    public GameDto updateGame(String id, GameDto gameDto) {
        log.info("Actualizando videojuego con id: {}", id);
        return repository.findById(id)
                .map(existingGame -> {
                    mapper.updateEntityFromDto(gameDto, existingGame);
                    return mapper.toDTO(repository.save(existingGame));
                })
                .orElseThrow(() -> new InvalidGameException("Juego no encontrado con id: " + id));
    }

    /**
     * Realiza una búsqueda avanzada de videojuegos aplicando filtros dinámicos.
     * Utiliza Query by Example (QBE) para permitir coincidencias parciales e ignorar mayúsculas.
     *
     * @param filter   Objeto DTO que contiene los criterios de búsqueda (ej. título o género).
     * @param pageable Configuración de paginación y ordenación de los resultados.
     * @return Una página {@link Page} con los videojuegos que coinciden con el criterio, convertidos a DTO.
     */
    @Override
    public Page<GameDto> findGamesFiltered(GameDto filter, Pageable pageable) {

        Game probe = mapper.toEntity(filter);
        probe.setActive(true);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withIgnorePaths("age", "releaseYear", "active")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Game> example = Example.of(probe, matcher);

        return repository.findAll(example, pageable).map(mapper::toDTO);
    }

    @Override
    public void deleteGame(String id) {
        log.info("Eliminando videojuego con id: {}", id);

        Game game = repository.findById(id)
                .orElseThrow(() -> new InvalidGameException("No se encontro el juego con id: " + id));

        game.setActive(false);

        repository.save(game);
    }


}

