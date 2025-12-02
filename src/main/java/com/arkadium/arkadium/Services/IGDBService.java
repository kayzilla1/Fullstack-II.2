package com.arkadium.arkadium.Services;

import com.arkadium.arkadium.Model.GameInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
public class IGDBService {

    private final String CLIENT_ID = "785uwogi2n2af99l4djkfi8mird69r";
    private final String CLIENT_SECRET = "eet6h7me6hu7cvh89zrzsf0jabwkev";
    private String accessToken = "pyey0n70jmxcn7f65qki425fwlb9em";

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://api.igdb.com/v4")
            .defaultHeader("Client-ID", CLIENT_ID)
            .build();

    private String renewAccessToken() throws Exception {
        WebClient tokenClient = WebClient.builder()
                .baseUrl("https://id.twitch.tv/oauth2")
                .build();

        JsonNode response = tokenClient.post()
                .uri("/token?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&grant_type=client_credentials")
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        if (response == null || !response.has("access_token")) {
            throw new Exception("Error obteniendo token OAuth");
        }

        accessToken = response.get("access_token").asText();
        return accessToken;
    }

    private JsonNode rawGameRequest(String gameName) throws Exception {
        String query = "search \"" + gameName + "\"; fields name,summary,genres.name,game_modes.name; limit 1;";

        JsonNode response = webClient.post()
                .uri("/games")
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .bodyValue(query)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        return response;
    }

    public GameInfo fetchGameByName(String name) throws Exception {
        JsonNode response = rawGameRequest(name);

        if (response.isEmpty()) {
            renewAccessToken();
            response = rawGameRequest(name);
        }

        if (response.isEmpty()) {
            return null;
        }

        JsonNode game = response.get(0);

        long id = game.get("id").asLong();
        String gameName = game.get("name").asText();
        String summary = game.has("summary") ? game.get("summary").asText() : "No summary";

        List<String> genres = new ArrayList<>();
        if (game.has("genres")) {
            game.get("genres").forEach(g -> genres.add(g.get("name").asText()));
        }

        String gameType = game.has("game_modes")
                ? game.get("game_modes").get(0).get("name").asText()
                : "Unknown";

        return new GameInfo(id, gameName, summary, genres, gameType);
    }
}
