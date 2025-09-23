package com.videogamedb.cli.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SecurityUtils {
    private static final String CONFIG_DIR = System.getProperty("user.home") + "/.videogamedb-cli";
    private static final String TOKEN_FILE = CONFIG_DIR + "/token";
    private static final String REFRESH_TOKEN_FILE = CONFIG_DIR + "/refresh_token";

    static {
        try {
            Files.createDirectories(Paths.get(CONFIG_DIR));
        } catch (IOException e) {
            System.err.println("Warning: Could not create config directory: " + e.getMessage());
        }
    }

    public static void saveToken(String token) {
        saveToFile(TOKEN_FILE, token);
    }

    public static String getToken() {
        return readFromFile(TOKEN_FILE);
    }

    public static void saveRefreshToken(String refreshToken) {
        saveToFile(REFRESH_TOKEN_FILE, refreshToken);
    }

    public static String getRefreshToken() {
        return readFromFile(REFRESH_TOKEN_FILE);
    }

    public static void clearTokens() {
        deleteFile(TOKEN_FILE);
        deleteFile(REFRESH_TOKEN_FILE);
    }

    public static boolean hasValidToken() {
        String token = getToken();
        return token != null && !token.isEmpty();
    }

    private static void saveToFile(String filename, String content) {
        try {
            Path path = Paths.get(filename);
            Files.writeString(path, content);
        } catch (IOException e) {
            System.err.println("Warning: Could not save to file: " + e.getMessage());
        }
    }

    private static String readFromFile(String filename) {
        try {
            Path path = Paths.get(filename);
            if (Files.exists(path)) {
                return Files.readString(path).trim();
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not read from file: " + e.getMessage());
        }
        return null;
    }

    private static void deleteFile(String filename) {
        try {
            Files.deleteIfExists(Paths.get(filename));
        } catch (IOException e) {
            System.err.println("Warning: Could not delete file: " + e.getMessage());
        }
    }
}