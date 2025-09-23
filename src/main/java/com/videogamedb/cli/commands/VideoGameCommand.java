package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.VideoGame;
import com.videogamedb.cli.services.VideoGameService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class VideoGameCommand {
    private final Scanner scanner;
    private final VideoGameService videoGameService;

    public VideoGameCommand(Scanner scanner) {
        this.scanner = scanner;
        this.videoGameService = new VideoGameService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("VIDEO GAMES");
            System.out.println("1. List All Games");
            System.out.println("2. Search Games by Title");
            System.out.println("3. Get Game by ID");
            System.out.println("4. Search by ESRB Rating");
            System.out.println("5. Search by Genre");
            System.out.println("6. Search by Developer");
            System.out.println("7. Search by Publisher");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-7): ", 0, 7);

            switch (choice) {
                case 1:
                    listAllGames();
                    break;
                case 2:
                    searchByTitle();
                    break;
                case 3:
                    getGameById();
                    break;
                case 4:
                    searchByEsrb();
                    break;
                case 5:
                    searchByGenre();
                    break;
                case 6:
                    searchByDeveloper();
                    break;
                case 7:
                    searchByPublisher();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listAllGames() {
        try {
            List<VideoGame> games = videoGameService.getAllGames();
            displayGames(games);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list games: " + e.getMessage());
        }
    }

    private void searchByTitle() {
        try {
            String title = ConsoleUtils.readRequiredInput(scanner, "Enter title to search: ");
            List<VideoGame> games = videoGameService.searchByTitle(title);
            displayGames(games);
        } catch (Exception e) {
            ConsoleUtils.printError("Search failed: " + e.getMessage());
        }
    }

    private void getGameById() {
        try {
            String gameId = ConsoleUtils.readRequiredInput(scanner, "Enter Game ID: ");
            VideoGame game = videoGameService.getGameById(gameId);
            displayGameDetails(game);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get game: " + e.getMessage());
        }
    }

    private void searchByEsrb() {
        try {
            String esrb = ConsoleUtils.readRequiredInput(scanner, "Enter ESRB rating (E, T, M, etc.): ");
            List<VideoGame> games = videoGameService.searchByEsrb(esrb);
            displayGames(games);
        } catch (Exception e) {
            ConsoleUtils.printError("Search failed: " + e.getMessage());
        }
    }

    private void searchByGenre() {
        try {
            String genre = ConsoleUtils.readRequiredInput(scanner, "Enter genre: ");
            List<VideoGame> games = videoGameService.searchByGenre(genre);
            displayGames(games);
        } catch (Exception e) {
            ConsoleUtils.printError("Search failed: " + e.getMessage());
        }
    }

    private void searchByDeveloper() {
        try {
            String developer = ConsoleUtils.readRequiredInput(scanner, "Enter developer name: ");
            List<VideoGame> games = videoGameService.searchByDeveloper(developer);
            displayGames(games);
        } catch (Exception e) {
            ConsoleUtils.printError("Search failed: " + e.getMessage());
        }
    }

    private void searchByPublisher() {
        try {
            String publisher = ConsoleUtils.readRequiredInput(scanner, "Enter publisher name: ");
            List<VideoGame> games = videoGameService.searchByPublisher(publisher);
            displayGames(games);
        } catch (Exception e) {
            ConsoleUtils.printError("Search failed: " + e.getMessage());
        }
    }

    private void displayGames(List<VideoGame> games) {
        if (games.isEmpty()) {
            ConsoleUtils.printInfo("No games found.");
            return;
        }

        String[][] tableData = games.stream()
                .map(game -> new String[] {
                        game.getId(),
                        game.getTitle(),
                        game.getEsrb(),
                        String.join(", ", game.getGenres()),
                        String.join(", ", game.getDevelopers()),
                        String.join(", ", game.getPublishers())
                })
                .toArray(String[][]::new);

        ConsoleUtils.printTable(
                new String[] { "ID", "Title", "ESRB", "Genres", "Developers", "Publishers" },
                tableData);
    }

    private void displayGameDetails(VideoGame game) {
        ConsoleUtils.printSubHeader("GAME DETAILS");
        ConsoleUtils.printInfo("ID: " + game.getId());
        ConsoleUtils.printInfo("Title: " + game.getTitle());
        ConsoleUtils.printInfo("ESRB Rating: " + game.getEsrb());
        ConsoleUtils.printInfo("Genres: " + String.join(", ", game.getGenres()));
        ConsoleUtils.printInfo("Developers: " + String.join(", ", game.getDevelopers()));
        ConsoleUtils.printInfo("Publishers: " + String.join(", ", game.getPublishers()));

        if (game.getDescription() != null) {
            ConsoleUtils.printInfo("Description: " + game.getDescription());
        }
        if (game.getReleaseDate() != null) {
            ConsoleUtils.printInfo("Release Date: " + game.getReleaseDate());
        }
    }
}