package com.videogamedb.cli.services;

import com.videogamedb.cli.models.Platform;

import java.util.Arrays;
import java.util.List;

public class PlatformService {
    private final ApiClient apiClient;

    public PlatformService() {
        this.apiClient = new ApiClient();
    }

    public List<Platform> getAllPlatforms() throws Exception {
        Platform[] platforms = apiClient.get("/platforms", Platform[].class);
        return Arrays.asList(platforms);
    }

    public Platform getPlatformById(String platformId) throws Exception {
        return apiClient.get("/platforms/" + platformId, Platform.class);
    }

    public List<Platform> searchByName(String name) throws Exception {
        Platform[] platforms = apiClient.get("/platforms/search?name=" + name, Platform[].class);
        return Arrays.asList(platforms);
    }
}