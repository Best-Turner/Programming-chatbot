package org.example.service.impl;

import org.example.repository.KeywordStorage;
import org.example.service.LessonService;
import org.example.service.strategy.ResponseStrategy;
import org.example.service.strategy.impl.DeleteStrategy;
import org.example.service.strategy.impl.EditLessonStrategy;
import org.example.service.strategy.impl.GreetingStrategy;
import org.example.service.strategy.impl.LessonStrategy;
import org.springframework.stereotype.Service;

@Service
public class ChatBotService {
    private static final int CODE_GREETING = 0;
    private static final int CODE_LESSONS = 1;
    private static final int CODE_DELETE = 2;
    private static final int CODE_EDIT_LESSON = 3;
    private static final String MESSAGE_ERROR_INPUT = "Неверная команда";
    private final KeywordStorage storageCommand;
    //private static final int CODE_GOODBYE = 4;
    private final LessonService lessonService;
    private ResponseStrategy strategy;
    private int currentStatus = CODE_GREETING;


    public ChatBotService(KeywordStorage storageCommand, LessonService lessonService) {
        this.storageCommand = storageCommand;
        this.lessonService = lessonService;
        strategy = new GreetingStrategy();
    }


    public String getResponse(String input) {
        String[] words = input.split("\\s+");
        int tempInputCode = storageCommand.getStatusCode(words[0]);
        if (currentStatus != tempInputCode) {
            switch (tempInputCode) {
                case CODE_GREETING:
                    this.setStrategy(new GreetingStrategy());
                    break;
                case CODE_LESSONS:
                    this.setStrategy(new LessonStrategy(storageCommand, lessonService));
                    break;
                case CODE_DELETE:
                    this.setStrategy(new DeleteStrategy(lessonService));
                    break;
                case CODE_EDIT_LESSON:
                    this.setStrategy(new EditLessonStrategy(lessonService));
                    break;
            }
            currentStatus = tempInputCode;
        }
        return strategy != null ? strategy.response(input) : MESSAGE_ERROR_INPUT;
    }

    public void setStrategy(ResponseStrategy strategy) {
        this.strategy = strategy;
    }
}
