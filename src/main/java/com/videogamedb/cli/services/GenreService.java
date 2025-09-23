package com.videogamedb.cli.services;

import com.videogamedb.cli.models.Genre;

import java.util.Arrays;
import java.util.List;

public class GenreService {
    private final ApiClient apiClient;

    public GenreService() {
        this.apiClient = new ApiClient();
    }

    public List<Genre> getAllGenres() throws Exception {
        Genre[] genres = apiClient.get("/genres", Genre[].class);
        return Arrays.asList(genres);
    }

    public Genre getGenreById(String genreId) throws Exception {
        return apiClient.get("/genres/" + genreId, Genre.class);
    }

    public List<Genre> searchByName(String name) throws Exception {
        Genre[] genres = apiClient.get("/genres/search?name=" + name, Genre[].class);
        return Arrays.asList(genres);
    }
}