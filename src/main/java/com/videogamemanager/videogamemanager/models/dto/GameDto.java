package com.videogamemanager.videogamemanager.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class GameDto {

        @Schema(description = "Título del videojuego", example = "The Legend of Zelda")
        @NotBlank(message = "El título no puede estar vacío")
        @Size(min = 2, max = 100, message = "El título debe tener entre 2 y 100 caracteres")
        private String title;

        @Schema(description = "Género principal", example = "Aventura")
        @NotBlank(message = "El género es obligatorio")
        private String genre;

        @Schema(description = "Año de lanzamiento", example = "2023")
        @Min(value = 1950, message = "El año no puede ser anterior a 1950")
        @Max(value = 2026, message = "El año no puede ser superior al actual")
        private int releaseYear;

        @Schema(description = "Edad mínima recomendada", example = "12")
        @Min(value = 0, message = "La edad no puede ser negativa")
        @Max(value = 18, message = "La edad máxima es 18")
        private Integer age;

        @Schema(description = "Indica si el juego ha sido completado", example = "false")
        @NotNull(message = "El estado de completado es obligatorio")
        private boolean completed;

        @Schema(description = "Fecha de creación (automática)", accessMode = Schema.AccessMode.READ_ONLY)
        private LocalDateTime createdAt;

        @Schema(description = "Fecha de última modificación (automática)", accessMode = Schema.AccessMode.READ_ONLY)
        private LocalDateTime updatedAt;

}
