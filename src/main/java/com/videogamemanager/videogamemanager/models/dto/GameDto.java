package com.videogamemanager.videogamemanager.models.dto;

import lombok.Data;

@Data
public class GameDto {

        private String title;
        private String genre;
        private int releaseYear;
        private int age;
        private boolean completed;
}
