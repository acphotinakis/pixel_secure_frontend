package com.videogamedb.cli.services;

import com.videogamedb.cli.models.JwtResponse;
import com.videogamedb.cli.models.LoginRequest;
import com.videogamedb.cli.models.SignUpRequest;

import java.util.Map;

public class AuthService {
    private final ApiClient apiClient;

    public AuthService() {
        this.apiClient = new ApiClient();
    }

    public JwtResponse login(LoginRequest loginRequest) throws Exception {
        return apiClient.post("/auth/login", loginRequest, JwtResponse.class);
    }

    public Map<String, Object> signUp(SignUpRequest signUpRequest) throws Exception {
        return apiClient.post("/auth/register", signUpRequest, Map.class);
    }

    public void logout() throws Exception {
        apiClient.post("/auth/logout", null, Void.class);
    }
}