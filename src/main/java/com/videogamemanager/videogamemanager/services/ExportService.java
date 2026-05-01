package com.videogamemanager.videogamemanager.services;

import com.videogamemanager.videogamemanager.models.dto.GameAdminDto;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface ExportService {

    ByteArrayInputStream exportToCsv(List<GameAdminDto> games);
}
