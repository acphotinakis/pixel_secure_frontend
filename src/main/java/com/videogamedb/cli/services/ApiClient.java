package com.videogamedb.cli.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videogamedb.cli.util.ConfigLoader;
import com.videogamedb.cli.util.SecurityUtils;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ApiClient {
    private static final String BASE_URL = ConfigLoader.getProperty("api.base-url", "http://localhost:8080/api");
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .build();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public <T> T get(String endpoint, Class<T> responseType) throws Exception {
        HttpRequest request = buildRequest(endpoint).GET().build();
        return executeRequest(request, responseType);
    }

    public <T> T post(String endpoint, Object body, Class<T> responseType) throws Exception {
        String requestBody = objectMapper.writeValueAsString(body);
        HttpRequest request = buildRequest(endpoint)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .build();
        return executeRequest(request, responseType);
    }

    public <T> T put(String endpoint, Object body, Class<T> responseType) throws Exception {
        String requestBody = objectMapper.writeValueAsString(body);
        HttpRequest request = buildRequest(endpoint)
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .header("Content-Type", "application/json")
                .build();
        return executeRequest(request, responseType);
    }

    public void delete(String endpoint) throws Exception {
        HttpRequest request = buildRequest(endpoint).DELETE().build();
        executeRequest(request, Void.class);
    }

    private HttpRequest.Builder buildRequest(String endpoint) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Accept", "application/json");

        String token = SecurityUtils.getToken();
        if (token != null && !token.isEmpty()) {
            builder.header("Authorization", "Bearer " + token);
        }

        return builder;
    }

    private <T> T executeRequest(HttpRequest request, Class<T> responseType) throws Exception {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            throw new SecurityException("Authentication required. Please login again.");
        }

        if (response.statusCode() >= 400) {
            String errorMessage = extractErrorMessage(response.body());
            throw new RuntimeException("HTTP " + response.statusCode() + ": " + errorMessage);
        }

        if (responseType == Void.class) {
            return null;
        }

        return objectMapper.readValue(response.body(), responseType);
    }

    private String extractErrorMessage(String responseBody) {
        try {
            // Try to parse error response
            var errorNode = objectMapper.readTree(responseBody);
            if (errorNode.has("message")) {
                return errorNode.get("message").asText();
            }
            if (errorNode.has("error")) {
                return errorNode.get("error").asText();
            }
        } catch (Exception e) {
            // Ignore parsing errors
        }
        return responseBody;
    }
}