package com.videogamedb.cli.services;

import com.videogamedb.cli.models.VideoGame;

import java.util.Arrays;
import java.util.List;

public class VideoGameService {
    private final ApiClient apiClient;

    public VideoGameService() {
        this.apiClient = new ApiClient();
    }

    public List<VideoGame> getAllGames() throws Exception {
        VideoGame[] games = apiClient.get("/video-games", VideoGame[].class);
        return Arrays.asList(games);
    }

    public VideoGame getGameById(String gameId) throws Exception {
        return apiClient.get("/video-games/" + gameId, VideoGame.class);
    }

    public List<VideoGame> searchByTitle(String title) throws Exception {
        VideoGame[] games = apiClient.get("/video-games/search?title=" + title, VideoGame[].class);
        return Arrays.asList(games);
    }

    public List<VideoGame> searchByEsrb(String esrb) throws Exception {
        VideoGame[] games = apiClient.get("/video-games/esrb/" + esrb, VideoGame[].class);
        return Arrays.asList(games);
    }

    public List<VideoGame> searchByGenre(String genre) throws Exception {
        VideoGame[] games = apiClient.get("/video-games/genre/" + genre, VideoGame[].class);
        return Arrays.asList(games);
    }

    public List<VideoGame> searchByDeveloper(String developer) throws Exception {
        VideoGame[] games = apiClient.get("/video-games/developer/" + developer, VideoGame[].class);
        return Arrays.asList(games);
    }

    public List<VideoGame> searchByPublisher(String publisher) throws Exception {
        VideoGame[] games = apiClient.get("/video-games/publisher/" + publisher, VideoGame[].class);
        return Arrays.asList(games);
    }
}