package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.exceptions.InvalidGameException;
import com.videogamemanager.videogamemanager.mapper.GameMapper;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.repository.GameRepository;
import com.videogamemanager.videogamemanager.services.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {


    private final GameMapper mapper;
    private final GameRepository repository;

    @Override
    public List<GameDto> getAllGames() {
        log.info("Obteniendo todos los videojuegos");
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
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

    @Override
    public List<GameDto> findByGenre(String genre) {
        log.info("Buscando videojuegos por género: {}", genre);
        return repository.findByGenreIgnoreCase(genre).stream()
                .map(mapper::toDTO)
                .toList();

    }

    @Override
    public List<GameDto> findByTitle(String title) {
        log.info("Buscando videojuegos por titulo: {}", title);
        return repository.findByTitleContainingIgnoreCase(title).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public void deleteGame(String id) {
        log.info("Eliminando videojuego con id: {}", id);
        if (!repository.existsById(id)) {
            throw new InvalidGameException("No se encontró el juego con ID: " + id);
        }
        repository.deleteById(id);
    }
}

