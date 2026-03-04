package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.exceptions.InvalidGameException;
import com.videogamemanager.videogamemanager.mapper.GameMapper;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.repository.GameRepository;
import com.videogamemanager.videogamemanager.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {


    private final GameMapper mapper;
    private final GameRepository repository;

    @Override
    public List<GameDto> getAllGames() {

        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GameDto saveGame(GameDto gameDto) {
        if (gameDto == null || gameDto.getTitle() == null || gameDto.getTitle().isEmpty()) {
            throw new InvalidGameException("El juego no puede venir vacío o sin título.");
        }

        return mapper.toDTO(repository.save(mapper.toEntity(gameDto)));
    }

    @Override
    public GameDto updateGame(String id, GameDto gameDto) {

        return repository.findById(id)
                .map(existingGame -> {
                    mapper.updateEntityFromDto(gameDto, existingGame);
                    return mapper.toDTO(repository.save(existingGame));
                })
                .orElseThrow(() -> new InvalidGameException("Juego no encontrado con id: " + id));
    }

    @Override
    public List<GameDto> findByGenre(String genre) {
        return repository.findByGenreIgnoreCase(genre).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<GameDto> findByTitle(String title) {
        return repository.findByTitleContainingIgnoreCase(title).stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteGame(String id) {
        if (!repository.existsById(id)) {
            throw new InvalidGameException("No se encontró el juego con ID: " + id);
        }
        repository.deleteById(id);
    }
}

