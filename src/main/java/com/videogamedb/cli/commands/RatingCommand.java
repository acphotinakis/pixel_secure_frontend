package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.Rating;
import com.videogamedb.cli.services.RatingService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class RatingCommand {
    private final Scanner scanner;
    private final RatingService ratingService;

    public RatingCommand(Scanner scanner) {
        this.scanner = scanner;
        this.ratingService = new RatingService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("GAME RATINGS");
            System.out.println("1. List My Ratings");
            System.out.println("2. Rate a Game");
            System.out.println("3. Update My Rating");
            System.out.println("4. Get Game Ratings");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-4): ", 0, 4);

            switch (choice) {
                case 1:
                    listMyRatings();
                    break;
                case 2:
                    rateGame();
                    break;
                case 3:
                    updateRating();
                    break;
                case 4:
                    getGameRatings();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listMyRatings() {
        try {
            List<Rating> ratings = ratingService.getMyRatings();
            displayRatings(ratings);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list ratings: " + e.getMessage());
        }
    }

    private void rateGame() {
        try {
            String gameId = ConsoleUtils.readRequiredInput(scanner, "Enter Game ID: ");
            int ratingValue = ConsoleUtils.readIntInput(scanner, "Enter rating (1-5): ", 1, 5);

            Rating rating = ratingService.rateGame(gameId, ratingValue);
            ConsoleUtils.printSuccess("Rating submitted successfully!");
            displayRating(rating);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to rate game: " + e.getMessage());
        }
    }

    private void updateRating() {
        try {
            String gameId = ConsoleUtils.readRequiredInput(scanner, "Enter Game ID: ");
            int newRating = ConsoleUtils.readIntInput(scanner, "Enter new rating (1-5): ", 1, 5);

            Rating rating = ratingService.updateRating(gameId, newRating);
            ConsoleUtils.printSuccess("Rating updated successfully!");
            displayRating(rating);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to update rating: " + e.getMessage());
        }
    }

    private void getGameRatings() {
        try {
            String gameId = ConsoleUtils.readRequiredInput(scanner, "Enter Game ID: ");
            List<Rating> ratings = ratingService.getGameRatings(gameId);
            displayRatings(ratings);

            // Show statistics
            double average = ratings.stream()
                    .mapToInt(Rating::getRating)
                    .average()
                    .orElse(0.0);
            ConsoleUtils.printInfo("Average Rating: " + String.format("%.1f", average));
            ConsoleUtils.printInfo("Total Ratings: " + ratings.size());
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get game ratings: " + e.getMessage());
        }
    }

    private void displayRatings(List<Rating> ratings) {
        if (ratings.isEmpty()) {
            ConsoleUtils.printInfo("No ratings found.");
            return;
        }

        String[][] tableData = ratings.stream()
                .map(rating -> new String[] {
                        rating.getId(),
                        rating.getGameId(),
                        String.valueOf(rating.getRating()),
                        rating.getRatingDate().toString()
                })
                .toArray(String[][]::new);

        ConsoleUtils.printTable(
                new String[] { "ID", "Game ID", "Rating", "Date" },
                tableData);
    }

    private void displayRating(Rating rating) {
        ConsoleUtils.printSubHeader("RATING DETAILS");
        ConsoleUtils.printInfo("ID: " + rating.getId());
        ConsoleUtils.printInfo("Game ID: " + rating.getGameId());
        ConsoleUtils.printInfo("Rating: " + rating.getRating() + "/5");
        ConsoleUtils.printInfo("Date: " + rating.getRatingDate());
    }
}