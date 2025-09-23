package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.AccessTime;
import com.videogamedb.cli.services.AccessTimeService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class AccessTimeCommand {
    private final Scanner scanner;
    private final AccessTimeService accessTimeService;

    public AccessTimeCommand(Scanner scanner) {
        this.scanner = scanner;
        this.accessTimeService = new AccessTimeService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("ACCESS TIMES");
            System.out.println("1. List My Access Times");
            System.out.println("2. Record Access Time");
            System.out.println("3. Get Recent Access Times");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-3): ", 0, 3);

            switch (choice) {
                case 1:
                    listMyAccessTimes();
                    break;
                case 2:
                    recordAccessTime();
                    break;
                case 3:
                    getRecentAccessTimes();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listMyAccessTimes() {
        try {
            List<AccessTime> accessTimes = accessTimeService.getMyAccessTimes();
            displayAccessTimes(accessTimes);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list access times: " + e.getMessage());
        }
    }

    private void recordAccessTime() {
        try {
            AccessTime accessTime = accessTimeService.recordAccessTime();
            ConsoleUtils.printSuccess("Access time recorded: " + accessTime.getTime());
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to record access time: " + e.getMessage());
        }
    }

    private void getRecentAccessTimes() {
        try {
            int days = ConsoleUtils.readIntInput(scanner, "Enter number of days: ", 1, 365);
            List<AccessTime> accessTimes = accessTimeService.getRecentAccessTimes(days);
            displayAccessTimes(accessTimes);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get recent access times: " + e.getMessage());
        }
    }

    private void displayAccessTimes(List<AccessTime> accessTimes) {
        if (accessTimes.isEmpty()) {
            ConsoleUtils.printInfo("No access times found.");
            return;
        }

        String[][] tableData = accessTimes.stream()
                .map(access -> new String[] {
                        access.getId(),
                        access.getTime().toString()
                })
                .toArray(String[][]::new);

        ConsoleUtils.printTable(
                new String[] { "ID", "Access Time" },
                tableData);
    }
}