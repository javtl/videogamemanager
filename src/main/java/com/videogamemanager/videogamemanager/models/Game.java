package com.videogamemanager.videogamemanager.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Game {

    @Id
    private String id;
    private String title;
    private String genre;
    private int releaseYear;
    private int age;
    private boolean completed;



}
