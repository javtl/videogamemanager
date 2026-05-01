package com.videogamemanager.videogamemanager.services.impl;

import com.videogamemanager.videogamemanager.models.dto.GameAdminDto;
import com.videogamemanager.videogamemanager.services.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class ExportServiceImpl implements ExportService {

    @Override
    public ByteArrayInputStream exportToCsv(List<GameAdminDto> games) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        // Cabeceras según tus campos de GameDto
        sb.append("ID,Título,Género,Año,Edad,Completado,Activo,Creado,Actualizado\n");

        for (GameAdminDto game : games) {
            sb.append(escapeCsv(game.getId())).append(",")
                    .append(escapeCsv(game.getTitle())).append(",")
                    .append(escapeCsv(game.getGenre())).append(",")
                    .append(game.getReleaseYear()).append(",")
                    .append(game.getAge()).append(",")
                    .append(game.isCompleted() ? "SÍ" : "NO").append(",")
                    .append(game.isActive() ? "SÍ" : "NO").append(",")
                    .append(game.getCreatedAt() != null ? game.getCreatedAt().format(formatter) : "").append(",")
                    .append(game.getUpdatedAt() != null ? game.getUpdatedAt().format(formatter) : "")
                    .append("\n");
        }

        return new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    private String escapeCsv(String data) {
        if (data == null) return "";
        // Si el dato contiene comas, comillas o saltos de línea, lo envolvemos en comillas dobles
        if (data.contains(",") || data.contains("\"") || data.contains("\n")) {
            return "\"" + data.replace("\"", "\"\"") + "\"";
        }
        return data;
    }
}
