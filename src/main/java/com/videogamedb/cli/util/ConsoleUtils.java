package com.videogamedb.cli.util;

import java.util.Scanner;

public class ConsoleUtils {
    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    public static void printWelcome() {
        System.out.println(CYAN + BOLD + "==========================================" + RESET);
        System.out.println(CYAN + BOLD + "    VIDEO GAME DATABASE CLI CLIENT" + RESET);
        System.out.println(CYAN + BOLD + "==========================================" + RESET);
        System.out.println();
    }

    public static void printHeader(String title) {
        System.out.println();
        System.out.println(CYAN + BOLD + "=== " + title + " ===" + RESET);
        System.out.println();
    }

    public static void printSubHeader(String title) {
        System.out.println();
        System.out.println(BLUE + "» " + title + RESET);
        System.out.println();
    }

    public static void printSuccess(String message) {
        System.out.println(GREEN + "✓ " + message + RESET);
    }

    public static void printError(String message) {
        System.out.println(RED + "✗ " + message + RESET);
    }

    public static void printInfo(String message) {
        System.out.println(BLUE + "ℹ " + message + RESET);
    }

    public static void printWarning(String message) {
        System.out.println(YELLOW + "⚠ " + message + RESET);
    }

    public static String readInput(Scanner scanner, String prompt) {
        System.out.print(CYAN + prompt + RESET);
        return scanner.nextLine().trim();
    }

    public static String readRequiredInput(Scanner scanner, String prompt) {
        while (true) {
            String input = readInput(scanner, prompt);
            if (!input.isEmpty()) {
                return input;
            }
            printError("This field is required. Please enter a value.");
        }
    }

    public static int readIntInput(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            try {
                String input = readInput(scanner, prompt);
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                printError("Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                printError("Please enter a valid number");
            }
        }
    }

    public static double readDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            try {
                String input = readInput(scanner, prompt);
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                printError("Please enter a valid number");
            }
        }
    }

    public static void printTable(String[] headers, String[][] data) {
        if (data == null || data.length == 0) {
            printInfo("No data to display.");
            return;
        }

        // Calculate column widths
        int[] columnWidths = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            columnWidths[i] = headers[i].length();
        }

        for (String[] row : data) {
            for (int i = 0; i < row.length; i++) {
                if (row[i] != null && row[i].length() > columnWidths[i]) {
                    columnWidths[i] = row[i].length();
                }
            }
        }

        // Print header
        printTableRow(headers, columnWidths, PURPLE + BOLD);

        // Print separator
        System.out.print("+");
        for (int width : columnWidths) {
            System.out.print("-".repeat(width + 2) + "+");
        }
        System.out.println();

        // Print data rows
        for (String[] row : data) {
            printTableRow(row, columnWidths, RESET);
        }
        System.out.println();
    }

    private static void printTableRow(String[] row, int[] widths, String color) {
        System.out.print("|");
        for (int i = 0; i < row.length; i++) {
            String cell = row[i] != null ? row[i] : "";
            System.out.print(" " + color + String.format("%-" + widths[i] + "s", cell) + RESET + " |");
        }
        System.out.println();
    }
}