package com.videogamedb.cli.services;

import com.videogamedb.cli.models.Collection;

import java.util.Arrays;
import java.util.List;

public class CollectionService {
    private final ApiClient apiClient;

    public CollectionService() {
        this.apiClient = new ApiClient();
    }

    public List<Collection> getMyCollections() throws Exception {
        Collection[] collections = apiClient.get("/collections/user/me", Collection[].class);
        return Arrays.asList(collections);
    }

    public Collection createCollection(String name, String description) throws Exception {
        Collection collection = new Collection();
        collection.setName(name);
        collection.setDescription(description);
        return apiClient.post("/collections", collection, Collection.class);
    }

    public Collection addGame(String collectionId, String gameId) throws Exception {
        Collection collectionUpdate = new Collection();
        // This would typically be handled by adding the game to the collection's game
        // list
        return apiClient.put("/collections/" + collectionId + "/games/" + gameId, null, Collection.class);
    }

    public Collection removeGame(String collectionId, String gameId) throws Exception {
        apiClient.delete("/collections/" + collectionId + "/games/" + gameId);
        return getCollectionById(collectionId);
    }

    public Collection getCollectionById(String collectionId) throws Exception {
        return apiClient.get("/collections/" + collectionId, Collection.class);
    }
}