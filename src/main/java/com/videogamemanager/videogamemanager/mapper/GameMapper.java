package com.videogamemanager.videogamemanager.mapper;

import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.models.Game;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GameMapper {

    GameDto toDTO(Game game);

    Game toEntity(GameDto gameDTO);

    void updateEntityFromDto(GameDto gameDto, @MappingTarget Game game);
}