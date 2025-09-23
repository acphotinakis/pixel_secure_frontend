package com.videogamedb.cli.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("Warning: application.properties not found, using default values");
            }
        } catch (IOException e) {
            System.err.println("Warning: Could not load application.properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}