package com.videogamemanager.videogamemanager.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class Game {

    @Id
    private String id;
    private String title;
    private String genre;
    private int releaseYear;
    private int age;
    private boolean completed;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
