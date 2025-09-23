package com.videogamedb.cli.services;

import com.videogamedb.cli.models.OwnedGame;

import java.util.Arrays;
import java.util.List;

public class OwnedGameService {
    private final ApiClient apiClient;

    public OwnedGameService() {
        this.apiClient = new ApiClient();
    }

    public List<OwnedGame> getMyGames() throws Exception {
        OwnedGame[] games = apiClient.get("/owned-games/user/me", OwnedGame[].class);
        return Arrays.asList(games);
    }

    public OwnedGame addGame(String gameId) throws Exception {
        OwnedGame ownedGame = new OwnedGame();
        ownedGame.setGameId(gameId);
        return apiClient.post("/owned-games", ownedGame, OwnedGame.class);
    }

    public void removeGame(String gameId) throws Exception {
        // First get the owned game entry
        List<OwnedGame> myGames = getMyGames();
        OwnedGame gameToRemove = myGames.stream()
                .filter(game -> game.getGameId().equals(gameId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Game not found in your collection"));

        apiClient.delete("/owned-games/" + gameToRemove.getId());
    }
}