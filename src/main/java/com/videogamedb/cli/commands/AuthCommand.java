package com.videogamedb.cli.commands;

import com.videogamedb.cli.Main;
import com.videogamedb.cli.models.LoginRequest;
import com.videogamedb.cli.models.SignUpRequest;
import com.videogamedb.cli.services.AuthService;
import com.videogamedb.cli.util.ConsoleUtils;
import com.videogamedb.cli.util.SecurityUtils;

import java.util.Scanner;
import java.util.function.Consumer;

public class AuthCommand {
    private final Scanner scanner;
    private final AuthService authService;
    private final Consumer<String> onLoginSuccess;

    public AuthCommand(Scanner scanner, Consumer<String> onLoginSuccess) {
        this.scanner = scanner;
        this.authService = new AuthService();
        this.onLoginSuccess = onLoginSuccess;
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("AUTHENTICATION");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Logout");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-3): ", 0, 3);
            
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    signUp();
                    break;
                case 3:
                    logout();
                    return;
                case 0:
                    return;
            }
        }
    }

    private void login() {
        try {
            ConsoleUtils.printSubHeader("USER LOGIN");
            
            String username = ConsoleUtils.readRequiredInput(scanner, "Username: ");
            String password = ConsoleUtils.readRequiredInput(scanner, "Password: ");
            
            LoginRequest loginRequest = new LoginRequest(username, password);
            var jwtResponse = authService.login(loginRequest);
            
            SecurityUtils.saveToken(jwtResponse.getToken());
            SecurityUtils.saveRefreshToken(jwtResponse.getRefreshToken());
            onLoginSuccess.accept(jwtResponse.getUsername());
            
            ConsoleUtils.printSuccess("Login successful! Welcome, " + jwtResponse.getUsername() + "!");
        } catch (Exception e) {
            ConsoleUtils.printError("Login failed: " + e.getMessage());
        }
    }

    private void signUp() {
        try {
            ConsoleUtils.printSubHeader("USER REGISTRATION");
            
            String username = ConsoleUtils.readRequiredInput(scanner, "Username: ");
            String email = ConsoleUtils.readRequiredInput(scanner, "Email: ");
            String password = ConsoleUtils.readRequiredInput(scanner, "Password: ");
            String firstName = ConsoleUtils.readRequiredInput(scanner, "First Name: ");
            String lastName = ConsoleUtils.readRequiredInput(scanner, "Last Name: ");
            
            SignUpRequest signUpRequest = new SignUpRequest(username, email, password, firstName, lastName, "USER");
            var response = authService.signUp(signUpRequest);
            
            ConsoleUtils.printSuccess("Registration successful!");
            ConsoleUtils.printInfo("User ID: " + response.get("userId"));
            ConsoleUtils.printInfo("You can now login with your credentials.");
        } catch (Exception e) {
            ConsoleUtils.printError("Registration failed: " + e.getMessage());
        }
    }

    private void logout() {
        try {
            authService.logout();
            SecurityUtils.clearTokens();
            Main.setCurrentUser(null);
            ConsoleUtils.printSuccess("Logged out successfully!");
        } catch (Exception e) {
            ConsoleUtils.printError("Logout failed: " + e.getMessage());
        }
    }
}