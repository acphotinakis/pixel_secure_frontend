package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.Platform;
import com.videogamedb.cli.services.PlatformService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class PlatformCommand {
    private final Scanner scanner;
    private final PlatformService platformService;

    public PlatformCommand(Scanner scanner) {
        this.scanner = scanner;
        this.platformService = new PlatformService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("GAMING PLATFORMS");
            System.out.println("1. List All Platforms");
            System.out.println("2. Search Platforms by Name");
            System.out.println("3. Get Platform by ID");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-3): ", 0, 3);

            switch (choice) {
                case 1:
                    listAllPlatforms();
                    break;
                case 2:
                    searchPlatforms();
                    break;
                case 3:
                    getPlatformById();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listAllPlatforms() {
        try {
            List<Platform> platforms = platformService.getAllPlatforms();
            displayPlatforms(platforms);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list platforms: " + e.getMessage());
        }
    }

    private void searchPlatforms() {
        try {
            String name = ConsoleUtils.readRequiredInput(scanner, "Enter platform name to search: ");
            List<Platform> platforms = platformService.searchByName(name);
            displayPlatforms(platforms);
        } catch (Exception e) {
            ConsoleUtils.printError("Search failed: " + e.getMessage());
        }
    }

    private void getPlatformById() {
        try {
            String platformId = ConsoleUtils.readRequiredInput(scanner, "Enter Platform ID: ");
            Platform platform = platformService.getPlatformById(platformId);
            displayPlatformDetails(platform);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get platform: " + e.getMessage());
        }
    }

    private void displayPlatforms(List<Platform> platforms) {
        if (platforms.isEmpty()) {
            ConsoleUtils.printInfo("No platforms found.");
            return;
        }

        String[][] tableData = platforms.stream()
                .map(platform -> new String[] {
                        platform.getId(),
                        platform.getPlatformName()
                })
                .toArray(String[][]::new);

        ConsoleUtils.printTable(
                new String[] { "ID", "Platform Name" },
                tableData);
    }

    private void displayPlatformDetails(Platform platform) {
        ConsoleUtils.printSubHeader("PLATFORM DETAILS");
        ConsoleUtils.printInfo("ID: " + platform.getId());
        ConsoleUtils.printInfo("Name: " + platform.getPlatformName());
    }
}