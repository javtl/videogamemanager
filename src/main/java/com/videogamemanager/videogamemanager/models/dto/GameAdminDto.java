package com.videogamemanager.videogamemanager.models.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Data
public class GameAdminDto {

    @Id
    @Field("_id")
    private String id; // El ID de MongoDB que el Admin necesita
    private String title;
    private String genre;
    private int releaseYear;
    private Integer age;
    private boolean completed;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}