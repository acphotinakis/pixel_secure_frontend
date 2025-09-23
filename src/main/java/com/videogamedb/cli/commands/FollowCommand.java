package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.Follow;
import com.videogamedb.cli.models.User;
import com.videogamedb.cli.services.FollowService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class FollowCommand {
    private final Scanner scanner;
    private final FollowService followService;

    public FollowCommand(Scanner scanner) {
        this.scanner = scanner;
        this.followService = new FollowService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("FOLLOW SYSTEM");
            System.out.println("1. List People I Follow");
            System.out.println("2. List My Followers");
            System.out.println("3. Follow User");
            System.out.println("4. Unfollow User");
            System.out.println("5. Get Follow Stats");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-5): ", 0, 5);

            switch (choice) {
                case 1:
                    listFollowing();
                    break;
                case 2:
                    listFollowers();
                    break;
                case 3:
                    followUser();
                    break;
                case 4:
                    unfollowUser();
                    break;
                case 5:
                    getStats();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listFollowing() {
        try {
            List<User> following = followService.getFollowing();
            displayUsers("People I Follow", following);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list following: " + e.getMessage());
        }
    }

    private void listFollowers() {
        try {
            List<User> followers = followService.getFollowers();
            displayUsers("My Followers", followers);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list followers: " + e.getMessage());
        }
    }

    private void followUser() {
        try {
            String userId = ConsoleUtils.readRequiredInput(scanner, "Enter User ID to follow: ");
            Follow follow = followService.followUser(userId);
            ConsoleUtils.printSuccess("You are now following user: " + userId);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to follow user: " + e.getMessage());
        }
    }

    private void unfollowUser() {
        try {
            String userId = ConsoleUtils.readRequiredInput(scanner, "Enter User ID to unfollow: ");
            followService.unfollowUser(userId);
            ConsoleUtils.printSuccess("You have unfollowed user: " + userId);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to unfollow user: " + e.getMessage());
        }
    }

    private void getStats() {
        try {
            int followingCount = followService.getFollowingCount();
            int followersCount = followService.getFollowersCount();

            ConsoleUtils.printSubHeader("FOLLOW STATISTICS");
            ConsoleUtils.printInfo("People I Follow: " + followingCount);
            ConsoleUtils.printInfo("My Followers: " + followersCount);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get follow stats: " + e.getMessage());
        }
    }

    private void displayUsers(String title, List<User> users) {
        if (users.isEmpty()) {
            ConsoleUtils.printInfo("No " + title.toLowerCase() + " found.");
            return;
        }

        String[][] tableData = users.stream()
                .map(user -> new String[] {
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                })
                .toArray(String[][]::new);

        ConsoleUtils.printTable(
                new String[] { "ID", "Username", "Email" },
                tableData);
    }
}