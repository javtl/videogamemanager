package com.videogamemanager.videogamemanager.mapper;

import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.models.Game;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameMapper {

    GameDto toDTO(Game game);

    Game toEntity(GameDto gameDTO);
}