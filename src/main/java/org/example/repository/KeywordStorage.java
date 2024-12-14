package org.example.repository;

public interface KeywordStorage {

    void add(String key, int code);

    void deleteCommand(String key);

    int getStatusCode(String input);

}
