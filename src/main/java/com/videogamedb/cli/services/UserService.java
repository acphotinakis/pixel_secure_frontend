package com.videogamedb.cli.services;

import com.videogamedb.cli.models.User;

import java.util.Arrays;
import java.util.List;

public class UserService {
    private final ApiClient apiClient;

    public UserService() {
        this.apiClient = new ApiClient();
    }

    public User getCurrentUser() throws Exception {
        return apiClient.get("/users/me", User.class);
    }

    public List<User> getAllUsers() throws Exception {
        User[] users = apiClient.get("/users", User[].class);
        return Arrays.asList(users);
    }

    public User getUserById(String userId) throws Exception {
        return apiClient.get("/users/" + userId, User.class);
    }

    public User updateUser(String userId, User userUpdate) throws Exception {
        return apiClient.put("/users/" + userId, userUpdate, User.class);
    }
}