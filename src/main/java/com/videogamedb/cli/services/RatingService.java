package com.videogamedb.cli.services;

import com.videogamedb.cli.models.Rating;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class RatingService {
    private final ApiClient apiClient;

    public RatingService() {
        this.apiClient = new ApiClient();
    }

    public List<Rating> getMyRatings() throws Exception {
        Rating[] ratings = apiClient.get("/ratings/user/me", Rating[].class);
        return Arrays.asList(ratings);
    }

    public Rating rateGame(String gameId, int ratingValue) throws Exception {
        Rating rating = new Rating();
        rating.setGameId(gameId);
        rating.setRating(ratingValue);
        rating.setRatingDate(LocalDateTime.now());
        return apiClient.post("/ratings", rating, Rating.class);
    }

    public Rating updateRating(String gameId, int newRating) throws Exception {
        // First get existing rating
        List<Rating> myRatings = getMyRatings();
        Rating existingRating = myRatings.stream()
                .filter(r -> r.getGameId().equals(gameId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("You haven't rated this game yet"));

        existingRating.setRating(newRating);
        existingRating.setRatingDate(LocalDateTime.now());

        return apiClient.put("/ratings/" + existingRating.getId(), existingRating, Rating.class);
    }

    public List<Rating> getGameRatings(String gameId) throws Exception {
        Rating[] ratings = apiClient.get("/ratings/game/" + gameId, Rating[].class);
        return Arrays.asList(ratings);
    }
}