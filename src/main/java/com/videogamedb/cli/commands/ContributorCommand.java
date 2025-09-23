package com.videogamedb.cli.commands;

import com.videogamedb.cli.models.Contributor;
import com.videogamedb.cli.services.ContributorService;
import com.videogamedb.cli.util.ConsoleUtils;

import java.util.List;
import java.util.Scanner;

public class ContributorCommand {
    private final Scanner scanner;
    private final ContributorService contributorService;

    public ContributorCommand(Scanner scanner) {
        this.scanner = scanner;
        this.contributorService = new ContributorService();
    }

    public void execute() {
        while (true) {
            ConsoleUtils.printHeader("CONTRIBUTORS (DEVELOPERS & PUBLISHERS)");
            System.out.println("1. List All Contributors");
            System.out.println("2. Search Contributors by Name");
            System.out.println("3. Get Contributor by ID");
            System.out.println("4. List Developers");
            System.out.println("5. List Publishers");
            System.out.println("0. Back to Main Menu");
            System.out.println();

            int choice = ConsoleUtils.readIntInput(scanner, "Enter your choice (0-5): ", 0, 5);

            switch (choice) {
                case 1:
                    listAllContributors();
                    break;
                case 2:
                    searchContributors();
                    break;
                case 3:
                    getContributorById();
                    break;
                case 4:
                    listDevelopers();
                    break;
                case 5:
                    listPublishers();
                    break;
                case 0:
                    return;
            }
        }
    }

    private void listAllContributors() {
        try {
            List<Contributor> contributors = contributorService.getAllContributors();
            displayContributors(contributors);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list contributors: " + e.getMessage());
        }
    }

    private void searchContributors() {
        try {
            String name = ConsoleUtils.readRequiredInput(scanner, "Enter contributor name to search: ");
            List<Contributor> contributors = contributorService.searchByName(name);
            displayContributors(contributors);
        } catch (Exception e) {
            ConsoleUtils.printError("Search failed: " + e.getMessage());
        }
    }

    private void getContributorById() {
        try {
            String contributorId = ConsoleUtils.readRequiredInput(scanner, "Enter Contributor ID: ");
            Contributor contributor = contributorService.getContributorById(contributorId);
            displayContributorDetails(contributor);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to get contributor: " + e.getMessage());
        }
    }

    private void listDevelopers() {
        try {
            List<Contributor> developers = contributorService.getDevelopers();
            displayContributors(developers);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list developers: " + e.getMessage());
        }
    }

    private void listPublishers() {
        try {
            List<Contributor> publishers = contributorService.getPublishers();
            displayContributors(publishers);
        } catch (Exception e) {
            ConsoleUtils.printError("Failed to list publishers: " + e.getMessage());
        }
    }

    private void displayContributors(List<Contributor> contributors) {
        if (contributors.isEmpty()) {
            ConsoleUtils.printInfo("No contributors found.");
            return;
        }

        String[][] tableData = contributors.stream()
                .map(contributor -> new String[] {
                        contributor.getId(),
                        contributor.getContributorName(),
                        contributor.getType()
                })
                .toArray(String[][]::new);

        ConsoleUtils.printTable(
                new String[] { "ID", "Name", "Type" },
                tableData);
    }

    private void displayContributorDetails(Contributor contributor) {
        ConsoleUtils.printSubHeader("CONTRIBUTOR DETAILS");
        ConsoleUtils.printInfo("ID: " + contributor.getId());
        ConsoleUtils.printInfo("Name: " + contributor.getContributorName());
        ConsoleUtils.printInfo("Type: " + contributor.getType());
    }
}