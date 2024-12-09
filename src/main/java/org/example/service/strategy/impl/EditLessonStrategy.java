package org.example.service.strategy.impl;

import org.example.repository.KeywordStorage;
import org.example.service.LessonService;
import org.example.service.strategy.ResponseStrategy;

public class EditLessonStrategy implements ResponseStrategy {
    public EditLessonStrategy(KeywordStorage storageCommand, LessonService lessonService) {
    }

    @Override
    public String response(String input) {
        return null;
    }
}
