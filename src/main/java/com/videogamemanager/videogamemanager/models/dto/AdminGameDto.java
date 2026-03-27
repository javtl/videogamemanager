package com.videogamemanager.videogamemanager.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = true)
public class AdminGameDto extends GameDto{

    @Schema(description = "Id Interno de la Base de Datos")
    private String id;
}
