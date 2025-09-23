package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.PlatformRelease;
import com.videogamedb.cli.services.PlatformReleaseService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class PlatformReleaseCommand {
    private final Scanner scanner;
    private final PlatformReleaseService platformReleaseService;

    public PlatformReleaseCommand(Scanner scanner) {
        this.scanner = scanner;
        this.platformReleaseService = new PlatformReleaseService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("PLATFORM RELEASES");
            System.out.println("1. List All Releases");
            System.out.println("2. Get Releases by Game");
            System.out.println("3. Get Releases by Platform");
            System.out.println("4. Search by Price Range");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-4): ", 0, 4);

            switch (choice) {
                case 1:
                    listAllReleases();
                    break;
                case 2:
                    getReleasesByGame();
                    break;
                case 3:
                    getReleasesByPlatform();
                    break;
                case 4:
                    searchByPriceRange();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listAllReleases() {
        try {
            List<PlatformRelease> releases = platformReleaseService.getAllReleases();
            displayReleases(releases);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list releases: " + e.getMessage());
        }
    }

    private void getReleasesByGame() {
        try {
            String gameId = ConsoleUtils.readRequiredInput(scanner, "Enter Game ID: ");
            List<PlatformRelease> releases = platformReleaseService.getReleasesByGame(gameId);
            displayReleases(releases);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get releases: " + e.getMessage());
        }
    }

    private void getReleasesByPlatform() {
        try {
            String platformId = ConsoleUtils.readRequiredInput(scanner, "Enter Platform ID: ");
            List<PlatformRelease> releases = platformReleaseService.getReleasesByPlatform(platformId);
            displayReleases(releases);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get releases: " + e.getMessage());
        }
    }

    private void searchByPriceRange() {
        try {
            Double minPrice = ConsoleUtils.readDoubleInput(scanner, "Enter minimum price: ");
            Double maxPrice = ConsoleUtils.readDoubleInput(scanner, "Enter maximum price: ");

            List<PlatformRelease> releases = platformReleaseService.searchByPriceRange(minPrice, maxPrice);
            displayReleases(releases);
        } catch (Exception e) {
            ConsoleUtils.printError("Search failed: " + e.getMessage());
        }
    }

    private void displayReleases(List<PlatformRelease> releases) {
        if (releases.isEmpty()) {
            ConsoleUtils.printInfo("No releases found.");
            return;
        }

        String[][] tableData = releases.stream()
                .map(release -> new String[] {
                        release.getId(),
                        release.getGameId(),
                        release.getPlatformId(),
                        release.getPrice() != null ? "$" + release.getPrice() : "N/A",
                        release.getReleaseDate() != null ? release.getReleaseDate().toString() : "N/A"
                })
                .toArray(String[][]::new);

        ConsoleUtils.printTable(
                new String[] { "ID", "Game ID", "Platform ID", "Price", "Release Date" },
                tableData);
    }
}