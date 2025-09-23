package com.videogamedb.cli.models;

import java.time.LocalDateTime;

public class PlaySession {
    private String id;
    private String userId;
    private String gameId;
    private LocalDateTime datetimeOpened;
    private Integer timePlayed;

    public PlaySession() {
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getDatetimeOpened() {
        return datetimeOpened;
    }

    public void setDatetimeOpened(LocalDateTime datetimeOpened) {
        this.datetimeOpened = datetimeOpened;
    }

    public Integer getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(Integer timePlayed) {
        this.timePlayed = timePlayed;
    }
}