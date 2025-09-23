package com.videogamedb.cli.services;

import com.videogamedb.cli.models.PlatformRelease;

import java.util.Arrays;
import java.util.List;

public class PlatformReleaseService {
    private final ApiClient apiClient;

    public PlatformReleaseService() {
        this.apiClient = new ApiClient();
    }

    public List<PlatformRelease> getAllReleases() throws Exception {
        PlatformRelease[] releases = apiClient.get("/platform-releases", PlatformRelease[].class);
        return Arrays.asList(releases);
    }

    public List<PlatformRelease> getReleasesByGame(String gameId) throws Exception {
        PlatformRelease[] releases = apiClient.get("/platform-releases/game/" + gameId, PlatformRelease[].class);
        return Arrays.asList(releases);
    }

    public List<PlatformRelease> getReleasesByPlatform(String platformId) throws Exception {
        PlatformRelease[] releases = apiClient.get("/platform-releases/platform/" + platformId,
                PlatformRelease[].class);
        return Arrays.asList(releases);
    }

    public List<PlatformRelease> searchByPriceRange(Double minPrice, Double maxPrice) throws Exception {
        String endpoint = "/platform-releases/price/range?minPrice=" + minPrice + "&maxPrice=" + maxPrice;
        PlatformRelease[] releases = apiClient.get(endpoint, PlatformRelease[].class);
        return Arrays.asList(releases);
    }
}