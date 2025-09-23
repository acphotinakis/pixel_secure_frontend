package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.PlaySession;
import com.videogamedb.cli.services.PlaySessionService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class PlaySessionCommand {
    private final Scanner scanner;
    private final PlaySessionService playSessionService;

    public PlaySessionCommand(Scanner scanner) {
        this.scanner = scanner;
        this.playSessionService = new PlaySessionService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("PLAY SESSIONS");
            System.out.println("1. List My Play Sessions");
            System.out.println("2. Start New Play Session");
            System.out.println("3. End Current Play Session");
            System.out.println("4. Get Session by ID");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-4): ", 0, 4);

            switch (choice) {
                case 1:
                    listMySessions();
                    break;
                case 2:
                    startSession();
                    break;
                case 3:
                    endSession();
                    break;
                case 4:
                    getSessionById();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listMySessions() {
        try {
            List<PlaySession> sessions = playSessionService.getMySessions();
            displaySessions(sessions);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list play sessions: " + e.getMessage());
        }
    }

    private void startSession() {
        try {
            String gameId = ConsoleUtils.readRequiredInput(scanner, "Enter Game ID: ");
            PlaySession session = playSessionService.startSession(gameId);
            ConsoleUtils.printSuccess("Play session started!");
            displaySession(session);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to start session: " + e.getMessage());
        }
    }

    private void endSession() {
        try {
            String sessionId = ConsoleUtils.readRequiredInput(scanner, "Enter Session ID: ");
            int timePlayed = ConsoleUtils.readIntInput(scanner, "Enter time played (minutes): ", 1, 1000);

            PlaySession session = playSessionService.endSession(sessionId, timePlayed * 60); // Convert to seconds
            ConsoleUtils.printSuccess("Play session ended! Total time: " + timePlayed + " minutes");
            displaySession(session);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to end session: " + e.getMessage());
        }
    }

    private void getSessionById() {
        try {
            String sessionId = ConsoleUtils.readRequiredInput(scanner, "Enter Session ID: ");
            PlaySession session = playSessionService.getSessionById(sessionId);
            displaySession(session);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get session: " + e.getMessage());
        }
    }

    private void displaySessions(List<PlaySession> sessions) {
        if (sessions.isEmpty()) {
            ConsoleUtils.printInfo("No play sessions found.");
            return;
        }

        String[][] tableData = sessions.stream()
                .map(session -> new String[] {
                        session.getId(),
                        session.getGameId(),
                        session.getDatetimeOpened().toString(),
                        session.getTimePlayed() != null ? (session.getTimePlayed() / 60) + " mins" : "Active"
                })
                .toArray(String[][]::new);

        ConsoleUtils.printTable(
                new String[] { "ID", "Game ID", "Started", "Time Played" },
                tableData);
    }

    private void displaySession(PlaySession session) {
        ConsoleUtils.printSubHeader("PLAY SESSION DETAILS");
        ConsoleUtils.printInfo("ID: " + session.getId());
        ConsoleUtils.printInfo("Game ID: " + session.getGameId());
        ConsoleUtils.printInfo("Started: " + session.getDatetimeOpened());
        ConsoleUtils.printInfo("Time Played: " +
                (session.getTimePlayed() != null ? (session.getTimePlayed() / 60) + " minutes" : "Active"));
    }
}