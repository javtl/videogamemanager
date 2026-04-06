package com.videogamemanager.videogamemanager.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GameStatsDto {

    private String genre;
    private long totalGames;
    private double averageAge;
    private List<GameAdminDto> games;
}
