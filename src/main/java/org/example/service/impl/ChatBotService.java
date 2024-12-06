package org.example.service.impl;

import org.example.command.Command;
import org.example.command.impl.GoodbyeCommand;
import org.example.command.impl.GreetCommand;
import org.example.command.impl.LessonsCommand;
import org.example.model.Message;
import org.example.repository.KeywordStorage;
import org.example.service.LessonService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ChatBotService {
    private final String errorMessage = "Не известная команда. Пожалуйста повторите";

    private final KeywordStorage storageCommand;
    private final LessonService lessonService;


    public ChatBotService(KeywordStorage storageCommand, LessonService lessonService) {
        this.storageCommand = storageCommand;
        this.lessonService = lessonService;
    }


    public String getResponse(String input) throws Exception {
        Command response = null;
        Message message = processInput(input);
        String[] words = input.split("\\s+");
        int statusCode = storageCommand.getStatusCode(words[0]);

        switch (statusCode) {
            case 0:
                response = new GreetCommand();
                break;
            case 1:
                response = new LessonsCommand(lessonService);
                break;
            case 2:
                //response = new QuestionCommand(questionService);
                break;
            case 3:
                response = new GoodbyeCommand();
                break;
            default:
                return "команда не распознана";
        }
        return response.execute(message);
    }


    private Message processInput(String input) {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setText(input);
        message.setDateTime(LocalDateTime.now());
        return message;
    }
}
