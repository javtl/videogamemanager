package com.videogamemanager.videogamemanager.mapper;

import com.videogamemanager.videogamemanager.models.dto.AdminGameDto;
import com.videogamemanager.videogamemanager.models.dto.GameDto;
import com.videogamemanager.videogamemanager.models.Game;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Componente de mapeo encargado de la transformación de datos entre la capa
 * de persistencia (Entity) y la capa de presentación (DTO).
 * <p>
 * Utiliza MapStruct para la generación automática de implementaciones
 * optimizadas en tiempo de compilación.
 */
@Mapper(componentModel = "spring")
public interface GameMapper {

    /**
     * Convierte una entidad de dominio en un objeto de transferencia de datos.
     * * @param game Objeto de base de datos {@link Game}.
     * @return El objeto {@link GameDto} con la información procesada para la API.
     */
    GameDto toDTO(Game game);

    /**
     * Transforma un DTO recibido en una entidad de base de datos.
     * * @param gameDTO Objeto con los datos provenientes de la petición.
     * @return La entidad {@link Game} lista para ser persistida.
     */
    Game toEntity(GameDto gameDTO);

    /**
     * Actualiza una instancia de entidad existente con los datos proporcionados en un DTO.
     * Este método es ideal para operaciones de actualización parcial (PUT/PATCH).
     * * @param gameDto Fuente de los nuevos datos.
     * @param game    Entidad de destino que será modificada ({@link MappingTarget}).
     */
    void updateEntityFromDto(GameDto gameDto, @MappingTarget Game game);

    AdminGameDto toAdminDTO(Game game);
}