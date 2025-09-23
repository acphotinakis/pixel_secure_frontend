package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.Genre;
import com.videogamedb.cli.services.GenreService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class GenreCommand {
    private final Scanner scanner;
    private final GenreService genreService;

    public GenreCommand(Scanner scanner) {
        this.scanner = scanner;
        this.genreService = new GenreService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("GAME GENRES");
            System.out.println("1. List All Genres");
            System.out.println("2. Search Genres by Name");
            System.out.println("3. Get Genre by ID");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-3): ", 0, 3);

            switch (choice) {
                case 1:
                    listAllGenres();
                    break;
                case 2:
                    searchGenres();
                    break;
                case 3:
                    getGenreById();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listAllGenres() {
        try {
            List<Genre> genres = genreService.getAllGenres();
            displayGenres(genres);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list genres: " + e.getMessage());
        }
    }

    private void searchGenres() {
        try {
            String name = ConsoleUtils.readRequiredInput(scanner, "Enter genre name to search: ");
            List<Genre> genres = genreService.searchByName(name);
            displayGenres(genres);
        } catch (Exception e) {
            ConsoleUtils.printError("Search failed: " + e.getMessage());
        }
    }

    private void getGenreById() {
        try {
            String genreId = ConsoleUtils.readRequiredInput(scanner, "Enter Genre ID: ");
            Genre genre = genreService.getGenreById(genreId);
            displayGenreDetails(genre);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get genre: " + e.getMessage());
        }
    }

    private void displayGenres(List<Genre> genres) {
        if (genres.isEmpty()) {
            ConsoleUtils.printInfo("No genres found.");
            return;
        }

        String[][] tableData = genres.stream()
                .map(genre -> new String[] {
                        genre.getId(),
                        genre.getGenreName()
                })
                .toArray(String[][]::new);

        ConsoleUtils.printTable(
                new String[] { "ID", "Genre Name" },
                tableData);
    }

    private void displayGenreDetails(Genre genre) {
        ConsoleUtils.printSubHeader("GENRE DETAILS");
        ConsoleUtils.printInfo("ID: " + genre.getId());
        ConsoleUtils.printInfo("Name: " + genre.getGenreName());
    }
}