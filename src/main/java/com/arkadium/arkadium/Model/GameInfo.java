package com.arkadium.arkadium.Model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class GameInfo {
    @Schema(description = "Identificador único del juego", example = "12345")
    private long id;

    @Schema(description = "Nombre del juego", example = "Super Fun Game")
    private String name;

    @Schema(description = "Resumen del juego", example = "Este es un juego muy divertido...")
    private String summary;

    @Schema(description = "Géneros del juego", example = "[\"Acción\", \"Aventura\"]")
    private List<String> genres;

    @Schema(description = "Tipos de juego", example = "[\"Multijugador\", \"Cooperativo\"]")
    private List<String> gameType;

    public GameInfo(long id, String name, String summary, List<String> genres, List<String> gameType) {
        this.id = id;
        this.name = name;
        this.summary = summary;
        this.genres = genres;
        this.gameType = gameType;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getSummary() { return summary; }
    public List<String> getGenres() { return genres; }
    public List<String> getGameType() { return gameType; }
}