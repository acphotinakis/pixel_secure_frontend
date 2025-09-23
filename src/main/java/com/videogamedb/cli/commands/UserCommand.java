package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.User;
import com.videogamedb.cli.services.UserService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class UserCommand {
    private final Scanner scanner;
    private final UserService userService;

    public UserCommand(Scanner scanner) {
        this.scanner = scanner;
        this.userService = new UserService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("USER MANAGEMENT");
            System.out.println("1. Get My Profile");
            System.out.println("2. Update My Profile");
            System.out.println("3. List All Users (Admin Only)");
            System.out.println("4. Get User by ID");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-4): ", 0, 4);

            switch (choice) {
                case 1:
                    getMyProfile();
                    break;
                case 2:
                    updateMyProfile();
                    break;
                case 3:
                    listAllUsers();
                    break;
                case 4:
                    getUserById();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void getMyProfile() {
        try {
            User user = userService.getCurrentUser();
            displayUserInfo(user);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get user profile: " + e.getMessage());
        }
    }

    private void updateMyProfile() {
        try {
            User currentUser = userService.getCurrentUser();
            ConsoleUtils.printSubHeader("UPDATE PROFILE");

            String email = ConsoleUtils.readInput(scanner,
                    "New Email (current: " + currentUser.getEmail() + "): ");
            String firstName = ConsoleUtils.readInput(scanner,
                    "New First Name (current: " + currentUser.getFirstName() + "): ");
            String lastName = ConsoleUtils.readInput(scanner,
                    "New Last Name (current: " + currentUser.getLastName() + "): ");

            User updateRequest = new User();
            if (!email.isEmpty())
                updateRequest.setEmail(email);
            if (!firstName.isEmpty())
                updateRequest.setFirstName(firstName);
            if (!lastName.isEmpty())
                updateRequest.setLastName(lastName);

            User updatedUser = userService.updateUser(currentUser.getId(), updateRequest);
            ConsoleUtils.printSuccess("Profile updated successfully!");
            displayUserInfo(updatedUser);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to update profile: " + e.getMessage());
        }
    }

    private void listAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            if (users.isEmpty()) {
                ConsoleUtils.printInfo("No users found.");
                return;
            }

            ConsoleUtils.printSubHeader("ALL USERS");
            String[][] tableData = users.stream()
                    .map(user -> new String[] {
                            user.getId(),
                            user.getUsername(),
                            user.getEmail(),
                            user.getRole(),
                            user.getCreationDate().toString()
                    })
                    .toArray(String[][]::new);

            ConsoleUtils.printTable(
                    new String[] { "ID", "Username", "Email", "Role", "Created" },
                    tableData);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list users: " + e.getMessage());
        }
    }

    private void getUserById() {
        try {
            String userId = ConsoleUtils.readRequiredInput(scanner, "Enter User ID: ");
            User user = userService.getUserById(userId);
            displayUserInfo(user);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get user: " + e.getMessage());
        }
    }

    private void displayUserInfo(User user) {
        ConsoleUtils.printSubHeader("USER INFORMATION");
        ConsoleUtils.printInfo("ID: " + user.getId());
        ConsoleUtils.printInfo("Username: " + user.getUsername());
        ConsoleUtils.printInfo("Email: " + user.getEmail());
        ConsoleUtils.printInfo("First Name: " + user.getFirstName());
        ConsoleUtils.printInfo("Last Name: " + user.getLastName());
        ConsoleUtils.printInfo("Role: " + user.getRole());
        ConsoleUtils.printInfo("Creation Date: " + user.getCreationDate());
        ConsoleUtils.printInfo("Last Login: " +
                (user.getLastLoginDate() != null ? user.getLastLoginDate() : "Never"));
    }
}