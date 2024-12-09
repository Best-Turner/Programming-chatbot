package org.example.service.impl;

import org.example.command.Command;
import org.example.command.impl.DeleteLessonByIdCommand;
import org.example.command.impl.GetLessonsCommand;
import org.example.command.impl.GreetCommand;
import org.example.command.impl.LessonByIdCommand;
import org.example.model.Message;
import org.example.repository.KeywordStorage;
import org.example.service.LessonService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ChatBotService {
    private final KeywordStorage storageCommand;
    private final LessonService lessonService;
    private static final int CODE_GREETING = 0;
    private static final int CODE_LESSONS = 1;
    private static final int CODE_DELETE = 2;
    private static final int CODE_EDIT_LESSON = 3;
    private static final int CODE_GOODBYE = 4;

    private int currentStatusCode = CODE_GREETING;


    public ChatBotService(KeywordStorage storageCommand, LessonService lessonService) {
        this.storageCommand = storageCommand;
        this.lessonService = lessonService;
    }


    public String getResponse(String input) throws Exception {
        Command response = null;
        Message message = processInput(input);
        StringBuilder tempMessage;

        if (currentStatusCode == CODE_GREETING) {
            String[] words = input.split("\\s+");
            int tempCode = storageCommand.getStatusCode(words[0]);

            switch (tempCode) {
                case CODE_GREETING:
                    response = new GreetCommand();
                    break;
                case CODE_LESSONS:
                    currentStatusCode = tempCode;
                    response = new GetLessonsCommand(lessonService);
                    break;
                case CODE_DELETE:
                    tempMessage = new StringBuilder();
                    tempMessage.append(new GetLessonsCommand(lessonService).execute(message))
                            .append("\n")
                            .append("Введите ID урока который хотите удалить");
                    currentStatusCode = CODE_DELETE;
                    return tempMessage.toString();
                case CODE_EDIT_LESSON:
                    tempMessage = new StringBuilder();
                    tempMessage.append(new GetLessonsCommand(lessonService).execute(message))
                            .append("\n")
                            .append("Введите ID урока который хотите изменить");
                    currentStatusCode = CODE_EDIT_LESSON;
                    return tempMessage.toString();
                default: {
                    return "команда не распознана";
                }
            }

        } else {
            if (checkNumber(input)) {
                switch (currentStatusCode) {
                    case CODE_LESSONS:
                        response = new LessonByIdCommand(lessonService);
                        break;
                    case CODE_DELETE:
                        new DeleteLessonByIdCommand(lessonService);
                        break;
                }
                currentStatusCode = CODE_GREETING;
            } else {
                return "Вы ввели не числовое значение";
            }
        }

        return response != null ? response.execute(message) : "Ошибка выполнения команды.";
    }


    private Message processInput(String input) {
        Message message = new Message();
        message.setId(UUID.randomUUID().toString());
        message.setText(input);
        message.setDateTime(LocalDateTime.now());
        return message;
    }


    private boolean checkNumber(String input) {
        return input.matches("\\d+");
    }
}
