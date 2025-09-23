package com.videogamedb.cli.services;

import com.videogamedb.cli.models.PlaySession;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class PlaySessionService {
    private final ApiClient apiClient;

    public PlaySessionService() {
        this.apiClient = new ApiClient();
    }

    public List<PlaySession> getMySessions() throws Exception {
        PlaySession[] sessions = apiClient.get("/play-sessions/user/me", PlaySession[].class);
        return Arrays.asList(sessions);
    }

    public PlaySession startSession(String gameId) throws Exception {
        PlaySession session = new PlaySession();
        session.setGameId(gameId);
        session.setDatetimeOpened(LocalDateTime.now());
        return apiClient.post("/play-sessions", session, PlaySession.class);
    }

    public PlaySession endSession(String sessionId, int timePlayed) throws Exception {
        PlaySession sessionUpdate = new PlaySession();
        sessionUpdate.setTimePlayed(timePlayed);
        return apiClient.put("/play-sessions/" + sessionId, sessionUpdate, PlaySession.class);
    }

    public PlaySession getSessionById(String sessionId) throws Exception {
        return apiClient.get("/play-sessions/" + sessionId, PlaySession.class);
    }
}