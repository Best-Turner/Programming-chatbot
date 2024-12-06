package org.example.repository;

import org.example.command.Command;
import org.example.model.Message;

public interface KeywordStorage {

    void add(String key, int code);

    void deleteCommand(String key);

    int getStatusCode(String input);

}
