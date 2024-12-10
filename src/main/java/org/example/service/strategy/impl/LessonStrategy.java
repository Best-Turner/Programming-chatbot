package org.example.service.strategy.impl;

import org.example.model.Lesson;
import org.example.repository.KeywordStorage;
import org.example.service.LessonService;
import org.example.service.strategy.ResponseStrategy;

import java.util.Formatter;
import java.util.List;

public class LessonStrategy implements ResponseStrategy {

    private final KeywordStorage keywordStorage;
    private final LessonService lessonService;

    public LessonStrategy(KeywordStorage keywordStorage, LessonService lessonService) {
        this.keywordStorage = keywordStorage;
        this.lessonService = lessonService;
    }

    @Override
    public String response(String input) {
        List<Lesson> all = lessonService.getAll();
        StringBuilder builder = new StringBuilder();
        builder.append("\t~ВОТ СПИСОК ДОСТУПНЫХ УРОКОВ~\n");
        if (all.isEmpty()) {
            return "~У ВАС НЕТ НИ ОДНОГО ДОСТУПНОГО УРОКА~";
        }
        try (Formatter formatter = new Formatter(builder)){
            all.forEach(el -> formatter.format("\tID (%d): %s; %s%n", el.getId(), el.getTitle(), el.getTopic()));
            return builder.toString();
        }
    }
}
