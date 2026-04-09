package com.videogamemanager.videogamemanager.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStatsDto {

    private String genre;
    private Long totalGames;
    private Double averageAge;

    @JsonIgnore
    private List<GameAdminDto> games;
}
