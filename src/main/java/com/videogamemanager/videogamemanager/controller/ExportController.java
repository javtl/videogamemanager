package com.videogamemanager.videogamemanager.controller;


import com.videogamemanager.videogamemanager.models.dto.GameAdminDto;
import com.videogamemanager.videogamemanager.services.ExportService;
import com.videogamemanager.videogamemanager.services.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/export")
@RequiredArgsConstructor
@Tag(name = "Exportación de Informes", description = "Operaciones para exportar informes")
public class ExportController {

    private final GameService gameService;
    private final ExportService exportService;

    @GetMapping("/csv")
    public ResponseEntity<Resource> exportGameToCSV(@ModelAttribute GameAdminDto filter) {

        List<GameAdminDto> games = gameService.findAllForExport(filter);

        ByteArrayInputStream in = exportService.exportToCsv(games);

        InputStreamResource file = new InputStreamResource(in);

        String filename = "admin_report_" + System.currentTimeMillis() + ".csv";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(file);
    }
}
