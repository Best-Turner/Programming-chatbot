package org.example.service.strategy.impl;

import org.example.repository.KeywordStorage;
import org.example.service.LessonService;
import org.example.service.strategy.ResponseStrategy;

public class DeleteStrategy implements ResponseStrategy {
    public DeleteStrategy(KeywordStorage storageCommand, LessonService lessonService) {
    }

    @Override
    public String response(String input) {
        return null;
    }
}
