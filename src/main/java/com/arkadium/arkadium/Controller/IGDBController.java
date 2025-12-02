package com.arkadium.arkadium.Controller;

import com.arkadium.arkadium.Model.GameInfo;
import com.arkadium.arkadium.Services.IGDBService;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/igdb")
@CrossOrigin(origins = "*")
@Tag(name = "IGDB Controller", description = "Operaciones para interactuar con IGDB")
public class IGDBController {

    private final IGDBService igdbService;

    public IGDBController(IGDBService igdbService) {
        this.igdbService = igdbService;
    }

    @GetMapping("/game")
    public GameInfo getGame(
        @Parameter(description = "Nombre del juego a buscar", example = "Super Fun Game")
        @RequestParam String name
    ) {
        try {
            return igdbService.fetchGameByName(name);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener juego: " + e.getMessage());
        }
    }
}
