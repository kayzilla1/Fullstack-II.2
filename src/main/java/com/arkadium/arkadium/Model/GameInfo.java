package com.arkadium.arkadium.Model;

import java.util.List;

public class GameInfo {
    private long id;
    private String name;
    private String summary;
    private List<String> genres;
    private String gameType;

    public GameInfo(long id, String name, String summary, List<String> genres, String gameType) {
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
    public String getGameType() { return gameType; }
}
