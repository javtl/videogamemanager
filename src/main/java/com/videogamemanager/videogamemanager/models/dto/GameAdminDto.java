package com.videogamemanager.videogamemanager.models.dto;

import lombok.AllArgsConstructor; // Importante
import lombok.Data;
import lombok.NoArgsConstructor; // Importante
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor // Genera el constructor de 9 parámetros
@NoArgsConstructor  // Genera el constructor vacío
public class GameAdminDto {
    @Id
    @Field("_id")
    private String id;
    private String title;
    private String genre;
    private Integer releaseYear;
    private Integer age;
    private Boolean completed;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
