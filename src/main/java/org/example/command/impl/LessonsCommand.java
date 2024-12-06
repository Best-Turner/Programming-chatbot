package org.example.command.impl;

import org.example.command.Command;
import org.example.model.Lesson;
import org.example.model.Message;
import org.example.service.LessonService;

import java.util.List;

public class LessonsCommand implements Command {
    private final LessonService service;

    public LessonsCommand(LessonService service) {
        this.service = service;
    }

    @Override
    public String execute(Message input) throws Exception {
        String text = input.getText();
        StringBuilder builder = new StringBuilder();
        String[] split = text.split("\\s+");
        if (split.length > 1) {
            if (split[1].matches("\\d+")) {
                return service.getById(Integer.parseInt(split[1])).toString();
            }
        } else {
            builder.append("Список доступных уроков\n");
            List<Lesson> all = service.getAll();
            all.forEach(lesson -> builder
                    .append("\n")
                    .append(lesson.getId()).append(" ")
                    .append(lesson.getTitle()).append(" ")
                    .append(lesson.getTopic())
                    .append("\n"));
        }

        return builder.toString();
    }
}
