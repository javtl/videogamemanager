package com.videogamemanager.videogamemanager.models.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GameAdminDto {
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