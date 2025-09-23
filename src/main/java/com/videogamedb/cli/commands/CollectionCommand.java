package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.Collection;
import com.videogamedb.cli.services.CollectionService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class CollectionCommand {
    private final Scanner scanner;
    private final CollectionService collectionService;

    public CollectionCommand(Scanner scanner) {
        this.scanner = scanner;
        this.collectionService = new CollectionService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("GAME COLLECTIONS");
            System.out.println("1. List My Collections");
            System.out.println("2. Create Collection");
            System.out.println("3. Add Game to Collection");
            System.out.println("4. Remove Game from Collection");
            System.out.println("5. Get Collection Details");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-5): ", 0, 5);

            switch (choice) {
                case 1:
                    listMyCollections();
                    break;
                case 2:
                    createCollection();
                    break;
                case 3:
                    addGameToCollection();
                    break;
                case 4:
                    removeGameFromCollection();
                    break;
                case 5:
                    getCollectionDetails();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listMyCollections() {
        try {
            List<Collection> collections = collectionService.getMyCollections();
            displayCollections(collections);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list collections: " + e.getMessage());
        }
    }

    private void createCollection() {
        try {
            String name = ConsoleUtils.readRequiredInput(scanner, "Enter collection name: ");
            String description = ConsoleUtils.readInput(scanner, "Enter description (optional): ");

            Collection collection = collectionService.createCollection(name, description);
            ConsoleUtils.printSuccess("Collection created successfully!");
            displayCollection(collection);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to create collection: " + e.getMessage());
        }
    }

    private void addGameToCollection() {
        try {
            String collectionId = ConsoleUtils.readRequiredInput(scanner, "Enter Collection ID: ");
            String gameId = ConsoleUtils.readRequiredInput(scanner, "Enter Game ID: ");

            Collection collection = collectionService.addGame(collectionId, gameId);
            ConsoleUtils.printSuccess("Game added to collection successfully!");
            displayCollection(collection);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to add game to collection: " + e.getMessage());
        }
    }

    private void removeGameFromCollection() {
        try {
            String collectionId = ConsoleUtils.readRequiredInput(scanner, "Enter Collection ID: ");
            String gameId = ConsoleUtils.readRequiredInput(scanner, "Enter Game ID: ");

            Collection collection = collectionService.removeGame(collectionId, gameId);
            ConsoleUtils.printSuccess("Game removed from collection successfully!");
            displayCollection(collection);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to remove game from collection: " + e.getMessage());
        }
    }

    private void getCollectionDetails() {
        try {
            String collectionId = ConsoleUtils.readRequiredInput(scanner, "Enter Collection ID: ");
            Collection collection = collectionService.getCollectionById(collectionId);
            displayCollection(collection);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get collection: " + e.getMessage());
        }
    }

    private void displayCollections(List<Collection> collections) {
        if (collections.isEmpty()) {
            ConsoleUtils.printInfo("No collections found.");
            return;
        }

        String[][] tableData = collections.stream()
                .map(collection -> new String[] {
                        collection.getId(),
                        collection.getName(),
                        collection.getDescription() != null ? collection.getDescription() : "No description",
                        String.valueOf(collection.getGames() != null ? collection.getGames().size() : 0)
                })
                .toArray(String[][]::new);

        ConsoleUtils.printTable(
                new String[] { "ID", "Name", "Description", "Game Count" },
                tableData);
    }

    private void displayCollection(Collection collection) {
        ConsoleUtils.printSubHeader("COLLECTION DETAILS");
        ConsoleUtils.printInfo("ID: " + collection.getId());
        ConsoleUtils.printInfo("Name: " + collection.getName());
        ConsoleUtils.printInfo("Description: " +
                (collection.getDescription() != null ? collection.getDescription() : "No description"));
        ConsoleUtils.printInfo("Game Count: " +
                (collection.getGames() != null ? collection.getGames().size() : 0));

        if (collection.getGames() != null && !collection.getGames().isEmpty()) {
            ConsoleUtils.printInfo("Games: " + String.join(", ", collection.getGames()));
        }
    }
}