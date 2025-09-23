package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.OwnedGame;
import com.videogamedb.cli.services.OwnedGameService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class OwnedGameCommand {
    private final Scanner scanner;
    private final OwnedGameService ownedGameService;

    public OwnedGameCommand(Scanner scanner) {
        this.scanner = scanner;
        this.ownedGameService = new OwnedGameService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("OWNED GAMES");
            System.out.println("1. List My Owned Games");
            System.out.println("2. Add Game to My Collection");
            System.out.println("3. Remove Game from My Collection");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-3): ", 0, 3);

            switch (choice) {
                case 1:
                    listMyGames();
                    break;
                case 2:
                    addGame();
                    break;
                case 3:
                    removeGame();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listMyGames() {
        try {
            List<OwnedGame> ownedGames = ownedGameService.getMyGames();
            displayOwnedGames(ownedGames);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list owned games: " + e.getMessage());
        }
    }

    private void addGame() {
        try {
            String gameId = ConsoleUtils.readRequiredInput(scanner, "Enter Game ID to add: ");
            OwnedGame ownedGame = ownedGameService.addGame(gameId);
            ConsoleUtils.printSuccess("Game added to your collection successfully!");
            displayOwnedGame(ownedGame);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to add game: " + e.getMessage());
        }
    }

    private void removeGame() {
        try {
            String gameId = ConsoleUtils.readRequiredInput(scanner, "Enter Game ID to remove: ");
            ownedGameService.removeGame(gameId);
            ConsoleUtils.printSuccess("Game removed from your collection successfully!");
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to remove game: " + e.getMessage());
        }
    }

    private void displayOwnedGames(List<OwnedGame> ownedGames) {
        if (ownedGames.isEmpty()) {
            ConsoleUtils.printInfo("You don't own any games yet.");
            return;
        }

        String[][] tableData = ownedGames.stream()
                .map(game -> new String[] {
                        game.getId(),
                        game.getGameId(),
                        game.getGameTitle() != null ? game.getGameTitle() : "N/A",
                        game.getAcquisitionDate().toString()
                })
                .toArray(String[][]::new);

        ConsoleUtils.printTable(
                new String[] { "ID", "Game ID", "Title", "Acquisition Date" },
                tableData);
    }

    private void displayOwnedGame(OwnedGame game) {
        ConsoleUtils.printSubHeader("OWNED GAME DETAILS");
        ConsoleUtils.printInfo("ID: " + game.getId());
        ConsoleUtils.printInfo("Game ID: " + game.getGameId());
        ConsoleUtils.printInfo("Title: " + (game.getGameTitle() != null ? game.getGameTitle() : "N/A"));
        ConsoleUtils.printInfo("Acquisition Date: " + game.getAcquisitionDate());
    }
}