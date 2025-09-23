package com.videogamedb.cli.services;

import com.videogamedb.cli.models.Contributor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ContributorService {
    private final ApiClient apiClient;

    public ContributorService() {
        this.apiClient = new ApiClient();
    }

    public List<Contributor> getAllContributors() throws Exception {
        Contributor[] contributors = apiClient.get("/contributors", Contributor[].class);
        return Arrays.asList(contributors);
    }

    public Contributor getContributorById(String contributorId) throws Exception {
        return apiClient.get("/contributors/" + contributorId, Contributor.class);
    }

    public List<Contributor> searchByName(String name) throws Exception {
        Contributor[] contributors = apiClient.get("/contributors/search?name=" + name, Contributor[].class);
        return Arrays.asList(contributors);
    }

    public List<Contributor> getDevelopers() throws Exception {
        Contributor[] contributors = apiClient.get("/contributors/type/developer", Contributor[].class);
        return Arrays.asList(contributors);
    }

    public List<Contributor> getPublishers() throws Exception {
        Contributor[] contributors = apiClient.get("/contributors/type/publisher", Contributor[].class);
        return Arrays.asList(contributors);
    }
}