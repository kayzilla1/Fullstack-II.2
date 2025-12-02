package com.arkadium.arkadium.Controller;

import com.arkadium.arkadium.Model.GameInfo;
import com.arkadium.arkadium.Services.IGDBService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/igdb")
@CrossOrigin(origins = "*")
public class IGDBController {

    private final IGDBService igdbService;

    public IGDBController(IGDBService igdbService) {
        this.igdbService = igdbService;
    }

    @GetMapping("/game")
    public GameInfo getGame(@RequestParam String name) {
        try {
            return igdbService.fetchGameByName(name);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener juego: " + e.getMessage());
        }
    }
}
