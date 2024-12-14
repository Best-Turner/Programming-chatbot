package org.example.service.strategy.impl;

import org.example.model.Lesson;
import org.example.service.LessonService;
import org.example.service.strategy.ResponseStrategy;

import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;

public class DeleteStrategy implements ResponseStrategy {

    private final LessonService lessonService;

    public DeleteStrategy(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Override
    public String response(String input) {
        StringBuilder builder = new StringBuilder();
        try (Formatter formatter = new Formatter(builder)) {
            Optional<Integer> isNumber = parseInputToInt(input);
            if (isNumber.isPresent()) {
                int id = isNumber.get();
                Lesson byId = lessonService.getById(id);
                lessonService.deleteById(id);
                formatter.format("Урок - (%d) %s, %s  /УДАЛЕН", id, byId.getTitle(), byId.getTopic());
            } else {
                builder.append("~ВОТ СПИСОК УРОКОВ КОТОРЫЕ ВЫ МОЖЕТЕ УДАЛИТЬ~");
                List<Lesson> all = lessonService.getAll();
                all.forEach(lesson -> formatter.format("%nID (%d): %s, %s", lesson.getId(), lesson.getTitle(), lesson.getTopic()));
                builder.append("\n~ВВЕДИТЕ ID УРОКА КОТОРЫЙ ХОТИТЕ УДАЛИТЬ~");
            }
        } catch (Exception e) {
            builder.append(e.getMessage());
        }
        return builder.toString();
    }
}
