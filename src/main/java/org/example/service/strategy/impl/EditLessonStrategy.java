package org.example.service.strategy.impl;

import org.example.exception.LessonNotFoundException;
import org.example.model.Lesson;
import org.example.service.LessonService;
import org.example.service.strategy.ResponseStrategy;

import java.util.List;
import java.util.Optional;

public class EditLessonStrategy implements ResponseStrategy {

    private static final String ENTER_NEW_TITLE = "~ВВЕДИТЕ НОВЫЙ ЗАГОЛОВОК~";
    private static final String ENTER_NEW_DESCRIPTION = "~ВВДЕЛИТЕ НОВОЕ ОПИСАНИЕ~";
    private static final String ENTER_NEW_TOPIC = "~ВВЕДИТЕ НОВУЮ ТЕМУ~";
    private static final String ENTER_NEW_CONTENT = "~ВВЕДИТЕ НОВОЕ СОДЕРЖАНИЕ~";
    private final LessonService lessonService;
    private Lesson tempLesson;
    private int counter = 0;
    private int tempLessonId = 0;

    public EditLessonStrategy(LessonService lessonService) {
        this.lessonService = lessonService;
        tempLesson = new Lesson();
    }

    @Override
    public String response(String input) {

        StringBuilder builder = new StringBuilder();
        if (counter == 0) {
            Optional<Integer> isNumber = parseInputToInt(input);
            if (isNumber.isPresent()) {
                tempLessonId = isNumber.get();
                counter++;
            } else {
                builder.append("~СПИСОК УРОКОВ КОТОРЫЙ МОЖНО ИЗМЕНИТЬ~");
                List<Lesson> all = lessonService.getAll();
                all.forEach(el -> builder.append(String.format("\n\tID (%d): %s, %s", el.getId(), el.getTitle(), el.getTopic())));
                builder.append("\n~ВВЕДИТЕ ID УРОКА КОТОРЫЙ ХОТИТЕ ИЗМЕНИТЬ~");
                return builder.toString();
            }
        }
        try {
            switch (counter) {
                case 1:
                    builder.append(ENTER_NEW_TITLE);
                    counter++;
                    break;
                case 2:
                    tempLesson.setTitle(input);
                    builder.append(ENTER_NEW_DESCRIPTION);
                    counter++;
                    break;
                case 3:
                    tempLesson.setDescription(input);
                    builder.append(ENTER_NEW_CONTENT);
                    counter++;
                    break;
                case 4:
                    tempLesson.setContent(input);
                    builder.append(ENTER_NEW_TOPIC);
                    counter++;
                    break;
                case 5:
                    tempLesson.setTopic(input);
                    lessonService.editById(tempLessonId, tempLesson);
                    builder.append(String.format("~ДАННЫЕ ЗАДАЧИ С ID = %d ИЗМЕНЕНЫ~", tempLessonId));
                    counter = 0;
                    break;
            }
        } catch (LessonNotFoundException e) {
            builder.append(e.getMessage());
        }

        return builder.toString();
    }
}
