package com.videogamedb.cli;

import com.videogamedb.cli.commands.*;
import com.videogamedb.cli.util.ConsoleUtils;
import com.videogamedb.cli.util.SecurityUtils;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean running = true;
    private static String currentUser = null;

    public static void main(String[] args) {
        ConsoleUtils.printWelcome();

        while (running) {
            displayMainMenu();
            int choice = readMenuChoice();
            handleMainMenuChoice(choice);
        }

        scanner.close();
        ConsoleUtils.printSuccess("Thank you for using Video Game Database CLI!");
    }

    private static void displayMainMenu() {
        ConsoleUtils.printHeader("VIDEO GAME DATABASE CLI");
        ConsoleUtils.printInfo("Current User: " + (currentUser != null ? currentUser : "Not logged in"));
        System.out.println();

        System.out.println("1.  Authentication");
        System.out.println("2.  User Management");
        System.out.println("3.  Video Games");
        System.out.println("4.  Game Genres");
        System.out.println("5.  Platforms");
        System.out.println("6.  Platform Releases");
        System.out.println("7.  Contributors");
        System.out.println("8.  Owned Games");
        System.out.println("9.  Play Sessions");
        System.out.println("10. Ratings");
        System.out.println("11. Collections");
        System.out.println("12. Follow System");
        System.out.println("13. Access Times");
        System.out.println("0.  Exit");
        System.out.println();
    }

    private static int readMenuChoice() {
        while (true) {
            try {
                System.out.print("Enter your choice (0-13): ");
                String input = scanner.nextLine().trim();
                int choice = Integer.parseInt(input);
                if (choice >= 0 && choice <= 13) {
                    return choice;
                }
                ConsoleUtils.printError("Please enter a number between 0 and 13");
            } catch (NumberFormatException e) {
                ConsoleUtils.printError("Please enter a valid number");
            }
        }
    }

    private static void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1:
                new AuthCommand(scanner, Main::setCurrentUser).execute();
                break;
            case 2:
                if (requireAuthentication())
                    new UserCommand(scanner).execute();
                break;
            case 3:
                if (requireAuthentication())
                    new VideoGameCommand(scanner).execute();
                break;
            case 4:
                if (requireAuthentication())
                    new GenreCommand(scanner).execute();
                break;
            case 5:
                if (requireAuthentication())
                    new PlatformCommand(scanner).execute();
                break;
            case 6:
                if (requireAuthentication())
                    new PlatformReleaseCommand(scanner).execute();
                break;
            case 7:
                if (requireAuthentication())
                    new ContributorCommand(scanner).execute();
                break;
            case 8:
                if (requireAuthentication())
                    new OwnedGameCommand(scanner).execute();
                break;
            case 9:
                if (requireAuthentication())
                    new PlaySessionCommand(scanner).execute();
                break;
            case 10:
                if (requireAuthentication())
                    new RatingCommand(scanner).execute();
                break;
            case 11:
                if (requireAuthentication())
                    new CollectionCommand(scanner).execute();
                break;
            case 12:
                if (requireAuthentication())
                    new FollowCommand(scanner).execute();
                break;
            case 13:
                if (requireAuthentication())
                    new AccessTimeCommand(scanner).execute();
                break;
            case 0:
                running = false;
                break;
            default:
                ConsoleUtils.printError("Invalid choice");
        }
    }

    private static boolean requireAuthentication() {
        if (currentUser == null && !SecurityUtils.hasValidToken()) {
            ConsoleUtils.printError("Please login first to access this feature");
            return false;
        }
        return true;
    }

    public static void setCurrentUser(String username) {
        currentUser = username;
    }

    public static String getCurrentUser() {
        return currentUser;
    }
}