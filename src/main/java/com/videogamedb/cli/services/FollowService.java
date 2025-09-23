package com.videogamedb.cli.services;

import com.videogamedb.cli.models.Follow;
import com.videogamedb.cli.models.User;

import java.util.Arrays;
import java.util.List;

public class FollowService {
    private final ApiClient apiClient;

    public FollowService() {
        this.apiClient = new ApiClient();
    }

    public List<User> getFollowing() throws Exception {
        User[] users = apiClient.get("/follows/following", User[].class);
        return Arrays.asList(users);
    }

    public List<User> getFollowers() throws Exception {
        User[] users = apiClient.get("/follows/followers", User[].class);
        return Arrays.asList(users);
    }

    public Follow followUser(String userId) throws Exception {
        Follow follow = new Follow();
        follow.setFollowedId(userId);
        return apiClient.post("/follows", follow, Follow.class);
    }

    public void unfollowUser(String userId) throws Exception {
        apiClient.delete("/follows/user/" + userId);
    }

    public int getFollowingCount() throws Exception {
        // This would typically be returned from a specific endpoint
        return getFollowing().size();
    }

    public int getFollowersCount() throws Exception {
        // This would typically be returned from a specific endpoint
        return getFollowers().size();
    }
}