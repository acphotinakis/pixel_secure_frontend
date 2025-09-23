package com.videogamedb.cli.services;

import com.videogamedb.cli.models.AccessTime;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class AccessTimeService {
    private final ApiClient apiClient;

    public AccessTimeService() {
        this.apiClient = new ApiClient();
    }

    public List<AccessTime> getMyAccessTimes() throws Exception {
        AccessTime[] accessTimes = apiClient.get("/access-times/user/me", AccessTime[].class);
        return Arrays.asList(accessTimes);
    }

    public AccessTime recordAccessTime() throws Exception {
        AccessTime accessTime = new AccessTime();
        accessTime.setTime(LocalDateTime.now());
        return apiClient.post("/access-times", accessTime, AccessTime.class);
    }

    public List<AccessTime> getRecentAccessTimes(int days) throws Exception {
        AccessTime[] accessTimes = apiClient.get("/access-times/recent?days=" + days, AccessTime[].class);
        return Arrays.asList(accessTimes);
    }
}